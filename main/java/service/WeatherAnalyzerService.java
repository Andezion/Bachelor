package service;

import model.AnalysisResult;
import model.ProjectSettings;
import model.WeatherData;

import java.util.List;

public class WeatherAnalyzerService
{

    public AnalysisResult analyzeSolarPowerPayback(List<WeatherData> weatherDataList, ProjectSettings settings)
    {
        int sunnyDays = 0;
        int rainyDays = 0;
        double totalTemp = 0.0;
        double totalWind = 0.0;
        double totalSolarRadiation = 0.0;

        for (WeatherData wd : weatherDataList)
        {
            if (wd.getPrecipitation() == 0.0 && wd.getTemperature() > 5.0) sunnyDays++;
            if (wd.getPrecipitation() > 0.0) rainyDays++;

            totalTemp += wd.getTemperature();
            totalWind += wd.getWindSpeed();

            totalSolarRadiation += wd.getSolarRadiation();
        }

        double avgTemp = weatherDataList.isEmpty() ? 0.0 : totalTemp / weatherDataList.size();
        double avgWind = weatherDataList.isEmpty() ? 0.0 : totalWind / weatherDataList.size();

        double efficiency = settings.getSolarEfficiency();
        double installationCost = settings.getSolarInstallationCost();
        double tariff = settings.getElectricityTariff_solar();

        double azimuth = settings.getPanelAzimuthAngle();
        double tilt = settings.getPanelTiltAngle();
        double optimalTilt = 35;

        double azimuthFactor = Math.cos(Math.toRadians(Math.abs(azimuth - 180)));
        double tiltFactor = Math.cos(Math.toRadians(Math.abs(tilt - optimalTilt)));
        double orientationEfficiencyFactor = azimuthFactor * tiltFactor;

        double totalKWhPerSquareMeter = totalSolarRadiation / 2000.0;

        double panelArea = settings.getPanelArea();
        double realEnergyGenerated = totalKWhPerSquareMeter * efficiency * orientationEfficiencyFactor * panelArea;

        double savings = realEnergyGenerated * tariff;

        double estimatedPaybackMonths = (savings == 0)
                ? Double.POSITIVE_INFINITY
                : installationCost / savings;

        boolean meets = estimatedPaybackMonths <= settings.getTargetPaybackMonths();

        String notes = String.format(
                "Sunny days: %d, avg. temperature: %.1f°C, generation: %.1f kW⋅h, savings: %.0f UAH, orientation factor: %.2f",
                sunnyDays, avgTemp, realEnergyGenerated, savings, orientationEfficiencyFactor
        );

        return new AnalysisResult(
                estimatedPaybackMonths / 12,
                meets,
                notes,
                sunnyDays,
                rainyDays,
                avgTemp,
                avgWind
        );
    }

    public AnalysisResult analyzeWindPowerPayback(List<WeatherData> weatherDataList, ProjectSettings settings)
    {
        int sunnyDays = 0;
        int rainyDays = 0;
        double totalTemp = 0.0;
        double totalWind = 0.0;

        for (WeatherData wd : weatherDataList)
        {
            if (wd.getPrecipitation() == 0.0 && wd.getTemperature() > 5.0)
            {
                sunnyDays++;
            }
            if (wd.getPrecipitation() > 0.0)
            {
                rainyDays++;
            }
            totalTemp += wd.getTemperature();
            totalWind += wd.getWindSpeed();
        }

        double avgTemp = weatherDataList.isEmpty() ? 0.0 : totalTemp / weatherDataList.size();
        double avgWind = weatherDataList.isEmpty() ? 0.0 : totalWind / weatherDataList.size();

        double area = Math.PI * Math.pow(settings.getTurbineRadius(), 2);
        double efficiency = settings.getTurbineEfficiency();
        double tariff = settings.getElectricityTariff_wind();
        double totalEnergyKWh = getTotalEnergyKWh(weatherDataList, area, efficiency);

        double totalCost = settings.getWorkerCost() + settings.getWindTurbineCost();

        double estimatedPaybackMonths = totalEnergyKWh == 0.0 ? Double.POSITIVE_INFINITY
                : totalCost / (totalEnergyKWh * tariff);

        boolean meets = estimatedPaybackMonths <= settings.getDesiredPaybackPeriodYears() * 12;

        String notes = String.format("Average wind speed: %.2f m/s, Energy production: %.0f kW⋅h", avgWind, totalEnergyKWh);

        return new AnalysisResult(
                estimatedPaybackMonths / 12,
                meets,
                notes,
                sunnyDays,
                rainyDays,
                avgTemp,
                avgWind
        );
    }

    private static double getTotalEnergyKWh(List<WeatherData> weatherDataList, double area, double efficiency)
    {
        double totalEnergyWh = 0.0;

        for (WeatherData wd : weatherDataList)
        {
            double windSpeed = wd.getWindSpeed();
            double temperature = wd.getTemperature();
            double pressure = wd.getPressure();

            double airDensity = AirDensityCalculator.calculateDensity(pressure, temperature);
            double power = 0.5 * airDensity * area * Math.pow(windSpeed, 3) * efficiency;

            totalEnergyWh += power * 24;
        }


        double totalEnergyKWh = totalEnergyWh / 1000.0;
        return totalEnergyKWh;
    }
}


class AirDensityCalculator
{
    private static final double R = 287.05;

    public static double calculateDensity(double pressure_hPa, double temperature_C)
    {

        double pressure_Pa = pressure_hPa * 100;
        double temperature_K = temperature_C + 273.15;

        return pressure_Pa / (R * temperature_K);
    }
}
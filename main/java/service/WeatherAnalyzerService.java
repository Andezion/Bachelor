package service;

import model.AnalysisResult;
import model.ProjectSettings;
import model.WeatherData;

import java.util.List;

public class WeatherAnalyzerService {

    public AnalysisResult analyzeSolarPowerPayback(List<WeatherData> weatherDataList, ProjectSettings settings)
    {
        int sunnyDays = 0;
        int rainyDays = 0;
        double totalTemp = 0.0;
        double totalWind = 0.0;
        double totalSolarRadiation = 0.0;

        for (WeatherData wd : weatherDataList) {
            if (wd.getPrecipitation() == 0.0 && wd.getTemperature() > 5.0) sunnyDays++;
            if (wd.getPrecipitation() > 0.0) rainyDays++;

            totalTemp += wd.getTemperature();
            totalWind += wd.getWindSpeed();

            totalSolarRadiation += wd.getSolarRadiation(); // суммарная радиация (Вт⋅ч/м²)
        }

        double avgTemp = weatherDataList.isEmpty() ? 0.0 : totalTemp / weatherDataList.size();
        double avgWind = weatherDataList.isEmpty() ? 0.0 : totalWind / weatherDataList.size();

        // Параметры из настроек
        double efficiency = settings.getSolarEfficiency();                 // КПД панели, например 0.18
        double installationCost = settings.getSolarInstallationCost();    // Стоимость установки
        double tariff = settings.getElectricityTariff_solar();                   // Цена за 1 кВт⋅ч

        // Корректировка на ориентацию и наклон
        double azimuth = settings.getPanelAzimuthAngle(); // 180 = юг
        double tilt = settings.getPanelTiltAngle();       // угол наклона
        double optimalTilt = 35;                          // условно для средней широты

        // Упрощённая модель поправки
        double azimuthFactor = Math.cos(Math.toRadians(Math.abs(azimuth - 180))); // максимум при юге
        double tiltFactor = Math.cos(Math.toRadians(Math.abs(tilt - optimalTilt))); // максимум при оптимальном угле
        double orientationEfficiencyFactor = azimuthFactor * tiltFactor;

        // Общая энергия (в кВт⋅ч/м²)
        double totalKWhPerSquareMeter = totalSolarRadiation / 1000.0;

        double panelArea = settings.getPanelArea();
        double realEnergyGenerated = totalKWhPerSquareMeter * efficiency * orientationEfficiencyFactor * panelArea;

        double savingsRub = realEnergyGenerated * tariff;

        // Окупаемость
        double estimatedPaybackMonths = (savingsRub == 0)
                ? Double.POSITIVE_INFINITY
                : installationCost / savingsRub;

        boolean meets = estimatedPaybackMonths <= settings.getTargetPaybackMonths();

        String notes = String.format(
                "Солнечных дней: %d, ср. температура: %.1f°C, генерация: %.1f кВт⋅ч, экономия: %.0f грн., ориентац. фактор: %.2f",
                sunnyDays, avgTemp, realEnergyGenerated, savingsRub, orientationEfficiencyFactor
        );

        return new AnalysisResult(
                estimatedPaybackMonths,
                meets,
                notes,
                sunnyDays,
                rainyDays,
                avgTemp,
                avgWind
        );
    }

    public AnalysisResult analyzeWindPowerPayback(List<WeatherData> weatherDataList, ProjectSettings settings) {
        int sunnyDays = 0;
        int rainyDays = 0;
        double totalTemp = 0.0;
        double totalWind = 0.0;

        for (WeatherData wd : weatherDataList) {
            if (wd.getPrecipitation() == 0.0 && wd.getTemperature() > 5.0) sunnyDays++;
            if (wd.getPrecipitation() > 0.0) rainyDays++;
            totalTemp += wd.getTemperature();
            totalWind += wd.getWindSpeed();
        }

        double avgTemp = weatherDataList.isEmpty() ? 0.0 : totalTemp / weatherDataList.size();
        double avgWind = weatherDataList.isEmpty() ? 0.0 : totalWind / weatherDataList.size();

        double area = Math.PI * Math.pow(settings.getTurbineRadius(), 2);
        double efficiency = settings.getTurbineEfficiency();
        double tariff = settings.getElectricityTariff_wind(); // евро/кВт⋅ч
        double totalEnergyKWh = getTotalEnergyKWh(weatherDataList, area, efficiency);

        // Расчёт окупаемости
        double totalCost = settings.getWorkerCost() + settings.getWindTurbineCost();
        double estimatedPaybackMonths = totalEnergyKWh == 0.0 ? Double.POSITIVE_INFINITY
                : totalCost / (totalEnergyKWh * tariff / 12.0);

        boolean meets = estimatedPaybackMonths <= settings.getDesiredPaybackPeriodYears() * 12;

        String notes = String.format("Средняя скорость ветра: %.2f м/с, Производство энергии: %.0f кВт⋅ч", avgWind, totalEnergyKWh);

        return new AnalysisResult(
                estimatedPaybackMonths,
                meets,
                notes,
                sunnyDays,
                rainyDays,
                avgTemp,
                avgWind
        );
    }

    private static double getTotalEnergyKWh(List<WeatherData> weatherDataList, double area, double efficiency) {
        double totalEnergyWh = 0.0;

        for (WeatherData wd : weatherDataList) {
            double windSpeed = wd.getWindSpeed();
            double temperature = wd.getTemperature();
            double pressure = wd.getPressure(); // убедись, что есть getPressure() в hPa

            double airDensity = AirDensityCalculator.calculateDensity(pressure, temperature);
            double power = 0.5 * airDensity * area * Math.pow(windSpeed, 3) * efficiency;

            totalEnergyWh += power * 24;
        }


        // Считаем кВт⋅ч
        double totalEnergyKWh = totalEnergyWh / 1000.0;
        return totalEnergyKWh;
    }
}


class AirDensityCalculator
{
    private static final double R = 287.05; // Дж/(кг·К)

    public static double calculateDensity(double pressure_hPa, double temperature_C)
    {

        double pressure_Pa = pressure_hPa * 100;
        double temperature_K = temperature_C + 273.15;

        return pressure_Pa / (R * temperature_K);
    }
}
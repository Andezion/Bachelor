package service;

import model.AnalysisResult;
import model.ProjectSettings;
import model.WeatherData;

import java.util.List;

public class WeatherAnalyzerService {

    public AnalysisResult analyzeSolarPowerPayback(List<WeatherData> weatherDataList, ProjectSettings settings) {

//        int sunnyDays = 0;
//        int rainyDays = 0;
//        double totalTemp = 0.0;
//        double totalWind = 0.0;
//
//        for (WeatherData wd : weatherDataList) {
//            if (wd.getPrecipitation() == 0.0 && wd.getTemperature() > 5.0) sunnyDays++;
//            if (wd.getPrecipitation() > 0.0) rainyDays++;
//
//            totalTemp += wd.getTemperature();
//            totalWind += wd.getWindSpeed();
//        }
//
//        double avgTemp = weatherDataList.isEmpty() ? 0.0 : totalTemp / weatherDataList.size();
//        double avgWind = weatherDataList.isEmpty() ? 0.0 : totalWind / weatherDataList.size();
//
//        double incomePerSunnyDay = 100.0;
//        double totalIncome = sunnyDays * incomePerSunnyDay;
//        double estimatedPaybackMonths = totalIncome == 0
//                ? Double.POSITIVE_INFINITY
//                : (settings.getWorkerCost() + settings.getEquipmentCost()) / totalIncome;
//
//        boolean meets = estimatedPaybackMonths <= settings.getDesiredPaybackPeriodYears() * 12;
//
//        String notes = String.format("Средняя температура: %.2f°C, солнечных дней: %d", avgTemp, sunnyDays);
//
//        return new AnalysisResult(
//                estimatedPaybackMonths,
//                meets,
//                notes,
//                sunnyDays,
//                rainyDays,
//                avgTemp,
//                avgWind
//        );
        int sunnyDays = 0;
        int rainyDays = 0;
        double totalTemp = 0.0;
        double totalWind = 0.0;

        double totalSolarRadiation = 0.0; // Вт·ч/м²

        for (WeatherData wd : weatherDataList) {
            if (wd.getPrecipitation() == 0.0 && wd.getTemperature() > 5.0) sunnyDays++;
            if (wd.getPrecipitation() > 0.0) rainyDays++;

            totalTemp += wd.getTemperature();
            totalWind += wd.getWindSpeed();

            // solarRadiation уже в Вт/м² за час (или суточная сумма) — уточни единицы при сборе
            totalSolarRadiation += wd.getSolarRadiation();
        }

        double avgTemp = weatherDataList.isEmpty() ? 0.0 : totalTemp / weatherDataList.size();
        double avgWind = weatherDataList.isEmpty() ? 0.0 : totalWind / weatherDataList.size();

        // Параметры из настроек
        double efficiency = settings.getSolarEfficiency();                  // Например, 0.18
        double installationCost = settings.getSolarInstallationCost();     // Например, 80_000 руб/кВт
        double tariff = settings.getElectricityTariff();                    // Например, 5.5 руб/кВт⋅ч

        // Энергия в кВт⋅ч на 1 м²:
        double totalKWhPerSquareMeter = totalSolarRadiation / 1000.0;      // кВт⋅ч/м²
        double realEnergyGenerated = totalKWhPerSquareMeter * efficiency;  // учёт КПД

        // Стоимость установки системы мощностью 1 кВт
        double costRub = installationCost;

        // Доход от генерации:
        double savingsRub = realEnergyGenerated * tariff;

        // Окупаемость в месяцах:
        double estimatedPaybackMonths = (savingsRub == 0)
                ? Double.POSITIVE_INFINITY
                : costRub / savingsRub;

        boolean meets = estimatedPaybackMonths <= settings.getTargetPaybackMonths();

        String notes = String.format(
                "Солнечных дней: %d, средняя температура: %.1f°C, генерация: %.1f кВт⋅ч, экономия: %.0f руб.",
                sunnyDays, avgTemp, realEnergyGenerated, savingsRub
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

        double totalWindPower = 0.0;
        for (WeatherData wd : weatherDataList)
        {
            totalWindPower += Math.pow(wd.getWindSpeed(), 3); // кинетическая энергия ветра
        }



        double estimatedPaybackMonths = (settings.getWorkerCost() + settings.getEquipmentCost()) / (totalWindPower * 0.005);
        boolean meets = estimatedPaybackMonths <= settings.getDesiredPaybackPeriodYears() * 12;

        String notes = String.format("Средняя скорость ветра: %.2f м/с", avgWind);

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
}

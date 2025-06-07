package service;

import model.AnalysisResult;
import model.ProjectSettings;
import model.WeatherData;

import java.util.List;

public class WeatherAnalyzerService {

    public AnalysisResult analyzeSolarPowerPayback(List<WeatherData> weatherDataList, ProjectSettings settings) {
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

        double estimatedPaybackMonths = (settings.getWorkerCost() + settings.getEquipmentCost()) / (sunnyDays * 100.0);
        boolean meets = estimatedPaybackMonths <= settings.getDesiredPaybackPeriodYears() * 12;

        String notes = String.format("Средняя температура: %.2f°C, солнечных дней: %d", avgTemp, sunnyDays);

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

        double estimatedPaybackMonths = (settings.getWorkerCost() + settings.getEquipmentCost()) / (avgWind * 150.0);
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

package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.AnalysisResult;
import model.Location;
import model.ProjectSettings;
import model.WeatherData;
import repository.FavouriteRepository;
import service.WeatherAnalyzerService;
import service.WeatherApiService;

import java.io.IOException;
import java.util.List;

public class MainController
{

    @FXML
    private TextField latitudeField;

    @FXML
    private TextField longitudeField;

    @FXML
    private TextArea outputArea;

    @FXML
    private Label statusLabel;

    @FXML
    private LineChart<Number, Number> temperatureChart;

    @FXML
    private LineChart<Number, Number> windChart;

    @FXML
    private LineChart<Number, Number> solarChart;

    @FXML
    private void onAnalyze() {
        try
        {
            double lat = Double.parseDouble(latitudeField.getText());
            double lon = Double.parseDouble(longitudeField.getText());

            List<WeatherData> dataList = WeatherApiService.getHistoricalDataForYear(lat, lon);

            // Загружаем настройки проекта (из файла или как-то иначе — пока просто "заглушка")
            ProjectSettings settings = ProjectSettings.load(); // Предположим, ты реализовал метод load()

            WeatherAnalyzerService analyzer = new WeatherAnalyzerService();
            AnalysisResult result_solar = analyzer.analyzeSolarPowerPayback(dataList, settings);
            AnalysisResult result_wind = analyzer.analyzeWindPowerPayback(dataList, settings);

            outputArea.clear();
            outputArea.appendText("Оценка окупаемости солнечной электростанции:\n");
            outputArea.appendText(String.format("Окупаемость: %.1f мес.\n", result_solar.getPaybackPeriodMonths()));
            outputArea.appendText("Соответствует требованиям: " + (result_solar.isMeetsRequirements() ? "Да" : "Нет") + "\n");
            outputArea.appendText("Солнечных дней: " + result_solar.getSunnyDays() + "\n");
            outputArea.appendText("Дождливых дней: " + result_solar.getRainyDays() + "\n");
            outputArea.appendText(String.format("Средняя температура: %.2f°C\n", result_solar.getAverageTemperature()));
            outputArea.appendText("Примечание: " + result_solar.getNotes() + "\n\n");

            outputArea.appendText("Оценка окупаемости ветровой электростанции:\n");
            outputArea.appendText(String.format("Окупаемость: %.1f мес.\n", result_wind.getPaybackPeriodMonths()));
            outputArea.appendText("Соответствует требованиям: " + (result_wind.isMeetsRequirements() ? "Да" : "Нет") + "\n");
            outputArea.appendText(String.format("Средняя скорость ветра: %.2f м/с\n", result_wind.getAverageWindSpeed()));
            outputArea.appendText("Примечание: " + result_wind.getNotes());

            plotCharts(dataList);
        }
        catch (NumberFormatException e)
        {
            outputArea.setText("Ошибка: введите корректные координаты.");
        }
        catch (Exception e)
        {
            outputArea.setText("Ошибка при анализе данных: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void plotCharts(List<WeatherData> dataList)
    {
        temperatureChart.getData().clear();
        windChart.getData().clear();
        solarChart.getData().clear();

        XYChart.Series<Number, Number> tempSeries = new XYChart.Series<>();
        tempSeries.setName("Температура (°C)");

        XYChart.Series<Number, Number> windSeries = new XYChart.Series<>();
        windSeries.setName("Скорость ветра (м/с)");

        XYChart.Series<Number, Number> solarSeries = new XYChart.Series<>();
        solarSeries.setName("Солнечная радиация (Вт/м²)");

        for (int i = 0; i < dataList.size(); i++) {
            WeatherData data = dataList.get(i);
            tempSeries.getData().add(new XYChart.Data<>(i + 1, data.getTemperature()));
            windSeries.getData().add(new XYChart.Data<>(i + 1, data.getWindSpeed()));
            solarSeries.getData().add(new XYChart.Data<>(i + 1, data.getSolarRadiation()));
        }

        temperatureChart.getData().add(tempSeries);
        windChart.getData().add(windSeries);
        solarChart.getData().add(solarSeries);
    }

    @FXML
    private void onSaveFavorite()
    {
        try {
            double lat = Double.parseDouble(latitudeField.getText());
            double lon = Double.parseDouble(longitudeField.getText());

            Location location = new Location(lat, lon);
            FavouriteRepository.save(location);

            statusLabel.setText("Координаты сохранены в избранное.");
        } catch (NumberFormatException e) {
            statusLabel.setText("Ошибка: введите корректные координаты.");
        }
    }

    @FXML
    private void onLoadFavorite()
    {
        List<Location> favourites = FavouriteRepository.load();

        if (favourites.isEmpty()) {
            statusLabel.setText("Список избранного пуст.");
            return;
        }

        ChoiceDialog<Location> dialog = new ChoiceDialog<>(favourites.get(0), favourites);
        dialog.setTitle("Выбор из избранного");
        dialog.setHeaderText("Выберите координаты");
        dialog.setContentText("Избранное:");

        dialog.showAndWait().ifPresent(selected -> {
            latitudeField.setText(String.valueOf(selected.getLatitude()));
            longitudeField.setText(String.valueOf(selected.getLongitude()));
            statusLabel.setText("Загружены координаты из избранного.");
        });
    }

    @FXML
    private void onOpenSettings()
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/settings_view.fxml"));
            Scene scene = new Scene(loader.load());

            Stage settingsStage = new Stage();
            settingsStage.setTitle("Настройки проекта");
            settingsStage.setScene(scene);
            settingsStage.initModality(Modality.APPLICATION_MODAL); // блокирует основное окно
            settingsStage.showAndWait(); // ждёт закрытия окна

            statusLabel.setText("Настройки обновлены");
        } catch (IOException e) {
            statusLabel.setText("Не удалось открыть окно настроек.");
            e.printStackTrace();
        }
    }

    @FXML
    private void onOpenInfo()
    {

        outputArea.setText("Функция 'Информация' ещё не реализована.");
    }
}
package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.WeatherData;
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
    private void onAnalyze() {
        try {
            double lat = Double.parseDouble(latitudeField.getText());
            double lon = Double.parseDouble(longitudeField.getText());

            List<WeatherData> dataList = WeatherApiService.getHistoricalDataForYear(lat, lon);

            String analysisResult = WeatherApiService.analyzeWeatherData(dataList);

            outputArea.clear();
            outputArea.appendText(analysisResult);
        } catch (NumberFormatException e) {
            outputArea.setText("Ошибка: введите корректные координаты.");
        } catch (Exception e) {
            outputArea.setText("Ошибка при анализе данных: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void onSaveFavorite()
    {

        outputArea.setText("Функция 'Сохранить в избранное' ещё не реализована.");
    }

    @FXML
    private void onLoadFavorite()
    {

        outputArea.setText("Функция 'Открыть из избранного' ещё не реализована.");
    }

    @FXML
    private void onOpenSettings()
    {

        statusLabel.setText("Окно настроек ещё не подключено");
    }

    @FXML
    private void onOpenInfo()
    {

        outputArea.setText("Функция 'Информация' ещё не реализована.");
    }
}
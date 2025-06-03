package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MainController {

    @FXML
    private TextField latitudeField;

    @FXML
    private TextField longitudeField;

    @FXML
    private TextArea outputArea;

    @FXML
    private Label statusLabel;

    @FXML
    private void onAnalyze()
    {
        String latText = latitudeField.getText();
        String lonText = longitudeField.getText();

        try {
            double lat = Double.parseDouble(latText);
            double lon = Double.parseDouble(lonText);

            statusLabel.setText("Запрос данных...");
            // В будущем: вызов WeatherApiService и анализ
            outputArea.setText("Заглушка: анализ координат (" + lat + ", " + lon + ")");
            statusLabel.setText("Анализ завершён");

        } catch (NumberFormatException e) {
            outputArea.setText("Ошибка: Введите корректные числовые значения широты и долготы.");
            statusLabel.setText("Ошибка ввода");
        }
    }

    @FXML
    private void onSaveFavorite() {
        outputArea.setText("Функция 'Сохранить в избранное' ещё не реализована.");
    }

    @FXML
    private void onLoadFavorite() {
        outputArea.setText("Функция 'Открыть из избранного' ещё не реализована.");
    }

    @FXML
    private void onOpenSettings() {
        outputArea.setText("Функция 'Настройки' ещё не реализована.");
    }

    @FXML
    private void onOpenInfo() {
        outputArea.setText("Функция 'Информация' ещё не реализована.");
    }
}
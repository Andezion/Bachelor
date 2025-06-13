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
    private void onAnalyze()
    {
        try
        {
            double lat = Double.parseDouble(latitudeField.getText());
            double lon = Double.parseDouble(longitudeField.getText());

            List<WeatherData> dataList = WeatherApiService.getHistoricalDataForYear(lat, lon);

            ProjectSettings settings = ProjectSettings.load();

            WeatherAnalyzerService analyzer = new WeatherAnalyzerService();
            AnalysisResult result_solar = analyzer.analyzeSolarPowerPayback(dataList, settings);
            AnalysisResult result_wind = analyzer.analyzeWindPowerPayback(dataList, settings);

            outputArea.clear();
            outputArea.appendText("Estimating the payback period of a solar power plant:\n");
            outputArea.appendText(String.format("Payback: %.1f month.\n", result_solar.getPaybackPeriodMonths()));
            outputArea.appendText("Meets requirements: " + (result_solar.isMeetsRequirements() ? "Yes" : "No") + "\n");
            outputArea.appendText("Sunny days: " + result_solar.getSunnyDays() + "\n");
            outputArea.appendText("Rainy days: " + result_solar.getRainyDays() + "\n");
            outputArea.appendText(String.format("Average temperature: %.2f°C\n", result_solar.getAverageTemperature()));
            outputArea.appendText("Note: " + result_solar.getNotes() + "\n\n");

            outputArea.appendText("Estimating the payback of a wind power plant:\n");
            outputArea.appendText(String.format("Payback: %.1f month.\n", result_wind.getPaybackPeriodMonths()));
            outputArea.appendText("Meets requirements: " + (result_wind.isMeetsRequirements() ? "Yes" : "No") + "\n");
            outputArea.appendText(String.format("Average wind speed: %.2f m/s\n", result_wind.getAverageWindSpeed()));
            outputArea.appendText("Note: " + result_wind.getNotes());

            plotCharts(dataList);
        }
        catch (NumberFormatException e)
        {
            outputArea.setText("Error: Please enter correct coordinates!");
        }
        catch (Exception e)
        {
            outputArea.setText("Error in data analysis: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void plotCharts(List<WeatherData> dataList)
    {
        temperatureChart.getData().clear();
        windChart.getData().clear();
        solarChart.getData().clear();

        XYChart.Series<Number, Number> tempSeries = new XYChart.Series<>();
        tempSeries.setName("Temperature (°C)");

        XYChart.Series<Number, Number> windSeries = new XYChart.Series<>();
        windSeries.setName("Wind speed (m/s)");

        XYChart.Series<Number, Number> solarSeries = new XYChart.Series<>();
        solarSeries.setName("Solar radiation (W/m²)");

        for (int i = 0; i < dataList.size(); i++)
        {
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
        try
        {
            double lat = Double.parseDouble(latitudeField.getText());
            double lon = Double.parseDouble(longitudeField.getText());

            Location location = new Location(lat, lon);
            FavouriteRepository.save(location);

            statusLabel.setText("Coordinates saved to favorites!");
        }
        catch (NumberFormatException e)
        {
            statusLabel.setText("Error: Please enter correct coordinates!");
        }
    }

    @FXML
    private void onLoadFavorite()
    {
        List<Location> favourites = FavouriteRepository.load();

        if (favourites.isEmpty())
        {
            statusLabel.setText("Your favorites list is empty!");
            return;
        }

        ChoiceDialog<Location> dialog = new ChoiceDialog<>(favourites.get(0), favourites);
        dialog.setTitle("Select from Favorites");
        dialog.setHeaderText("Select coordinates");
        dialog.setContentText("Featured:");

        dialog.showAndWait().ifPresent(selected -> {
            latitudeField.setText(String.valueOf(selected.getLatitude()));
            longitudeField.setText(String.valueOf(selected.getLongitude()));
            statusLabel.setText("Coordinates from favorites loaded!");
        });
    }

    @FXML
    private void onOpenSettings()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/settings_view.fxml"));
            Scene scene = new Scene(loader.load());

            Stage settingsStage = new Stage();
            settingsStage.setTitle("Project Settings");
            settingsStage.setScene(scene);
            settingsStage.initModality(Modality.APPLICATION_MODAL);
            settingsStage.showAndWait();

            statusLabel.setText("Settings updated");
        }
        catch (IOException e)
        {
            statusLabel.setText("Failed to open settings window!");
            e.printStackTrace();
        }
    }

    @FXML
    private void onOpenInfo()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/info_view.fxml"));
            Scene scene = new Scene(loader.load());

            Stage infoStage = new Stage();
            infoStage.setTitle("Project information");
            infoStage.setScene(scene);
            infoStage.initModality(Modality.APPLICATION_MODAL);
            infoStage.showAndWait();
        }
        catch (IOException e)
        {
            statusLabel.setText("Failed to open information window!");
            e.printStackTrace();
        }
    }
}
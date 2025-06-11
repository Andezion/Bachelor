package controller;

import model.ProjectSettings;
import service.ProjectSettingsService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class SettingsController {

    @FXML private TextField workerCostField;
    @FXML private TextField equipmentCostField;
    @FXML private TextField paybackPeriodField;
    @FXML private Label statusLabel;

    @FXML private TextField solarEfficiencyField;
    @FXML private TextField solarInstallationCostField;
    @FXML private TextField electricityTariffField;
    @FXML private TextField targetPaybackField;

    @FXML private TextField tiltAngleField;
    @FXML private TextField azimuthField;

    @FXML private TextField windTurbineCost;          // стоимость ветрогенератора
    @FXML private TextField turbineRadius;            // радиус лопастей, м
    @FXML private TextField turbineEfficiency;        // КПД (0.35–0.5)
    @FXML private TextField electricityTariff_wind;

    @FXML private TextField panelAreaField;

    private final ProjectSettingsService settingsService = ProjectSettingsService.getInstance();

    @FXML
    private void initialize() {
        ProjectSettings current = settingsService.getSettings();
        if (current != null) {
            workerCostField.setText(String.valueOf(current.getWorkerCost()));
            equipmentCostField.setText(String.valueOf(current.getEquipmentCost()));
            paybackPeriodField.setText(String.valueOf(current.getDesiredPaybackPeriodYears()));

            solarEfficiencyField.setText(String.valueOf(current.getSolarEfficiency() * 100)); // в %
            solarInstallationCostField.setText(String.valueOf(current.getSolarInstallationCost()));
            electricityTariffField.setText(String.valueOf(current.getElectricityTariff_solar()));
            targetPaybackField.setText(String.valueOf(current.getTargetPaybackMonths()));

            windTurbineCost.setText(String.valueOf(current.getWindTurbineCost()));
            turbineRadius.setText(String.valueOf(current.getTurbineRadius()));
            turbineEfficiency.setText(String.valueOf(current.getTurbineEfficiency()));
            electricityTariff_wind.setText(String.valueOf(current.getElectricityTariff_wind()));

            panelAreaField.setText(String.valueOf(current.getPanelArea()));
        }
    }

    @FXML
    private void onSave() {
        try {
            double workerCost = Double.parseDouble(workerCostField.getText());
            double equipmentCost = Double.parseDouble(equipmentCostField.getText());
            int paybackYears = Integer.parseInt(paybackPeriodField.getText());

            double solarEfficiency = Double.parseDouble(solarEfficiencyField.getText()) / 100.0;
            double solarInstallationCost = Double.parseDouble(solarInstallationCostField.getText());
            double electricityTariff = Double.parseDouble(electricityTariffField.getText());
            double targetPaybackMonths = Double.parseDouble(targetPaybackField.getText());

            double tilt = Double.parseDouble(tiltAngleField.getText());
            double azimuth = Double.parseDouble(azimuthField.getText());

            double windCost = Double.parseDouble(windTurbineCost.getText());
            double radius = Double.parseDouble(turbineRadius.getText());
            double efficiency = Double.parseDouble(turbineEfficiency.getText());
            double tariffWind = Double.parseDouble(electricityTariff_wind.getText());

            double panelArea = Double.parseDouble(panelAreaField.getText());


            ProjectSettings settings = new ProjectSettings(
                    workerCost, equipmentCost, paybackYears,
                    solarEfficiency, solarInstallationCost, electricityTariff, targetPaybackMonths,
                    tilt, azimuth
            );


            settings.setWindTurbineCost(windCost);
            settings.setTurbineRadius(radius);
            settings.setTurbineEfficiency(efficiency);
            settings.setElectricityTariff_wind(tariffWind);
            settings.setPanelArea(panelArea);

            settingsService.setSettings(settings);
            settings.save();
            statusLabel.setText("Настройки сохранены успешно!");
        }
        catch (NumberFormatException e)
        {
            statusLabel.setText("Ошибка: проверьте правильность ввода.");
        }
    }
}
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
            electricityTariffField.setText(String.valueOf(current.getElectricityTariff()));
            targetPaybackField.setText(String.valueOf(current.getTargetPaybackMonths()));
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

            ProjectSettings settings = new ProjectSettings(
                    workerCost, equipmentCost, paybackYears,
                    solarEfficiency, solarInstallationCost, electricityTariff, targetPaybackMonths
            );
            //ProjectSettings settings = new ProjectSettings(workerCost, equipmentCost, paybackYears);
            settingsService.setSettings(settings);

            statusLabel.setText("Настройки сохранены успешно!");
        } catch (NumberFormatException e) {
            statusLabel.setText("Ошибка: проверьте правильность ввода.");
        }
    }
}
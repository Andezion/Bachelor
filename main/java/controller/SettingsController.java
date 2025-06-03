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

    private final ProjectSettingsService settingsService = ProjectSettingsService.getInstance();

    @FXML
    private void initialize() {
        ProjectSettings current = settingsService.getSettings();
        if (current != null) {
            workerCostField.setText(String.valueOf(current.getWorkerCost()));
            equipmentCostField.setText(String.valueOf(current.getEquipmentCost()));
            paybackPeriodField.setText(String.valueOf(current.getDesiredPaybackPeriodYears()));
        }
    }

    @FXML
    private void onSave() {
        try {
            double workerCost = Double.parseDouble(workerCostField.getText());
            double equipmentCost = Double.parseDouble(equipmentCostField.getText());
            int paybackYears = Integer.parseInt(paybackPeriodField.getText());

            ProjectSettings settings = new ProjectSettings(workerCost, equipmentCost, paybackYears);
            settingsService.setSettings(settings);

            statusLabel.setText("Настройки сохранены успешно!");
        } catch (NumberFormatException e) {
            statusLabel.setText("Ошибка: проверьте правильность ввода.");
        }
    }
}
package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import model.ProjectSettings;

public class InfoController
{
    @FXML
    private TextArea infoArea;

    @FXML
    public void initialize()
    {
        ProjectSettings settings = ProjectSettings.load();

        String orientation = azimuthToText(settings.getPanelAzimuthAngle());

        String info = String.format("""
            🔧 Current project settings:

            💰 Financial parameters:
            • Cost of workers: %.2f UAH.
            • Cost of equipment: %.2f UAH.
            • Desired payback period: %d years (%.0f months)

            ☀️ Solar installation parameters:
            • Panel efficiency: %.2f %%
            • Installation cost: %.0f UAH/kW
            • Electricity tariff (solar): %.2f UAH/kWh
            • Electricity tariff (wind): %.2f UAH/kWh

            📐 Panel layout:
            • Orientation: %s (%.1f°)
            • Tilt angle: %.1f°
            • Size: %.1f m²
            """,
                settings.getWorkerCost(),
                settings.getEquipmentCost(),
                settings.getDesiredPaybackPeriodYears(),
                settings.getTargetPaybackMonths(),
                settings.getSolarEfficiency() * 100,
                settings.getSolarInstallationCost(),
                settings.getElectricityTariff_solar(),
                settings.getElectricityTariff_wind(),
                orientation,
                settings.getPanelAzimuthAngle(),
                settings.getPanelTiltAngle(),
                settings.getPanelArea()
        );

        infoArea.setText(info);
    }

    private String azimuthToText(double angle)
    {
        if (angle >= 337.5 || angle < 22.5) return "north";
        if (angle >= 22.5 && angle < 67.5) return "northeast";
        if (angle >= 67.5 && angle < 112.5) return "east";
        if (angle >= 112.5 && angle < 157.5) return "southeast";
        if (angle >= 157.5 && angle < 202.5) return "south";
        if (angle >= 202.5 && angle < 247.5) return "southwest";
        if (angle >= 247.5 && angle < 292.5) return "west";
        if (angle >= 292.5 && angle < 337.5) return "northwest";
        return "unknown";
    }
}

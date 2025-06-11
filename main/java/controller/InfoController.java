package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import model.ProjectSettings;

public class InfoController
{
    @FXML
    private TextArea infoArea;

    @FXML
    public void initialize() {
        ProjectSettings settings = ProjectSettings.load();

        // Преобразуем азимут в описание
        String orientation = azimuthToText(settings.getPanelAzimuthAngle());

        String info = String.format("""
            🔧 Текущие настройки проекта:

            💰 Финансовые параметры:
            • Стоимость рабочих: %.2f грн.
            • Стоимость оборудования: %.2f грн.
            • Желаемый срок окупаемости: %d лет (%.0f месяцев)

            ☀️ Параметры солнечной установки:
            • Эффективность панелей: %.2f %%
            • Стоимость установки: %.0f грн/кВт
            • Тариф на электроэнергию(солнце): %.2f грн/кВт⋅ч
            • Тариф на электроэнергию(ветер): %.2f грн/кВт⋅ч

            📐 Расположение панелей:
            • Ориентация: %s (%.1f°)
            • Угол наклона: %.1f°
            • Размер: %.1f м²
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

    private String azimuthToText(double angle) {
        if (angle >= 337.5 || angle < 22.5) return "север";
        if (angle >= 22.5 && angle < 67.5) return "северо-восток";
        if (angle >= 67.5 && angle < 112.5) return "восток";
        if (angle >= 112.5 && angle < 157.5) return "юго-восток";
        if (angle >= 157.5 && angle < 202.5) return "юг";
        if (angle >= 202.5 && angle < 247.5) return "юго-запад";
        if (angle >= 247.5 && angle < 292.5) return "запад";
        if (angle >= 292.5 && angle < 337.5) return "северо-запад";
        return "неизвестно";
    }
}

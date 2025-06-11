package model;

import util.JsonUtil;

import java.io.File;

public class ProjectSettings
{
    private double workerCost;
    private double equipmentCost;
    private int desiredPaybackPeriodYears;

    private double solarEfficiency;            // от 0.0 до 1.0
    private double solarInstallationCost;
    private double electricityTariff_solar;
    private double targetPaybackMonths;

    private double panelTiltAngle;      // в градусах
    private double panelAzimuthAngle;

    private double windTurbineCost;          // стоимость ветрогенератора
    private double turbineRadius;            // радиус лопастей, м
    private double turbineEfficiency;        // КПД (0.35–0.5)
    private double electricityTariff_wind;        // тариф за кВт⋅ч, например 0.05

    private double panelArea;        // м²


    private static final File FILE = new File("project_settings.json");

    public ProjectSettings()
    {

    }

    public ProjectSettings(double workerCost, double equipmentCost, int desiredPaybackPeriodYears,
                           double solarEfficiency,
                           double solarInstallationCost,
                           double electricityTariff_solar,
                           double targetPaybackMonths,
                           double panelTiltAngle,
                           double panelAzimuthAngle)
    {
        this.workerCost = workerCost;
        this.equipmentCost = equipmentCost;
        this.desiredPaybackPeriodYears = desiredPaybackPeriodYears;

        this.solarEfficiency = solarEfficiency;
        this.solarInstallationCost = solarInstallationCost;
        this.electricityTariff_solar = electricityTariff_solar;
        this.targetPaybackMonths = targetPaybackMonths;

        this.panelTiltAngle = panelTiltAngle;
        this.panelAzimuthAngle = panelAzimuthAngle;
    }

    public double getPanelArea()
    {
        return panelArea;
    }

    public void setPanelArea(double panelArea)
    {
        this.panelArea = panelArea;
    }

    public double getWindTurbineCost()
    {
        return windTurbineCost;
    }

    public void setWindTurbineCost(double windTurbineCost)
    {
        this.windTurbineCost = windTurbineCost;
    }

    public double getTurbineRadius() {
        return turbineRadius;
    }

    public void setTurbineRadius(double turbineRadius)
    {
        this.turbineRadius = turbineRadius;
    }

    public double getElectricityTariff_wind() {
        return electricityTariff_wind;
    }

    public void setElectricityTariff_wind(double electricityTariff_wind) {
        this.electricityTariff_wind = electricityTariff_wind;
    }

    public double getTurbineEfficiency() {
        return turbineEfficiency;
    }

    public void setTurbineEfficiency(double turbineEfficiency) {
        this.turbineEfficiency = turbineEfficiency;
    }

    public double getPanelTiltAngle()
    {
        return panelTiltAngle;
    }

    public void setPanelTiltAngle(double panelTiltAngle)
    {
        this.panelTiltAngle = panelTiltAngle;
    }

    public double getPanelAzimuthAngle()
    {
        return panelAzimuthAngle;
    }

    public void setPanelAzimuthAngle(double panelAzimuthAngle)
    {
        this.panelAzimuthAngle = panelAzimuthAngle;
    }

    public double getWorkerCost() {
        return workerCost;
    }

    public void setWorkerCost(double workerCost) {
        this.workerCost = workerCost;
    }

    public double getEquipmentCost() {
        return equipmentCost;
    }

    public void setEquipmentCost(double equipmentCost) {
        this.equipmentCost = equipmentCost;
    }

    public int getDesiredPaybackPeriodYears() {
        return desiredPaybackPeriodYears;
    }

    public void setDesiredPaybackPeriodYears(int desiredPaybackPeriodYears) {
        this.desiredPaybackPeriodYears = desiredPaybackPeriodYears;
    }

    public double getSolarEfficiency() {
        return solarEfficiency;
    }

    public void setSolarEfficiency(double solarEfficiency) {
        this.solarEfficiency = solarEfficiency;
    }

    public double getSolarInstallationCost() {
        return solarInstallationCost;
    }

    public void setSolarInstallationCost(double solarInstallationCost) {
        this.solarInstallationCost = solarInstallationCost;
    }

    public double getElectricityTariff_solar() {
        return electricityTariff_solar;
    }

    public void setElectricityTariff_solar(double electricityTariff_solar) {
        this.electricityTariff_solar = electricityTariff_solar;
    }

    public double getTargetPaybackMonths() {
        return targetPaybackMonths;
    }

    public void setTargetPaybackMonths(double targetPaybackMonths) {
        this.targetPaybackMonths = targetPaybackMonths;
    }

    public void save() {
        JsonUtil.writeToFile(FILE, this);
    }

    public static ProjectSettings load()
    {
        if (!FILE.exists())
        {
            // Возвращаем настройки по умолчанию, если файл не найден
            return new ProjectSettings(
                    10000.0,
                    150000.0,
                    7,
                    0.18,
                    150000.0,
                    6.5,
                    84,
                    150.0,
                    30.0
            );
        }
        return JsonUtil.readObjectFromFile(FILE, ProjectSettings.class);
    }
}

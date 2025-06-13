package model;

import util.JsonUtil;

import java.io.File;

public class ProjectSettings
{
    private double workerCost;
    private double equipmentCost;
    private int desiredPaybackPeriodYears;

    private double solarEfficiency;
    private double solarInstallationCost;
    private double electricityTariff_solar;
    private double targetPaybackMonths;

    private double panelTiltAngle;
    private double panelAzimuthAngle;

    private double windTurbineCost;
    private double turbineRadius;
    private double turbineEfficiency;
    private double electricityTariff_wind;

    private double panelArea;


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

    public double getPanelArea() { return panelArea; }
    public double getWindTurbineCost() { return windTurbineCost; }
    public double getTurbineRadius() { return turbineRadius; }
    public double getElectricityTariff_wind() { return electricityTariff_wind; }
    public double getTurbineEfficiency() { return turbineEfficiency; }
    public double getPanelTiltAngle() { return panelTiltAngle; }
    public double getPanelAzimuthAngle() { return panelAzimuthAngle; }
    public double getWorkerCost() { return workerCost; }
    public double getEquipmentCost() { return equipmentCost; }
    public int getDesiredPaybackPeriodYears() { return desiredPaybackPeriodYears; }
    public double getSolarEfficiency() { return solarEfficiency; }
    public double getSolarInstallationCost() { return solarInstallationCost; }
    public double getTargetPaybackMonths() { return targetPaybackMonths; }
    public double getElectricityTariff_solar() { return electricityTariff_solar; }


    public void setPanelArea(double panelArea) { this.panelArea = panelArea; }
    public void setWindTurbineCost(double windTurbineCost) { this.windTurbineCost = windTurbineCost; }
    public void setTurbineRadius(double turbineRadius) { this.turbineRadius = turbineRadius; }
    public void setElectricityTariff_wind(double electricityTariff_wind) { this.electricityTariff_wind = electricityTariff_wind; }
    public void setTurbineEfficiency(double turbineEfficiency) { this.turbineEfficiency = turbineEfficiency; }
    public void setPanelTiltAngle(double panelTiltAngle) { this.panelTiltAngle = panelTiltAngle; }
    public void setPanelAzimuthAngle(double panelAzimuthAngle) { this.panelAzimuthAngle = panelAzimuthAngle; }
    public void setWorkerCost(double workerCost) {
        this.workerCost = workerCost;
    }
    public void setEquipmentCost(double equipmentCost) { this.equipmentCost = equipmentCost; }
    public void setDesiredPaybackPeriodYears(int desiredPaybackPeriodYears) { this.desiredPaybackPeriodYears = desiredPaybackPeriodYears; }
    public void setSolarEfficiency(double solarEfficiency) { this.solarEfficiency = solarEfficiency; }
    public void setSolarInstallationCost(double solarInstallationCost) { this.solarInstallationCost = solarInstallationCost; }
    public void setElectricityTariff_solar(double electricityTariff_solar) { this.electricityTariff_solar = electricityTariff_solar; }
    public void setTargetPaybackMonths(double targetPaybackMonths) { this.targetPaybackMonths = targetPaybackMonths; }

    public void save()
    {

        JsonUtil.writeToFile(FILE, this);
    }

    public static ProjectSettings load()
    {
        if (!FILE.exists())
        {
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

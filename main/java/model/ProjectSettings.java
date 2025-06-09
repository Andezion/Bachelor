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
    private double electricityTariff;
    private double targetPaybackMonths;        // в месяцах

    private static final File FILE = new File("project_settings.json");

    public ProjectSettings(double workerCost, double equipmentCost, int desiredPaybackPeriodYears,
                           double solarEfficiency,
                           double solarInstallationCost,
                           double electricityTariff,
                           double targetPaybackMonths)
    {
        this.workerCost = workerCost;
        this.equipmentCost = equipmentCost;
        this.desiredPaybackPeriodYears = desiredPaybackPeriodYears;

        this.solarEfficiency = solarEfficiency;
        this.solarInstallationCost = solarInstallationCost;
        this.electricityTariff = electricityTariff;
        this.targetPaybackMonths = targetPaybackMonths;
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

    public double getElectricityTariff() {
        return electricityTariff;
    }

    public void setElectricityTariff(double electricityTariff) {
        this.electricityTariff = electricityTariff;
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

    public static ProjectSettings load() {
        if (!FILE.exists()) {
            // Возвращаем настройки по умолчанию, если файла нет
            return new ProjectSettings(10000.0, 20000.0, 5, 0, 0, 0, 0);
        }
        return (ProjectSettings) JsonUtil.readFromFile(FILE, ProjectSettings.class);
    }
}

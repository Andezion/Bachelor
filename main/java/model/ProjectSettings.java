package model;

import util.JsonUtil;

import java.io.File;

public class ProjectSettings
{
    private double workerCost;
    private double equipmentCost;
    private int desiredPaybackPeriodYears;

    private static final File FILE = new File("project_settings.json");

    public ProjectSettings(double workerCost, double equipmentCost, int desiredPaybackPeriodYears)
    {
        this.workerCost = workerCost;
        this.equipmentCost = equipmentCost;
        this.desiredPaybackPeriodYears = desiredPaybackPeriodYears;
    }

    public double getWorkerCost()
    {
        return workerCost;
    }
    public void setWorkerCost(double workerCost)
    {
        this.workerCost = workerCost;
    }

    public double getEquipmentCost()
    {
        return equipmentCost;
    }
    public void setEquipmentCost(double equipmentCost)
    {
        this.equipmentCost = equipmentCost;
    }

    public int getDesiredPaybackPeriodYears()
    {
        return desiredPaybackPeriodYears;
    }
    public void setDesiredPaybackPeriodYears(int desiredPaybackPeriodYears)
    {
        this.desiredPaybackPeriodYears = desiredPaybackPeriodYears;
    }

    public void save() {
        JsonUtil.writeToFile(FILE, this);
    }

    public static ProjectSettings load() {
        if (!FILE.exists()) {
            // Возвращаем настройки по умолчанию, если файла нет
            return new ProjectSettings(10000.0, 20000.0, 5);
        }
        return (ProjectSettings) JsonUtil.readFromFile(FILE, ProjectSettings.class);
    }
}

package model;

public class ProjectSettings
{
    private double workerCost;
    private double equipmentCost;
    private int desiredPaybackPeriodYears;

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

}

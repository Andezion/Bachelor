package model;

public class ProjectSettings
{
    private final double workerCost;
    private final double equipmentCost;
    private final int desiredPaybackPeriodYears;

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

    public double getEquipmentCost()
    {
        return equipmentCost;
    }

    public int getDesiredPaybackPeriodYears()
    {
        return desiredPaybackPeriodYears;
    }

    public double getTotalInvestment()
    {
        return workerCost + equipmentCost;
    }
}

package model;

public class AnalysisResult
{
    private double paybackPeriodMonths;
    private boolean meetsRequirements;
    private String notes;

    private int sunnyDays;
    private int rainyDays;
    private double averageTemperature;
    private double averageWindSpeed;

    public AnalysisResult(
            double paybackPeriodMonths,
            boolean meetsRequirements,
            String notes,
            int sunnyDays,
            int rainyDays,
            double averageTemperature,
            double averageWindSpeed
    )
    {
        this.paybackPeriodMonths = paybackPeriodMonths;
        this.meetsRequirements = meetsRequirements;
        this.notes = notes;
        this.sunnyDays = sunnyDays;
        this.rainyDays = rainyDays;
        this.averageTemperature = averageTemperature;
        this.averageWindSpeed = averageWindSpeed;
    }

    public double getPaybackPeriodMonths() { return paybackPeriodMonths; }
    public boolean isMeetsRequirements() { return meetsRequirements; }
    public String getNotes() { return notes; }
    public int getSunnyDays() { return sunnyDays; }
    public int getRainyDays() { return rainyDays; }
    public double getAverageTemperature() { return averageTemperature; }
    public double getAverageWindSpeed() { return averageWindSpeed; }

    public void setPaybackPeriodMonths(double paybackPeriodMonths) { this.paybackPeriodMonths = paybackPeriodMonths; }
    public void setMeetsRequirements(boolean meetsRequirements) { this.meetsRequirements = meetsRequirements; }
    public void setNotes(String notes) { this.notes = notes; }
    public void setSunnyDays(int sunnyDays) { this.sunnyDays = sunnyDays; }
    public void setRainyDays(int rainyDays) { this.rainyDays = rainyDays; }
    public void setAverageTemperature(double averageTemperature) { this.averageTemperature = averageTemperature; }
    public void setAverageWindSpeed(double averageWindSpeed) { this.averageWindSpeed = averageWindSpeed; }
}

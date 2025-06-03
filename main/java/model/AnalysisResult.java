package model;

public class AnalysisResult
{
    private double estimatedPaybackTime;
    private boolean meetsClientDeadline;

    public AnalysisResult(double estimatedPaybackTime, boolean meetsClientDeadline)
    {
        this.estimatedPaybackTime = estimatedPaybackTime;
        this.meetsClientDeadline = meetsClientDeadline;
    }

    public double getEstimatedPaybackTime()
    {
        return estimatedPaybackTime;
    }

    public boolean isMeetsClientDeadline()
    {
        return meetsClientDeadline;
    }
}
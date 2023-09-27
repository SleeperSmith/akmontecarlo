package com.bitclouded.akmontecarlo;

public class ReportResult {
    public double averageRollCount;
    public double averageSixStarCount;
    public int maxRollCount;
    public int maxSixStarCount;

    @Override
    public String toString() 
    {
        return new StringBuilder()
            .append("Average Rolls: ").append(averageRollCount)
            .append(System.lineSeparator())

            .append("Max Rolls: ").append(maxRollCount)
            .append(System.lineSeparator())

            .append("Average number of six stars: ").append(averageSixStarCount)
            .append(System.lineSeparator())

            .append("Max number of six stars: ").append(maxSixStarCount)
            .append(System.lineSeparator())

            .toString(); 
    }
}

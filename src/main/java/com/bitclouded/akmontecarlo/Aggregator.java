package com.bitclouded.akmontecarlo;

import java.util.ArrayList;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

@Component
public class Aggregator implements Aggregatable {

    private ArrayList<ReportResult> intermediateResults = new ArrayList<>();
    
    @Override
    public void submitResult(ArrayList<ArrayList<OnceResult>> result) {
        final Supplier<IntStream> rollCountList = () -> result.stream()
                .mapToInt(a -> a.stream().mapToInt(b -> b.count).sum());
        final Supplier<IntStream> sixStarCountList = () -> result.stream()
                .mapToInt(a -> a.size());
        final var resultSize = (double)result.size();

        var inteintermediateResult = new ReportResult(){{
            averageRollCount = rollCountList.get().sum() / resultSize;
            averageSixStarCount = sixStarCountList.get().sum() / resultSize;
            maxRollCount = rollCountList.get().max().getAsInt();
            maxSixStarCount = sixStarCountList.get().max().getAsInt();
        }};
        synchronized (inteintermediateResult) {
            intermediateResults.add(inteintermediateResult);
        }
    }

    @Override
    public ReportResult reduce() {

        var count = (double)intermediateResults.stream().count();
        var result = new ReportResult(){{
            averageRollCount = intermediateResults.stream()
                .mapToDouble(a -> a.averageRollCount)
                .sum() / count;
            averageSixStarCount = intermediateResults.stream()
                .mapToDouble(a -> a.averageSixStarCount)
                .sum() / count;
            maxRollCount = intermediateResults.stream()
                .mapToInt(a -> a.maxRollCount)
                .max().getAsInt();
            maxSixStarCount = intermediateResults.stream()
                .mapToInt(a -> a.maxSixStarCount)
                .max().getAsInt();
        }};
        return result;
    }
}

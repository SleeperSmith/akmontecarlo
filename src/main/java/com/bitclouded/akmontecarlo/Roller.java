package com.bitclouded.akmontecarlo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Roller extends Thread {
    private static Random seed = new Random(Calendar.getInstance().getTimeInMillis());
    // Accessor to allow unit testing.
    public static nextIntFunctionInterface random = (max) -> seed.nextInt(max) + 1;
    // Function interface cos Java can't have Callable<int>
    public static interface nextIntFunctionInterface {
        int nextInt(int max);
    }

    @Autowired
    private Aggregatable aggregator;

    public int runCount;
    public void run() {
        var allResults = new ArrayList<ArrayList<OnceResult>>();
        for (var i = 0; i < runCount; i++) {
            var results = new ArrayList<OnceResult>();
            while(true) {
                var result = rollOnce();
                results.add(result);
                // Roll until getting the desired operator.
                if (result.outcome == OnceResult.Result.SUCCESS)
                    break;
            }
            allResults.add(results);
        }
        aggregator.submitResult(allResults);
    }

    // Rolls for one six star operator.
    public OnceResult rollOnce() {
        var result = new OnceResult();
        while (result.outcome == null) {
            var roll = (int)random.nextInt(100); // 1-100 in a % chance.

            // Pity chance. 2% extra chance beyond 50th roll.
            var sixStarChance = result.count > 49
                ? 2 + 2 * (result.count - 49)
                : 2;
            
            result.count++;
            if (roll > sixStarChance) continue;

            // Allow customisation later.
            var rollSixStar = (int)random.nextInt(2); // 1-2 for 50% chance.
            result.outcome = rollSixStar == 1
                ? OnceResult.Result.SUCCESS
                : OnceResult.Result.OFF_BANNER;
        }
        return result;
    }
}

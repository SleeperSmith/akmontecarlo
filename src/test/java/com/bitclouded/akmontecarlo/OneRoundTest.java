package com.bitclouded.akmontecarlo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class OneRoundTest {
    
    @Test
    public void runSuccess() throws Exception {
        var testTarget = new Roller();
        Roller.random = (max) -> 1;
        // ^ will be called twice.
        // First roll is for gacha roll. Second roll is for 6* chance.

        // Act
        var result = testTarget.rollOnce();

        // Assert
        assertTrue("Result is Success", result.outcome == OnceResult.Result.SUCCESS);
        assertEquals("Roll count is 1", 1, result.count);
    }

    @Test
    public void runPity() throws Exception {
        var testTarget = new Roller();
        Roller.random = (max) -> max != 2 ? 3 : 1;
        // Keep returning three until pity chance kicks in.

        // Act
        var result = testTarget.rollOnce();

        // Assert
        assertTrue("Result is Success", result.outcome == OnceResult.Result.SUCCESS);
        assertEquals("Roll count is 51", 51, result.count);
    }
}

package com.bitclouded.akmontecarlo;

import org.springframework.stereotype.Component;

@Component
public class RunnerOptions {
    public int runCount = 100000;
    public int threadCount = 4;
}

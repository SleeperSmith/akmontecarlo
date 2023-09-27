package com.bitclouded.akmontecarlo;

import java.util.ArrayList;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Runner {

    @Autowired
    BeanFactory factory;

    @Autowired
    RunnerOptions options;

    @Autowired
    Aggregatable aggregatedResult;

    public void run() throws Exception {
        var runners = new ArrayList<Roller>();
        for (var i = 0; i < 4; i++) {
            var thread = factory.getBean(Roller.class);
            thread.runCount = options.runCount / options.threadCount;
            thread.start();
            runners.add(thread);
        }
        var exceptions = runners
            .stream()
            .map(a -> {
                try {
                    a.join();
                    return null;
                } catch (Exception e) {
                    return e;
                }
            })
            .filter(a -> a != null);
        if (exceptions.count() > 0) {
            throw new AggregateException("Error in roller threads.", exceptions.toArray(Exception[]::new));
        }

        var result = aggregatedResult.reduce();
        System.out.println(result);
    }
}

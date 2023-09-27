package com.bitclouded.akmontecarlo;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App 
{
    public static void main( String[] args ) throws Exception {
        try (var context = new AnnotationConfigApplicationContext()) {
            context.scan(App.class.getPackage().getName());
            context.refresh();
            var runner = context.getBean(Runner.class);
            runner.run();
        }
    }
}

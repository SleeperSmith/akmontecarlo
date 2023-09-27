package com.bitclouded.akmontecarlo;

public class AggregateException extends Exception {

    public final Exception[] secondaryExceptions;

    public AggregateException(String message, Exception[] exceptions) {
        super(message, exceptions[0]);
        this.secondaryExceptions = exceptions;
    }
}


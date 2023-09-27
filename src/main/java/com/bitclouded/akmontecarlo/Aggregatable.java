package com.bitclouded.akmontecarlo;

import java.util.ArrayList;

public interface Aggregatable {
    void submitResult(ArrayList<ArrayList<OnceResult>> results);

    ReportResult reduce();
}

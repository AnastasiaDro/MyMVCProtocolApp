package com.mymur.mymvcprotocolapp;

import java.util.ArrayList;

public class TrialClass {
    int goodTrials;
    int badTrials;
    int middleTrials;
    String trialName;

    public TrialClass (String trialName) {
       this.trialName = trialName;
    }

    public void toGoodTrials() {
        goodTrials++;
    }

    public void toBadTrials() {
        badTrials++;
    }

    public void toMiddleTrials() {
        middleTrials++;
    }
}

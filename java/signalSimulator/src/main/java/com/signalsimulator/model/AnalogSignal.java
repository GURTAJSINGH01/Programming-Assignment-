package com.signalsimulator.model;

import java.util.List;

public record AnalogSignal(List<Double> samples, double samplingInterval) {
    public AnalogSignal {
        samples = List.copyOf(samples);
        if (samples.isEmpty()) throw new IllegalArgumentException("Analog signal must have samples.");
        if (samplingInterval <= 0) throw new IllegalArgumentException("Sampling interval must be positive.");
    }
}

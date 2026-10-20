package com.signalsimulator.model;

import java.util.List;

public record EncodedSignal(EncodingType type, List<Double> levels, int samplesPerBit, String description) {
    public EncodedSignal {
        levels = List.copyOf(levels);
        if (samplesPerBit < 1) throw new IllegalArgumentException("samplesPerBit must be positive");
    }
}

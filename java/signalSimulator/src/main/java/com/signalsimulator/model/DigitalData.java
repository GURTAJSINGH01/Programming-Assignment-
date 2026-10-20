package com.signalsimulator.model;

import java.util.List;

public record DigitalData(List<Integer> bits, String sourceDescription) {
    public DigitalData {
        bits = List.copyOf(bits);
        if (bits.stream().anyMatch(b -> b != 0 && b != 1))
            throw new IllegalArgumentException("Digital data must contain only 0 and 1.");
    }
    public String asBitString() {
        StringBuilder sb = new StringBuilder();
        bits.forEach(sb::append);
        return sb.toString();
    }
}

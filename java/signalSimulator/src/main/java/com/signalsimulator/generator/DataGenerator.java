package com.signalsimulator.generator;

import com.signalsimulator.model.DigitalData;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataGenerator {
    private final Random random;
    public DataGenerator() { this(new Random()); }
    public DataGenerator(long seed) { this(new Random(seed)); }
    private DataGenerator(Random random) { this.random = random; }

    public DigitalData randomBits(int length) {
        if (length < 1) throw new IllegalArgumentException("Length must be at least 1.");
        List<Integer> bits = new ArrayList<>(length);
        for (int i=0;i<length;i++) bits.add(random.nextBoolean() ? 1 : 0);
        return new DigitalData(bits, "Random bits");
    }
    public DigitalData bitsWithZeroRun(int length, int runLength) {
        if (runLength != 4 && runLength != 8) throw new IllegalArgumentException("Run length must be 4 or 8.");
        if (length < runLength) throw new IllegalArgumentException("Length must be >= run length.");
        List<Integer> bits = new ArrayList<>(randomBits(length).bits());
        int start = random.nextInt(length-runLength+1);
        for (int i=start;i<start+runLength;i++) bits.set(i,0);
        return new DigitalData(bits, "Random bits with " + runLength + " consecutive zeroes at index " + start);
    }
    public DigitalData fromString(String input) {
        String clean = input.replaceAll("\\s+", "");
        if (clean.isEmpty() || !clean.matches("[01]+")) throw new IllegalArgumentException("Enter a non-empty sequence containing only 0 and 1.");
        List<Integer> bits = clean.chars().map(c -> c-'0').boxed().toList();
        return new DigitalData(bits, "User-provided bits");
    }
}

package com.signalsimulator.encoder;

import com.signalsimulator.model.EncodedSignal;
import com.signalsimulator.model.EncodingType;
import java.util.List;

public interface LineEncoder {
    EncodedSignal encode(List<Integer> bits);
    EncodingType type();
}

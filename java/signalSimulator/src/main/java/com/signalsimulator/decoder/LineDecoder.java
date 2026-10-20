package com.signalsimulator.decoder;

import com.signalsimulator.model.*;
import java.util.*;

public class LineDecoder {
    public List<Integer> decode(EncodedSignal signal) {
        List<Double> x=signal.levels(); List<Integer> bits=new ArrayList<>();
        switch(signal.type()){
            case NRZ_L -> { for(double v:x) bits.add(v>0?0:1); }
            case NRZ_I -> { double prev=1; for(double v:x){bits.add(Math.abs(v-prev)>0.5?1:0);prev=v;} }
            case MANCHESTER -> { if(x.size()%2!=0) throw new IllegalArgumentException("Manchester samples must be paired."); for(int i=0;i<x.size();i+=2) bits.add(x.get(i)>x.get(i+1)?0:1); }
            case DIFFERENTIAL_MANCHESTER -> { if(x.size()%2!=0) throw new IllegalArgumentException("Differential Manchester samples must be paired."); double prev=1; for(int i=0;i<x.size();i+=2){bits.add(Math.abs(x.get(i)-prev)>0.5?0:1);prev=x.get(i+1);} }
            case AMI -> { for(double v:x) bits.add(Math.abs(v)<0.5?0:1); }
            default -> throw new IllegalArgumentException("Decoder supports basic line codes only. Scrambled decoding requires pattern-aware violation detection.");
        }
        return List.copyOf(bits);
    }
}

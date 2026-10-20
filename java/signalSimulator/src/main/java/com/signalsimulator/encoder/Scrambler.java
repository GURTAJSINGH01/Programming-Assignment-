package com.signalsimulator.encoder;

import com.signalsimulator.model.EncodedSignal;
import com.signalsimulator.model.EncodingType;
import java.util.ArrayList;
import java.util.List;

public class Scrambler {
    // Input is AMI pulse levels. B8ZS substitutes 000VB0VB, with V=violation and B=bipolar pulse.
    public EncodedSignal b8zs(List<Integer> bits) {
        NRZLEncoder.validate(bits);
        List<Double> out=new ArrayList<>(); double last=-1; int i=0;
        while(i<bits.size()){
            if(i+8<=bits.size() && bits.subList(i,i+8).stream().allMatch(b->b==0)){
                double v = last; // violation has same polarity as previous non-zero pulse
                double b = -last;
                out.add(0.0); out.add(0.0); out.add(0.0); out.add(v);
                out.add(-v); out.add(0.0); out.add(b); out.add(v);
                last=v; i+=8;
            } else {
                int bit=bits.get(i++); if(bit==0) out.add(0.0); else {last=-last;out.add(last);}
            }
        }
        return new EncodedSignal(EncodingType.B8ZS,out,1,"AMI with B8ZS substitution; V/B pulses are represented by their levels");
    }
    // HDB3 replaces each four-zero run with 000V when number of non-zero pulses since last violation is odd,
    // otherwise B00V. Polarity follows the standard AMI violation/bipolar rules.
    public EncodedSignal hdb3(List<Integer> bits) {
        NRZLEncoder.validate(bits);
        List<Double> out=new ArrayList<>(); double last=-1; int pulseCount=0, i=0;
        while(i<bits.size()){
            if(i+4<=bits.size() && bits.subList(i,i+4).stream().allMatch(b->b==0)){
                if(pulseCount%2==1){ // 000V: violation same polarity as last pulse
                    out.add(0.0);out.add(0.0);out.add(0.0);out.add(last);
                } else { // B00V: balancing pulse then violation with same polarity as B
                    double b=-last; out.add(b);out.add(0.0);out.add(0.0);out.add(b);last=b;
                }
                pulseCount=0; i+=4;
            } else {
                int bit=bits.get(i++); if(bit==0) out.add(0.0); else {last=-last;out.add(last);pulseCount++;}
            }
        }
        return new EncodedSignal(EncodingType.HDB3,out,1,"AMI with HDB3 four-zero substitution");
    }
}

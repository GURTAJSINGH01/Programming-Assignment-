package com.signalsimulator.encoder;
import com.signalsimulator.model.*;
import java.util.*;
public class NRZLEncoder implements LineEncoder {
 public EncodedSignal encode(List<Integer> bits){ validate(bits); List<Double> out=new ArrayList<>(); for(int b:bits) out.add(b==0?1.0:-1.0); return new EncodedSignal(type(),out,1,"NRZ-L: 0=+1, 1=-1");}
 public EncodingType type(){return EncodingType.NRZ_L;}
 static void validate(List<Integer> bits){if(bits==null||bits.isEmpty()||bits.stream().anyMatch(b->b==null||(b!=0&&b!=1))) throw new IllegalArgumentException("Bits must be a non-empty list of 0/1.");}
}

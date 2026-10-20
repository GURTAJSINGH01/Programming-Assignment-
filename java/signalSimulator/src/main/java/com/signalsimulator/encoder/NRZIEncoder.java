package com.signalsimulator.encoder;
import com.signalsimulator.model.*;
import java.util.*;
public class NRZIEncoder implements LineEncoder {
 public EncodedSignal encode(List<Integer> bits){NRZLEncoder.validate(bits); List<Double> out=new ArrayList<>(); double level=1; for(int b:bits){if(b==1) level=-level; out.add(level);} return new EncodedSignal(type(),out,1,"NRZ-I: 1 causes transition at bit start; initial level +1");}
 public EncodingType type(){return EncodingType.NRZ_I;}
}

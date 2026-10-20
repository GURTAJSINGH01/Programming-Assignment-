package com.signalsimulator.encoder;
import com.signalsimulator.model.*;
import java.util.*;
public class ManchesterEncoder implements LineEncoder {
 public EncodedSignal encode(List<Integer> bits){NRZLEncoder.validate(bits); List<Double> out=new ArrayList<>(); for(int b:bits){out.add(b==0?1.0:-1.0);out.add(b==0?-1.0:1.0);} return new EncodedSignal(type(),out,2,"Manchester: 0=high-low, 1=low-high");}
 public EncodingType type(){return EncodingType.MANCHESTER;}
}

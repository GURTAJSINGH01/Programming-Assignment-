package com.signalsimulator.encoder;
import com.signalsimulator.model.*;
import java.util.*;
public class DifferentialManchesterEncoder implements LineEncoder {
 public EncodedSignal encode(List<Integer> bits){NRZLEncoder.validate(bits); List<Double> out=new ArrayList<>(); double level=1; for(int b:bits){if(b==0) level=-level; out.add(level); level=-level; out.add(level);} return new EncodedSignal(type(),out,2,"Differential Manchester: mid-bit transition always; 0 has start transition");}
 public EncodingType type(){return EncodingType.DIFFERENTIAL_MANCHESTER;}
}

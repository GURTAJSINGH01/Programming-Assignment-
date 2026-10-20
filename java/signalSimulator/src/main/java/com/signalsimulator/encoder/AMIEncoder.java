package com.signalsimulator.encoder;
import com.signalsimulator.model.*;
import java.util.*;
public class AMIEncoder implements LineEncoder {
 public EncodedSignal encode(List<Integer> bits){NRZLEncoder.validate(bits); List<Double> out=new ArrayList<>(); double last=-1; for(int b:bits){if(b==0) out.add(0.0); else {last=-last;out.add(last);}} return new EncodedSignal(type(),out,1,"AMI: zero=0V; successive ones alternate polarity, first +1");}
 public EncodingType type(){return EncodingType.AMI;}
}

package com.signalsimulator.modulation;
import java.util.*;
public class DeltaModulationDecoder {
 public List<Double> decode(List<Integer> bits,double step,double initial){
  if(bits==null||bits.isEmpty()||step<=0) throw new IllegalArgumentException("Bits and positive step required.");
  List<Double> out=new ArrayList<>(); double estimate=initial;
  for(int bit:bits){if(bit!=0&&bit!=1)throw new IllegalArgumentException("Bits must be 0/1.");estimate+=bit==1?step:-step;out.add(estimate);}
  return List.copyOf(out);
 }
}

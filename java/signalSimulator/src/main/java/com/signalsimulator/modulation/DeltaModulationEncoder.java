package com.signalsimulator.modulation;
import java.util.*;
public class DeltaModulationEncoder {
 public record DeltaResult(List<Integer> bits,double step,double initial) {}
 public DeltaResult encode(List<Double> samples,double step,double initial){
  if(samples==null||samples.isEmpty()||step<=0) throw new IllegalArgumentException("Samples and positive step required.");
  List<Integer> bits=new ArrayList<>(); double estimate=initial;
  for(double sample:samples){int bit=sample>=estimate?1:0;bits.add(bit);estimate+=bit==1?step:-step;}
  return new DeltaResult(List.copyOf(bits),step,initial);
 }
}

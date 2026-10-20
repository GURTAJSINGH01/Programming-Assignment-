package com.signalsimulator.modulation;
import java.util.*;
public class PCMEncoder {
 public record PCMResult(List<Integer> bits,int bitDepth,double min,double max,List<Integer> quantizedIndices) {}
 public PCMResult encode(List<Double> samples,int bitDepth){
  if(samples==null||samples.isEmpty()) throw new IllegalArgumentException("Samples required.");
  if(bitDepth<1||bitDepth>16) throw new IllegalArgumentException("Bit depth must be 1..16.");
  double min=samples.stream().mapToDouble(Double::doubleValue).min().orElse(0), max=samples.stream().mapToDouble(Double::doubleValue).max().orElse(0);
  int levels=1<<bitDepth; List<Integer> bits=new ArrayList<>(), idxs=new ArrayList<>();
  for(double s:samples){int q=(max==min)?0:(int)Math.round((s-min)/(max-min)*(levels-1));q=Math.max(0,Math.min(levels-1,q));idxs.add(q);for(int k=bitDepth-1;k>=0;k--)bits.add((q>>k)&1);}
  return new PCMResult(List.copyOf(bits),bitDepth,min,max,List.copyOf(idxs));
 }
}

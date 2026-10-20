package com.signalsimulator.modulation;
import java.util.*;
public class PCMDecoder {
 public List<Double> decode(PCMEncoder.PCMResult encoded){
  int depth=encoded.bitDepth(); List<Double> out=new ArrayList<>(); int levels=1<<depth;
  List<Integer> bits=encoded.bits(); if(bits.size()%depth!=0) throw new IllegalArgumentException("PCM bit count must be divisible by bit depth.");
  for(int i=0;i<bits.size();i+=depth){int q=0;for(int j=0;j<depth;j++)q=(q<<1)|bits.get(i+j);double v=levels==1?encoded.min():encoded.min()+q*(encoded.max()-encoded.min())/(levels-1);out.add(v);}
  return List.copyOf(out);
 }
}

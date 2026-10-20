package com.signalsimulator.service;

import com.signalsimulator.algorithms.PalindromeFinder;
import com.signalsimulator.decoder.LineDecoder;
import com.signalsimulator.encoder.*;
import com.signalsimulator.model.*;
import com.signalsimulator.modulation.*;
import com.signalsimulator.visualization.SignalPlotter;
import java.io.IOException;
import java.util.*;

public class SimulationService {
 private final PalindromeFinder palindromeFinder=new PalindromeFinder();
 private final Map<EncodingType,LineEncoder> encoders=new EnumMap<>(EncodingType.class);
 private final Scrambler scrambler=new Scrambler();
 private final LineDecoder decoder=new LineDecoder();
 private final SignalPlotter plotter=new SignalPlotter();
 public SimulationService(){
  register(new NRZLEncoder());register(new NRZIEncoder());register(new ManchesterEncoder());
  register(new DifferentialManchesterEncoder());register(new AMIEncoder());
 }
 private void register(LineEncoder encoder){encoders.put(encoder.type(),encoder);}
 public String longestPalindrome(DigitalData data){return palindromeFinder.longestPalindrome(data.asBitString());}
 public EncodedSignal encode(DigitalData data,EncodingType type){
  if(type==EncodingType.B8ZS)return scrambler.b8zs(data.bits());
  if(type==EncodingType.HDB3)return scrambler.hdb3(data.bits());
  LineEncoder e=encoders.get(type);if(e==null)throw new IllegalArgumentException("Not a line encoding: "+type);return e.encode(data.bits());
 }
 public List<Integer> decode(EncodedSignal signal){return decoder.decode(signal);}
 public java.io.File plot(EncodedSignal signal)throws IOException{return plotter.plot(signal,signal.type().name().toLowerCase(Locale.ROOT)+".png");}
 public PCMEncoder.PCMResult pcmEncode(List<Double> samples,int depth){return new PCMEncoder().encode(samples,depth);}
 public List<Double> pcmDecode(PCMEncoder.PCMResult r){return new PCMDecoder().decode(r);}
 public DeltaModulationEncoder.DeltaResult deltaEncode(List<Double> samples,double step,double initial){return new DeltaModulationEncoder().encode(samples,step,initial);}
 public List<Double> deltaDecode(DeltaModulationEncoder.DeltaResult r){return new DeltaModulationDecoder().decode(r.bits(),r.step(),r.initial());}
}

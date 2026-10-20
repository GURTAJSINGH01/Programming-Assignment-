package com.signalsimulator.app;

import com.signalsimulator.generator.DataGenerator;
import com.signalsimulator.model.*;
import com.signalsimulator.service.SimulationService;
import com.signalsimulator.modulation.PCMEncoder;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class ApplicationController {
 private final Scanner in=new Scanner(System.in);
 private final DataGenerator generator=new DataGenerator();
 private final SimulationService service=new SimulationService();

 public void run(){
  System.out.println("\n=== Digital Signal Encoder & Decoder Simulator ===");
  while(true){
   System.out.println("\n1. Digital input / line coding\n2. Analog samples / PCM\n3. Analog samples / Delta Modulation\n0. Exit");
   int choice=readInt("Choose: ",0,3); if(choice==0){System.out.println("Goodbye.");return;}
   try {if(choice==1) digitalFlow(); else if(choice==2) pcmFlow(); else deltaFlow();}
   catch(Exception e){System.out.println("Error: "+e.getMessage());}
  }
 }
 private void digitalFlow(){
  System.out.println("\nDigital data: 1=random, 2=random with 4 zeros, 3=random with 8 zeros, 4=enter bits");
  int mode=readInt("Choose: ",1,4); DigitalData data;
  if(mode==4){System.out.print("Enter bits: ");data=generator.fromString(in.nextLine());}
  else {int n=readInt("Sequence length"+(mode==2?" (>=4)":mode==3?" (>=8)":"")+" : ",mode==2?4:mode==3?8:1,10000);
   data=mode==1?generator.randomBits(n):generator.bitsWithZeroRun(n,mode==2?4:8);}
  System.out.println("Source: "+data.sourceDescription());System.out.println("Digital stream: "+data.asBitString());
  System.out.println("Longest palindrome: "+service.longestPalindrome(data)+" (length "+service.longestPalindrome(data).length()+")");
  System.out.println("\nEncoding: 1 NRZ-L, 2 NRZ-I, 3 Manchester, 4 Differential Manchester, 5 AMI, 6 B8ZS, 7 HDB3");
  int opt=readInt("Choose encoding: ",1,7);
  EncodingType type=switch(opt){case 1->EncodingType.NRZ_L;case 2->EncodingType.NRZ_I;case 3->EncodingType.MANCHESTER;case 4->EncodingType.DIFFERENTIAL_MANCHESTER;case 5->EncodingType.AMI;case 6->EncodingType.B8ZS;default->EncodingType.HDB3;};
  if((type==EncodingType.B8ZS||type==EncodingType.HDB3)&&!askYesNo("Scrambling is AMI-based. Continue? (y/n): "))return;
  EncodedSignal encoded=service.encode(data,type);
  System.out.println("Encoded levels: "+encoded.levels());
  try{File f=service.plot(encoded);System.out.println("Waveform plot: "+f.getAbsolutePath());}catch(Exception e){System.out.println("Plot error: "+e.getMessage());}
  if(askYesNo("Decode this signal? (y/n): ")){
   if(type==EncodingType.B8ZS||type==EncodingType.HDB3)System.out.println("Note: scrambled decoding is not enabled in this build; the pattern-aware decoder is a documented extension.");
   else {List<Integer> recovered=service.decode(encoded);String bits=recovered.stream().map(String::valueOf).collect(Collectors.joining());System.out.println("Recovered bits: "+bits);System.out.println("Round trip correct: "+bits.equals(data.asBitString()));}
  }
 }
 private void pcmFlow(){
  List<Double> samples=readSamples();int depth=readInt("PCM bit depth (1-8): ",1,8);
  PCMEncoder.PCMResult result=service.pcmEncode(samples,depth);
  System.out.println("PCM bitstream: "+result.bits().stream().map(String::valueOf).collect(Collectors.joining()));
  List<Double> decoded=service.pcmDecode(result);System.out.println("PCM reconstructed samples: "+decoded);
  try{new com.signalsimulator.visualization.SignalPlotter().plotStaircase(decoded,"PCM reconstruction","pcm-staircase.png");System.out.println("Plot saved to output/pcm-staircase.png");}catch(Exception e){System.out.println("Plot error: "+e.getMessage());}
  if(askYesNo("Feed PCM bits to a line encoder? (y/n): ")){
   DigitalData bits=new DigitalData(result.bits(),"PCM output bits");
   System.out.println("Choose line code: 1 NRZ-L, 2 NRZ-I, 3 Manchester, 4 Differential Manchester, 5 AMI");
   int c=readInt("Choose: ",1,5);EncodingType t=switch(c){case 1->EncodingType.NRZ_L;case 2->EncodingType.NRZ_I;case 3->EncodingType.MANCHESTER;case 4->EncodingType.DIFFERENTIAL_MANCHESTER;default->EncodingType.AMI;};
   EncodedSignal sig=service.encode(bits,t);System.out.println("Line-coded PCM levels: "+sig.levels());try{System.out.println("Plot saved: "+service.plot(sig));}catch(Exception e){System.out.println("Plot error: "+e.getMessage());}
  }
 }
 private void deltaFlow(){
  List<Double> samples=readSamples();double step=readDouble("Delta step size (>0): ",0.000001,Double.MAX_VALUE);double initial=readDouble("Initial estimate: ",-Double.MAX_VALUE,Double.MAX_VALUE);
  var encoded=service.deltaEncode(samples,step,initial);System.out.println("DM bits: "+encoded.bits());List<Double> decoded=service.deltaDecode(encoded);System.out.println("Reconstructed staircase: "+decoded);
  try{new com.signalsimulator.visualization.SignalPlotter().plotStaircase(decoded,"Delta modulation staircase","delta-staircase.png");System.out.println("Plot saved to output/delta-staircase.png");}catch(Exception e){System.out.println("Plot error: "+e.getMessage());}
  if(askYesNo("Feed DM bits to a line encoder? (y/n): ")){
   DigitalData bits=new DigitalData(encoded.bits(),"Delta modulation output bits");
   System.out.println("Choose line code: 1 NRZ-L, 2 NRZ-I, 3 Manchester, 4 Differential Manchester, 5 AMI");
   int c=readInt("Choose: ",1,5);EncodingType t=switch(c){case 1->EncodingType.NRZ_L;case 2->EncodingType.NRZ_I;case 3->EncodingType.MANCHESTER;case 4->EncodingType.DIFFERENTIAL_MANCHESTER;default->EncodingType.AMI;};
   EncodedSignal sig=service.encode(bits,t);System.out.println("Line-coded DM levels: "+sig.levels());try{System.out.println("Plot saved: "+service.plot(sig));}catch(Exception e){System.out.println("Plot error: "+e.getMessage());}
  }
 }
 private List<Double> readSamples(){
  System.out.print("Enter analog samples separated by commas (e.g. 0,0.5,1,0.5,0): ");
  String line=in.nextLine().trim();if(line.isEmpty())throw new IllegalArgumentException("Samples cannot be empty.");
  return Arrays.stream(line.split(",")).map(String::trim).map(Double::parseDouble).collect(Collectors.toList());
 }
 private boolean askYesNo(String prompt){System.out.print(prompt);String s=in.nextLine().trim().toLowerCase(Locale.ROOT);return s.equals("y")||s.equals("yes");}
 private int readInt(String prompt,int min,int max){while(true){System.out.print(prompt);try{int n=Integer.parseInt(in.nextLine().trim());if(n>=min&&n<=max)return n;}catch(Exception ignored){}System.out.println("Enter an integer from "+min+" to "+max+".");}}
 private double readDouble(String prompt,double min,double max){while(true){System.out.print(prompt);try{double n=Double.parseDouble(in.nextLine().trim());if(Double.isFinite(n)&&n>=min&&n<=max)return n;}catch(Exception ignored){}System.out.println("Enter a valid number in range.");}}
}

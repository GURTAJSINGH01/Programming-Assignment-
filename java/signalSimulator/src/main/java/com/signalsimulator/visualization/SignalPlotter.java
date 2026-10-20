package com.signalsimulator.visualization;

import com.signalsimulator.model.EncodedSignal;
import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class SignalPlotter {
 public File plot(EncodedSignal signal,String fileName) throws IOException {
  XYChart chart=new XYChartBuilder().width(1000).height(500).title(signal.type()+" waveform").xAxisTitle("Sample index").yAxisTitle("Voltage level").build();
  chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
  List<Double> xs=new ArrayList<>(), ys=new ArrayList<>();
  for(int i=0;i<signal.levels().size();i++){xs.add((double)i);ys.add(signal.levels().get(i));}
  chart.addSeries("Encoded levels",xs,ys);
  File dir=new File("output"); if(!dir.exists()&&!dir.mkdirs()) throw new IOException("Could not create output directory.");
  File file=new File(dir,fileName); BitmapEncoder.saveBitmap(chart,file.getPath(),BitmapEncoder.BitmapFormat.PNG); return file;
 }
 public File plotStaircase(List<Double> values,String title,String fileName) throws IOException {
  XYChart chart=new XYChartBuilder().width(1000).height(500).title(title).xAxisTitle("Sample index").yAxisTitle("Amplitude").build();
  List<Double> xs=new ArrayList<>();for(int i=0;i<values.size();i++)xs.add((double)i);
  chart.addSeries(title,xs,values);
  File dir=new File("output");if(!dir.exists()&&!dir.mkdirs())throw new IOException("Could not create output directory.");
  File file=new File(dir,fileName);BitmapEncoder.saveBitmap(chart,file.getPath(),BitmapEncoder.BitmapFormat.PNG);return file;
 }
}

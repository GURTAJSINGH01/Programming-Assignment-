package com.signalsimulator.encoder;
import com.signalsimulator.model.*;import com.signalsimulator.decoder.LineDecoder;import org.junit.jupiter.api.Test;import java.util.*;import static org.junit.jupiter.api.Assertions.*;
class LineEncoderTest {
 @Test void basicSchemesRoundTrip(){List<Integer>b=List.of(1,0,1,1,0,0,1);List<LineEncoder> es=List.of(new NRZLEncoder(),new NRZIEncoder(),new ManchesterEncoder(),new DifferentialManchesterEncoder(),new AMIEncoder());for(var e:es)assertEquals(b,new LineDecoder().decode(e.encode(b)),e.type().toString());}
 @Test void b8zsSubstitutesEightZeroRun(){var s=new Scrambler().b8zs(List.of(1,0,0,0,0,0,0,0,0,1));assertEquals(10,s.levels().size());}
}

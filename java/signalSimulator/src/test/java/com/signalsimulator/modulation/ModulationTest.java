package com.signalsimulator.modulation;
import org.junit.jupiter.api.Test;import java.util.*;import static org.junit.jupiter.api.Assertions.*;
class ModulationTest {
 @Test void pcmProducesExpectedBitCount(){var r=new PCMEncoder().encode(List.of(0.0,0.5,1.0),3);assertEquals(9,r.bits().size());assertEquals(3,new PCMDecoder().decode(r).size());}
 @Test void deltaDecoderProducesStaircase(){var e=new DeltaModulationEncoder().encode(List.of(0.0,1.0,0.5),0.25,0.0);assertEquals(3,new DeltaModulationDecoder().decode(e.bits(),e.step(),e.initial()).size());}
}

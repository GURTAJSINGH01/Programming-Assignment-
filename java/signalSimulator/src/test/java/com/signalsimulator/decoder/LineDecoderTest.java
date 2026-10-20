package com.signalsimulator.decoder;
import com.signalsimulator.encoder.*;import com.signalsimulator.model.*;import org.junit.jupiter.api.Test;import java.util.*;import static org.junit.jupiter.api.Assertions.*;
class LineDecoderTest {
 @Test void manchesterDecodesFromLevels(){var sig=new ManchesterEncoder().encode(List.of(0,1,1,0));assertEquals(List.of(0,1,1,0),new LineDecoder().decode(sig));}
}

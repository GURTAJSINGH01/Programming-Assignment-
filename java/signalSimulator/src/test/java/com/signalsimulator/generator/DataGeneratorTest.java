package com.signalsimulator.generator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class DataGeneratorTest {
 @Test void fixedZeroRunExists(){var d=new DataGenerator(1).bitsWithZeroRun(20,8);assertTrue(d.asBitString().contains("00000000"));}
 @Test void customBitsValidated(){assertEquals("10101",new DataGenerator().fromString("10101").asBitString());assertThrows(IllegalArgumentException.class,()->new DataGenerator().fromString("102"));}
}

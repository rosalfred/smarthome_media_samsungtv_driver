package org.rosmultimedia.player.samsung;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SamsungCommandTest {

    String[] stringValues;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        //TODO fix it !!
        this.stringValues = Arrays.toString(SamsungCommand.values()).replaceAll("^.|.$", "").split(", ");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public final void testGetValue() {
//      // TODO fix !
//      for (int i = 0; i < stringValues.length; i++) {
//          String string = stringValues[i];
//          SamsungCommand cmd = SamsungCommand.values()[i];
//          assertEquals(string, cmd.getValue());
//      }

        assertEquals("KEY_EXT41", SamsungCommand.KEY_EXT41.getValue());
    }

    @Test
    public final void testToString() {
        for (SamsungCommand cmd : SamsungCommand.values()) {
            assertEquals(SamsungCommand.class.getName(), cmd.toString());
        }
    }

    @Test
    public final void testFromName() {
//    	// TODO fix !
//        for (int i = 0; i < stringValues.length; i++) {
//            String string = stringValues[i];
//            SamsungCommand cmd = SamsungCommand.values()[i];
//            assertEquals(cmd, SamsungCommand.fromName(string));
//        }

        assertEquals(SamsungCommand.KEY_AUTO_ARC_ANYNET_AUTO_START, SamsungCommand.fromName("KEY_AUTO_ARC_ANYNET_AUTO_START"));
        assertEquals(SamsungCommand.KEY_EXT41, SamsungCommand.fromName("KEY_EXT41"));
    }

}

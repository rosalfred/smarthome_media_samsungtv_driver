package org.rosmultimedia.player.samsung;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ros2.rcljava.node.MockNode;
import org.ros2.rcljava.node.Node;

public class SamsungConfigTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public final void testSamsungConfig() {
        Node node = new MockNode();
        SamsungConfig config = new SamsungConfig(node);
        assertNotNull(config);
        assertEquals("/_test//MockNode", config.getPrefix());
//        assertEquals("samsungtv", config.get);
//        assertEquals("fixed_frame", config.getFixedFrame());
//        assertEquals(1, config.getRate());
//        assertEquals("00:00:00:00:00:00", config.getMac());
//        assertEquals("192.168.0.40", config.getHost());
//        assertEquals(55000L, config.getPort());
//        assertEquals("admin", config.getUser());
//        assertEquals("admin", config.getPassword());
    }

}

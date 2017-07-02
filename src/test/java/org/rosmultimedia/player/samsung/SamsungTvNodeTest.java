package org.rosmultimedia.player.samsung;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import org.ros2.rcljava.node.MockNode;
import org.ros2.rcljava.node.MockRemoteSeesion;
import org.ros2.rcljava.node.Node;

public class SamsungTvNodeTest {

    private SamsungTvNode managedNode;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        Node node = new MockNode();
        this.managedNode = new SamsungTvNode();
        this.managedNode.onStart(node);
        this.managedNode.onStarted();
        this.managedNode.tvIp = new MockRemoteSeesion(this.managedNode);
    }

    @After
    public void tearDown() throws Exception {
        this.managedNode.onShutdown();
        this.managedNode.onShutdowned();
    }

    @Test
    @Ignore
    public final void testOnShutdown() {
        this.managedNode.onShutdown();
    }

    @Test
    public final void testConnect() {
        boolean isconnected = this.managedNode.connect();
        assertTrue(isconnected);
    }

    @Test
    public final void testOnConnected() {
        this.managedNode.onConnected();
    }

    @Test
    public final void testOnDisconnected() {
        this.managedNode.onDisconnected();
    }

    @Test
    public final void testInitialize() {
        this.managedNode.initialize();
    }

    @Test
    public final void testSamsungTvNode() {
        assertNotNull(managedNode);
    }

    @Test
    @Ignore
    public final void testOnStartNode() {
        Node node = new MockNode();
        managedNode = new SamsungTvNode();
        managedNode.onStart(node);
        managedNode.tvIp = new MockRemoteSeesion(this.managedNode);
        managedNode.onShutdown();
        managedNode.onShutdowned();
    }

    @Test
    @Ignore
    public final void testPushEnumSamsungCommandFloat()  throws Exception {
        this.managedNode.pushEnum(SamsungCommand.KEY_0, 100.0F);
    }

    @Test
    @Ignore
    public final void testPushEnumSamsungCommand()  throws Exception {
        this.managedNode.pushEnum(SamsungCommand.KEY_0);
    }

    @Test
    @Ignore
    public final void testSendTextStringFloat() throws Exception {
        this.managedNode.sendText("test", 100.0F);
    }

    @Test
    @Ignore
    public final void testSendTextString() throws Exception {
        this.managedNode.sendText("test");
    }

    @Test
    public final void testIsInteger() {
        assertTrue(SamsungTvNode.isInteger("1"));
        assertTrue(SamsungTvNode.isInteger("2"));
        assertTrue(SamsungTvNode.isInteger("3"));
        assertTrue(SamsungTvNode.isInteger("11"));

        assertFalse(SamsungTvNode.isInteger("azerty"));
    }

    @Test
    public final void testMakeConfiguration() {
        SamsungConfig config = this.managedNode.makeConfiguration();
        assertNotNull(config);
    }

}

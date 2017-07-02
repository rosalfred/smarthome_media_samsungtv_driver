package org.ros2.rcljava.node;

import org.rosmultimedia.player.samsung.SamsungTvNode;
import org.rosmultimedia.player.samsung.driver.SamsungRemoteSession;

public class MockRemoteSeesion extends SamsungRemoteSession {

    public MockRemoteSeesion(SamsungTvNode samsungTvNode) {
        super(samsungTvNode);
    }

    public void destroy() {

    }

}

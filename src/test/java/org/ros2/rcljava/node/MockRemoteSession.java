package org.ros2.rcljava.node;

import org.rosmultimedia.player.samsung.SamsungTvNode;
import org.rosmultimedia.player.samsung.driver.SamsungRemoteSession;

public class MockRemoteSession extends SamsungRemoteSession {

    public MockRemoteSession(SamsungTvNode samsungTvNode) {
        super(samsungTvNode);
    }

    public void destroy() {

    }

}

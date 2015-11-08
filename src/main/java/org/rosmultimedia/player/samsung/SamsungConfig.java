/**
 * This file is part of the Alfred package.
 *
 * (c) Mickael Gaillard <mick.gaillard@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package org.rosmultimedia.player.samsung;

import org.ros.node.ConnectedNode;
import org.rosbuilding.common.NodeConfig;

/**
*
* @author Mickael Gaillard <mick.gaillard@gmail.com>
*
*/
public class SamsungConfig extends NodeConfig {

    public static final String RATE = "rate";

    private String mac;
    private String host;
    private int    port;
    private String user;
    private String password;

    public SamsungConfig(ConnectedNode connectedNode) {
        super(connectedNode, "samsung_salon", "fixed_frame", 1);
    }

    @Override
    protected void loadParameters() {
        super.loadParameters();

        this.mac = this.connectedNode.getParameterTree()
                .getString("~mac", "00:00:00:00:00:00");
        this.host = this.connectedNode.getParameterTree()
                .getString("~ip", "192.168.0.40");
        this.port = this.connectedNode.getParameterTree()
                .getInteger("~port", 55000);
        this.user = this.connectedNode.getParameterTree()
                .getString("~user", "admin");
        this.password = this.connectedNode.getParameterTree()
                .getString("~password", "admin");
    }

    public String getMac() {
        return this.mac;
    }

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }

    public String getUser() {
        return this.user;
    }

    public String getPassword() {
        return this.password;
    }
}

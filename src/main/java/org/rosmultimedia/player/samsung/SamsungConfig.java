/**
 * This file is part of the Alfred package.
 *
 * (c) Mickael Gaillard <mick.gaillard@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package org.rosmultimedia.player.samsung;

import org.ros2.rcljava.node.Node;
import org.rosbuilding.common.NodeDriverConnectedConfig;

/**
*
* @author Mickael Gaillard <mick.gaillard@gmail.com>
*
*/
public class SamsungConfig extends NodeDriverConnectedConfig {

    public SamsungConfig(final Node connectedNode) {
        super(
                connectedNode,
                "/home/salon/samsungtv/",
                "fixed_frame",
                1,
                "00:00:00:00:00:00",
                "192.168.0.40",
                55000L,
                "admin",
                "admin");
    }
}

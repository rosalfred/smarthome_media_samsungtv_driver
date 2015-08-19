/**
 * This file is part of the Alfred package.
 *
 * (c) Mickael Gaillard <mick.gaillard@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.alfred.ros.samsung;

import org.ros.dynamic_reconfigure.server.BaseConfig;
import org.ros.node.ConnectedNode;

/**
*
* @author Mickael Gaillard <mick.gaillard@gmail.com>
*
*/
public class SamsungConfig extends BaseConfig {

    public static final String RATE = "rate";

    public SamsungConfig(ConnectedNode connectedNode) {
        super(connectedNode);

        this.addField(RATE, "int", 0, "rate processus", 1, 0, 200);
    }

}

/**
 * This file is part of the Alfred package.
 *
 * (c) Mickael Gaillard <mick.gaillard@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package org.rosmultimedia.player.samsung.internal;

import java.io.IOException;

import org.rosbuilding.common.ISystem;
import org.rosmultimedia.player.samsung.SamsungCommand;
import org.rosmultimedia.player.samsung.SamsungTvNode;

import smarthome_media_msgs.MediaAction;
import smarthome_media_msgs.StateData;

/**
*
* @author Mickael Gaillard <mick.gaillard@gmail.com>
*
*/
public class SamsungSystem implements ISystem<StateData, MediaAction> {

    /**
     * SamsungTV node.
     */
    private SamsungTvNode samsungTv;

    public SamsungSystem(SamsungTvNode samsungTvNode) {
        this.samsungTv = samsungTvNode;
    }

    @Override
    public void load(StateData stateData) {
        // TODO Auto-generated method stub

    }

    @Override
    public void callbackCmdAction(MediaAction message, StateData stateData)
            throws IOException, InterruptedException {
        switch (message.getMethod()) {
        case OP_POWER:
            // Not available on Samsung
            //TODO but copy to wol
            break;
        case OP_SHUTDOWN:
            this.samsungTv.pushEnum(SamsungCommand.KEY_POWEROFF);
            break;
        default:

        }

    }

}

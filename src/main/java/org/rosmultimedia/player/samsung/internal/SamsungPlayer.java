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
import java.util.List;

import org.rosbuilding.common.media.Player;
import org.rosmultimedia.player.samsung.SamsungCommand;
import org.rosmultimedia.player.samsung.SamsungTvNode;

import smarthome_media_msgs.msg.MediaAction;
import smarthome_media_msgs.msg.PlayerInfo;
import smarthome_media_msgs.msg.StateData;

/**
*
* @author Mickael Gaillard <mick.gaillard@gmail.com>
*
*/
public class SamsungPlayer extends Player {

    private SamsungTvNode node;

    public SamsungPlayer(SamsungTvNode node) {
        this.node = node;
    }

    @Override
    protected void initializeAvailableMethods(List<String> availableMethods) {
        availableMethods.add(OP_HOME);
        availableMethods.add(OP_INFO);
        availableMethods.add(OP_DISPLAY);
        availableMethods.add(OP_SELECT);
        availableMethods.add(OP_CONTEXT);
        availableMethods.add(OP_UP);
        availableMethods.add(OP_DOWN);
        availableMethods.add(OP_LEFT);
        availableMethods.add(OP_RIGHT);
    }

    @Override
    public void load(StateData stateData) {
        this.load(stateData.getPlayer());
    }

    public void load(PlayerInfo stateData) {
        // TODO Auto-generated method stub

    }

    @Override
    public void callbackCmdAction(MediaAction message, StateData stateData) throws IOException, InterruptedException {
        switch (message.getMethod()) {

        case OP_HOME:
            this.node.pushEnum( SamsungCommand.KEY_HOME, 2f);
            break;

        case OP_INFO:
            this.node.pushEnum( SamsungCommand.KEY_INFO, 2f);
            break;

        case OP_DISPLAY:
            this.node.pushEnum( SamsungCommand.KEY_CONTENTS, 2f);
            break;

        case OP_SELECT:
            if (stateData.getPlayer().getCanseek()) {
                message.setMethod(OP_PLAYPAUSE);
                this.callbackCmdAction(message, stateData);
            } else {
                this.node.pushEnum( SamsungCommand.KEY_ENTER, 2f);
            }
            break;

        case OP_CONTEXT:
            this.node.pushEnum( SamsungCommand.KEY_MENU, 2f);
            break;

        case OP_UP:
            if (stateData.getPlayer().getCanseek()) {
                this.node.pushEnum( SamsungCommand.KEY_UP, 2f);
//                this.xbmcJson.getResult(new Player.Seek(1, Player.Seek.Value.BIGFORWARD));
            } else {
                this.node.pushEnum( SamsungCommand.KEY_UP, 2f);
            }
            break;

        case OP_DOWN:
            if (stateData.getPlayer().getCanseek()) {
                this.node.pushEnum( SamsungCommand.KEY_DOWN, 2f);
//                this.xbmcJson.getResult(new Player.Seek(1, Player.Seek.Value.BIGBACKWARD));
            } else {
                this.node.pushEnum( SamsungCommand.KEY_DOWN, 2f);
            }
            break;

        case OP_LEFT:
            if (stateData.getPlayer().getCanseek()) {
                this.node.pushEnum( SamsungCommand.KEY_LEFT, 2f);
//                this.xbmcJson.getResult(new Player.Seek(1, Player.Seek.Value.SMALLBACKWARD));
            } else {
                this.node.pushEnum( SamsungCommand.KEY_LEFT, 2f);
            }
            break;

        case OP_RIGHT:
            if (stateData.getPlayer().getCanseek()) {
                this.node.pushEnum( SamsungCommand.KEY_RIGHT, 2f);
//                this.xbmcJson.getResult(new Player.Seek(1, Player.Seek.Value.SMALLFORWARD));
            } else {
                this.node.pushEnum( SamsungCommand.KEY_RIGHT, 2f);
            }
            break;

        }
    }
}

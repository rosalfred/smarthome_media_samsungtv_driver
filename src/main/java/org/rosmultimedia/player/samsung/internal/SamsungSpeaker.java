/**
 * This file is part of the Alfred package.
 *
 * (c) Mickael Gaillard <mick.gaillard@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package org.rosmultimedia.player.samsung.internal;

import java.util.List;

import org.rosbuilding.common.media.Speaker;

import smarthome_media_msgs.msg.MediaAction;
import smarthome_media_msgs.msg.SpeakerInfo;
import smarthome_media_msgs.msg.StateData;
import smarthome_media_msgs.srv.ToggleMuteSpeaker_Request;
import smarthome_media_msgs.srv.ToggleMuteSpeaker_Response;

/**
*
* @author Mickael Gaillard <mick.gaillard@gmail.com>
*
*/
public class SamsungSpeaker extends Speaker {

    @Override
    public void load(StateData stateData) {
        this.load(stateData.getSpeaker());
    }

    @Override
    protected void initializeAvailableMethods(List<String> arg0) {

    }

    public void load(SpeakerInfo stateData) {
        // TODO Auto-generated method stub

    }

    @Override
    public void callbackCmdAction(MediaAction message, StateData stateData) {
        // TODO Auto-generated method stub

    }

    @Override
    public void handleSpeakerMuteToggle(ToggleMuteSpeaker_Request request,
            ToggleMuteSpeaker_Response response) {
        // TODO Auto-generated method stub

    }
}

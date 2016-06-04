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

import org.rosbuilding.common.media.IMonitor;
import org.rosmultimedia.player.samsung.SamsungCommand;
import org.rosmultimedia.player.samsung.SamsungTvNode;

import smarthome_media_msgs.MediaAction;
import smarthome_media_msgs.StateData;

/**
*
* @author Mickael Gaillard <mick.gaillard@gmail.com>
*
*/
public class SamsungMonitor implements IMonitor {

    public final static String TV    = "tv";
    public final static String HDMI  = "hdmi";
    public final static String HDMI1 = HDMI + "1";
    public final static String HDMI2 = "xbmc"; // HDMI + "2";
    public final static String HDMI3 = "ps3"; // HDMI + "3";
    public final static String HDMI4 = HDMI + "4";

    public final static String INFO  = "info";
    public final static String GUIDE = "guide";
    public final static String TOOLS = "tools";

    protected static final String PROTO_CHANNEL = "channel://";
    private SamsungTvNode node;

    public SamsungMonitor(SamsungTvNode owner) {
        this.node = owner;
    }

    @Override
    public void load(StateData stateData) {
        // TODO Auto-generated method stub
    }

    @Override
    public void callbackCmdAction(MediaAction message, StateData stateData)
            throws IOException, InterruptedException {

        switch (message.getMethod()) {
        case OP_CHANNEL:
            switch (message.getUri()) {
            case (PROTO_CHANNEL + TV):
                this.node.pushEnum( SamsungCommand.KEY_TV, 2f );
                break;

            case (PROTO_CHANNEL + HDMI1):
                this.node.pushEnum( SamsungCommand.KEY_TV, 2f );
                break;

            case (PROTO_CHANNEL + HDMI2): // Switch on XBMC
                this.node.pushEnum( SamsungCommand.KEY_AUTO_ARC_PIP_WIDE, 2f);
                break; //FIXME by hdmi2 resolv in 3th node

            case (PROTO_CHANNEL + HDMI3): // Switch on PS3
                this.node.pushEnum( SamsungCommand.KEY_AUTO_ARC_PIP_RIGHT_BOTTOM, 2f);
                break; //FIXME by hdmi3 resolv in 3th node

            case (PROTO_CHANNEL + HDMI2 + TV): //FIXME by tv-hdmi2 resolv in 3th node
            case (PROTO_CHANNEL + HDMI3 + TV): //FIXME by tv-hdmi3 resolv in 3th node
            case (PROTO_CHANNEL + TV + HDMI2):
            case (PROTO_CHANNEL + TV + HDMI3):
                // Switch on HDMI
                if ((PROTO_CHANNEL + HDMI2 + TV).equals(message.getUri())) {
                    this.node.pushEnum( SamsungCommand.KEY_AUTO_ARC_PIP_WIDE, 2f);
                } else

                if ((PROTO_CHANNEL + HDMI3 + TV).equals(message.getUri())) {
                    this.node.pushEnum( SamsungCommand.KEY_AUTO_ARC_PIP_RIGHT_BOTTOM, 2f);
                }

                // Activate PIP
                this.node.pushEnum( SamsungCommand.KEY_PIP_ONOFF, 2f);

                if (true) {
                    // Menu for Sound channel
                    this.node.pushEnum( SamsungCommand.KEY_TOOLS, 1f );
                    this.node.pushEnum( SamsungCommand.KEY_UP );
                    this.node.pushEnum( SamsungCommand.KEY_UP );
                    this.node.pushEnum( SamsungCommand.KEY_RIGHT, 0.75f);
                    this.node.pushEnum( SamsungCommand.KEY_UP );
                    this.node.pushEnum( SamsungCommand.KEY_RIGHT, 0.5f ); // Switch sound
                    this.node.pushEnum( SamsungCommand.KEY_RETURN, 0.75f );
                    this.node.pushEnum( SamsungCommand.KEY_RETURN );
                }
                break;
            }

            // Show Channel
            String subUri = message.getUri().replace(PROTO_CHANNEL, "");
            if ( SamsungTvNode.isInteger(subUri)) {
                int value = Integer.valueOf(subUri);
                int cent = value / 100 % 10;
                int disa = value / 10 % 10;
                int digit = value % 10;

//                if (true == false) { // if not in TV mode
//                    this.node.pushEnum( SamsungCommand.KEY_TV, 2f );
//                }

                if (true) {
                    float timeout = 0.4f;
                    if(digit > 0 || cent > 0) {
                        if (cent > 0) {
                            this.node.pushEnum(
                                    SamsungCommand.fromName(
                                            SamsungCommand.KEY + cent),
                                            timeout );
                        }
                        if (disa > 0 || cent > 0) {
                            this.node.pushEnum(
                                    SamsungCommand.fromName(
                                            SamsungCommand.KEY  + disa),
                                            timeout );
                        }
                    }
                    this.node.pushEnum(
                            SamsungCommand.fromName(
                                    SamsungCommand.KEY  + digit),
                                    timeout );
                    this.node.pushEnum( SamsungCommand.KEY_ENTER );
                }
            }
            break;

            // Show Guide
        case GUIDE:
            this.node.pushEnum( SamsungCommand.KEY_GUIDE );
            break;

            // Show Tools
        case TOOLS:
            this.node.pushEnum( SamsungCommand.KEY_TOOLS );
            break;


        default:
            // Send internal command
            if (message.getMethod().contains(SamsungCommand.KEY )) {
                SamsungCommand cmd = SamsungCommand.fromName(
                        message.getMethod());
                if (cmd != null) {
                    this.node.pushEnum(cmd);
                } else {
                    this.node.logE(
                            String.format("Key \"%s\" command not found",
                                    message.getMethod()));
                }
            } else

            // Else bypass the command
            {
                this.node.sendText( message.getMethod() );
            }

            break;
        }

    }
}

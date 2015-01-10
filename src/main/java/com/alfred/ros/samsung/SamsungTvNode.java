package com.alfred.ros.samsung;

import java.io.IOException;

import media_msgs.StateData;

import org.ros.dynamic_reconfigure.server.Server;
import org.ros.dynamic_reconfigure.server.Server.ReconfigureListener;
import org.ros.node.ConnectedNode;
import org.ros.node.Node;

import com.alfred.ros.media.BaseMediaNodeMain;
import com.alfred.ros.samsung.driver.LcdTvC650;
import com.alfred.ros.samsung.driver.SamsungRemoteSession;
import com.alfred.ros.samsung.internal.SamsungMonitor;
import com.alfred.ros.samsung.internal.SamsungPlayer;
import com.alfred.ros.samsung.internal.SamsungSystem;

/**
 * SamsungTv ROS Node.
 */
public class SamsungTvNode 
        extends BaseMediaNodeMain
        implements ReconfigureListener<SamsungConfig> {

    protected SamsungRemoteSession tvIp;

    static {
        nodeName = "samsungtv";
    }

    @Override
    public void onStart(ConnectedNode connectedNode) {
        super.onStart(connectedNode);
        this.tvIp = new LcdTvC650(this);
        this.startFinal();
    }

    @Override
    public void onShutdown(Node node) {
        this.tvIp.destroy();
        super.onShutdown(node);
    }

    protected void loadParameters() {
        this.prefix = String.format("/%s/", 
                this.connectedNode.getParameterTree()
                    .getString("~tf_prefix", "samsung_salon"));
        this.fixedFrame = this.connectedNode.getParameterTree()
                .getString("~fixed_frame", "fixed_frame");
        this.rate = this.connectedNode.getParameterTree()
                .getInteger("~" + SamsungConfig.RATE, 1);
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

        this.logI(
                String.format("rate : %s\nprefix : %s\nfixedFrame : %s\nip : %s\nmac : %s\nport : %s\nuser : %s\npassword : %s", 
                        this.rate, 
                        this.prefix, 
                        this.fixedFrame, 
                        this.host, 
                        this.mac, 
                        this.port, 
                        this.user, 
                        this.password));

        this.serverReconfig = new Server<SamsungConfig>(
                connectedNode,
                new SamsungConfig(this.connectedNode), 
                this);
    }

    @Override
    protected void connect() {
        this.logI(String.format("Connecting to %s:%s...", this.host, this.port));
        
        try {
            this.tvIp = SamsungRemoteSession.create(
                    this,
                    SamsungRemoteSession.APP, 
                    SamsungRemoteSession.REMOTE, 
                    this.getHost(), 
                    this.getPort() );
            
            this.stateData.setState(StateData.INIT);
            this.isConnected = true;
            this.logI("\tConnected done.");
        } catch (Exception e) {
            this.stateData.setState(StateData.SHUTDOWN);
            try {
                Thread.sleep(10000 / this.rate);
            } catch (InterruptedException ex) {
                this.logE(ex);
            }
        }
    }

    @Override
    protected void initialize() {
        super.initialize();

        this.monitor = new SamsungMonitor(this);
        this.player = new SamsungPlayer(this);
//        this.speaker = new SamsungSpeaker(this.xbmcJson, this);
        this.system = new SamsungSystem(this);
//        this.library = new SamsungLibrary(this.xbmcJson, this);
    }

    @Override
    public SamsungConfig onReconfigure(SamsungConfig config, int level) {
        this.rate = config.getInteger(SamsungConfig.RATE, this.rate);
        return config;
    }

    public void pushEnum (final SamsungCommand key, final float timeout )
            throws IOException, InterruptedException {
        this.logD("Send to Samsung : " + key.getValue() );
        this.tvIp.sendKey( key );
        Thread.sleep((long) (1000l*timeout));
        
    }

    public void pushEnum (final SamsungCommand key)
            throws IOException, InterruptedException {      
        this.pushEnum( key, 0.250f );
    }

    public void sendText (final String text, final float timeout )
            throws IOException, InterruptedException {
        this.tvIp.sendText(text);
        Thread.sleep((long) (1000l*timeout));
    }

    public void sendText (final String text)
            throws IOException, InterruptedException {
        this.sendText( text, 0.250f );
    }

    public static boolean isInteger(final String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c <= '/' || c >= ':') {
                return false;
            }
        }
        return true;
    }
}

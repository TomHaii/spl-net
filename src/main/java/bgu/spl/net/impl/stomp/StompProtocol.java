package bgu.spl.net.impl.stomp;

import bgu.spl.net.api.StompMessagingProtocol;
import bgu.spl.net.srv.Connections;

import java.util.LinkedList;

public class StompProtocol implements StompMessagingProtocol {
    private boolean terminate;
    private int connectionId;
    private Connections<String> connections;


    @Override
    public void start(int connectionId, Connections<String> connections) {
        this.connectionId = connectionId;
        this.connections = connections;
        this.terminate = false;
    }

    @Override
    public void process(LinkedList<String> message) {
        for(String msg: message){
            if(msg.equals("CONNECT")){
                ConnectFrame connectFrame = new ConnectFrame()
            }
        }
    }

    @Override
    public boolean shouldTerminate() {
        return terminate;
    }
}

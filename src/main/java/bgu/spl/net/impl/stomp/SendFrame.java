package bgu.spl.net.impl.stomp;

import bgu.spl.net.impl.rci.Command;

import java.io.Serializable;
import java.util.LinkedList;

public class SendFrame implements Frame {
    private String destination = "";
    private String body = "";

    public SendFrame(LinkedList<String> message) {
        for(String msg: message){
            if(msg.startsWith("destination"))
                destination = msg.substring(12);
            else if(!msg.equals("^@") && !msg.equals("\n")){
                body = body + msg;
            }
        }
    }


    public String getDestination() {
        return destination;
    }

    public String getBody() {
        return body;
    }
}

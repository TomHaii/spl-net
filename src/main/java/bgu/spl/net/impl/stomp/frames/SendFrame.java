package bgu.spl.net.impl.stomp.frames;

import java.util.LinkedList;

public class SendFrame implements Frame {
    private String destination = "";
    private String body = "";

    public SendFrame(LinkedList<String> message) {
        for(String msg: message){
            if(msg.contains("destination"))
                destination = msg.split(":")[1];
            else if(!msg.equals("\u0000") && !msg.equals("\n") && !msg.equals("SEND")){
                body = String.format("%s%s", body, msg);
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

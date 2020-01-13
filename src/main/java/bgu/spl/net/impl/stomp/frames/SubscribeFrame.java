package bgu.spl.net.impl.stomp.frames;

import java.util.LinkedList;

public class SubscribeFrame implements Frame {
    private String destination;
    private int id;
    private int receipt;

    public SubscribeFrame(LinkedList<String> message) {
        for(String msg: message){
            if(msg.startsWith("destination"))
                destination = msg.split(":")[1];
            else if(msg.startsWith("id")){
                id = Integer.parseInt(msg.split(":")[1]);
            }
            else if(msg.startsWith("receipt")){
                receipt = Integer.parseInt(msg.split(":")[1]);
            }
        }
    }


    public String getDestination() {
        return destination;
    }

    public int getId() {
        return id;
    }

    public int getReceipt() {
        return receipt;
    }
}

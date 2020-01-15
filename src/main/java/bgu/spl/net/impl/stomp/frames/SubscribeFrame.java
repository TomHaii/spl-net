package bgu.spl.net.impl.stomp.frames;

import java.util.LinkedList;

public class SubscribeFrame implements Frame {
    private String destination;
    private int id;
    private int receipt;

    public SubscribeFrame(LinkedList<String> message) {
        for(String msg: message){
            if(msg.contains("destination"))
                destination = msg.split(":")[1];
            else if(msg.contains("id")){
                id = Integer.parseInt(msg.split(":")[1]);
            }
            else if(msg.contains("receipt")){
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

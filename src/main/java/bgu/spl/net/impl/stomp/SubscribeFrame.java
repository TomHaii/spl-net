package bgu.spl.net.impl.stomp;

import java.util.LinkedList;

public class SubscribeFrame implements Frame {
    private String destination;
    private int id;
    private int receipt;

    public SubscribeFrame(LinkedList<String> message) {
        for(String msg: message){
            if(msg.startsWith("destination"))
                destination = msg.substring(12);
            else if(msg.startsWith("id")){
                id = Integer.parseInt(msg.substring(3));
            }
            else if(msg.startsWith("receipt")){
                receipt = Integer.parseInt(msg.substring(7));
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

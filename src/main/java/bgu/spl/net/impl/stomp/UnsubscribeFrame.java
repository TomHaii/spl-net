package bgu.spl.net.impl.stomp;

import java.util.LinkedList;

public class UnsubscribeFrame implements Frame{
    private int id;

    public UnsubscribeFrame(LinkedList<String> message) {
        for(String msg: message){
            if(msg.startsWith("id")){
                id = Integer.parseInt(msg.substring(3));
            }
        }
    }

    public int getId() {
        return id;
    }

}

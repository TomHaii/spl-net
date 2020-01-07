package bgu.spl.net.impl.stomp;

import java.util.LinkedList;

public class UnsubscribeFrame implements Frame{
    private int id;

    public UnsubscribeFrame(LinkedList<String> message) {
        for(String msg: message){
            if(msg.contains("id")){
                id = Integer.parseInt(msg.split(":")[1]);
            }
        }
    }

    public int getId() {
        return id;
    }

}

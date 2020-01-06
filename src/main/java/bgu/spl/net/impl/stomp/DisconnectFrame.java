package bgu.spl.net.impl.stomp;

import java.util.LinkedList;

public class DisconnectFrame implements Frame {
    private String receipt;

    public DisconnectFrame(LinkedList<String> message) {
        for(String msg: message){
            if(msg.startsWith("receipt"))
                receipt = msg.substring(7);
        }
    }

    public String getReceipt() {
        return receipt;
    }

}

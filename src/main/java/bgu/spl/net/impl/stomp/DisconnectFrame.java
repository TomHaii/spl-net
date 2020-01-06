package bgu.spl.net.impl.stomp;

import java.util.LinkedList;

public class DisconnectFrame implements Frame {
    private int receipt;

    public DisconnectFrame(LinkedList<String> message) {
        for(String msg: message){
            if(msg.startsWith("receipt"))
                receipt = Integer.parseInt(msg.substring(7));
        }
    }

    public int getReceipt() {
        return receipt;
    }

}

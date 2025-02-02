package bgu.spl.net.impl.stomp.frames;

import java.util.LinkedList;

public class DisconnectFrame implements Frame {
    private int receipt;

    public DisconnectFrame(LinkedList<String> message) {
        for(String msg: message){
            if(msg.contains("receipt"))
                try {
                    receipt = Integer.parseInt(msg.split(":")[1]);
                }
                catch (NumberFormatException e){
                }
        }
    }

    public int getReceipt() {
        return receipt;
    }

}

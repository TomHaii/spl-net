package bgu.spl.net.impl.stomp;

import bgu.spl.net.impl.rci.Command;

import java.io.Serializable;

public class DisconnectFrame implements Command<String> {
    private String receipt;

    public DisconnectFrame(String receipt) {
        this.receipt = receipt;
    }


    @Override
    public Serializable execute(String arg) {
        return null;
    }

    public String getReceipt() {
        return receipt;
    }
}

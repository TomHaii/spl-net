package bgu.spl.net.impl.stomp;

import bgu.spl.net.impl.rci.Command;

import java.io.Serializable;

public class SendFrame implements Command<String> {
    private String destination;
    private String body;

    public SendFrame(String destination, String body) {
        this.destination = destination;
        this.body = body;
    }

    @Override
    public Serializable execute(String arg) {
        return null;
    }


    public String getDestination() {
        return destination;
    }

    public String getBody() {
        return body;
    }
}

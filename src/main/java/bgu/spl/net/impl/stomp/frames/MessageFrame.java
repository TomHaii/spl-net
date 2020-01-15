package bgu.spl.net.impl.stomp.frames;

public class MessageFrame implements Frame {
    private int subscription;
    private int messageID;
    private String destination;
    private String body;

    public MessageFrame(int messageID, String destination, String body) {
        this.messageID = messageID;
        this.destination = destination;
        this.body = body;
    }

    @Override
    public String toString(){
        return "MESSAGE\n" +
                "subscription:" + subscription + "\n"+
                "Message-id:" + messageID + "\n"+
                "destination:" + destination + "\n\n"+
                body +
                '\n' + '\u0000';
    }

    public void setSubscription(int subscription) {
        this.subscription = subscription;
    }
}
package bgu.spl.net.impl.stomp;

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
        return "RECEIPT\n" + "subscription:" + subscription + "\nMessage-id:" + messageID + "\ndestination:" + destination + '\n' + body + '\n' + '\u0000';
    }

    public void setSubscription(int subscription) {
        this.subscription = subscription;
    }
}
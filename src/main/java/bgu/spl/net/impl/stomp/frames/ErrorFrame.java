package bgu.spl.net.impl.stomp.frames;

public class ErrorFrame implements Frame {
    private int receiptId;
    private String message;

    public ErrorFrame(int receiptId, String message) {
        this.receiptId = receiptId;
        this.message = message;
    }

    @Override
    public String toString(){
        return "ERROR\n" +
                "receipt-id:" + receiptId + '\n'+
                "message:" + message +
                '\n' + '\u0000';
    }
}

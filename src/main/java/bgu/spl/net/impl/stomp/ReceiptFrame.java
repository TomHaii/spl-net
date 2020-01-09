package bgu.spl.net.impl.stomp;

public class ReceiptFrame implements Frame {
    private int id;

    public ReceiptFrame(int id) {
        this.id = id;
    }

    @Override
    public String toString(){
        return "RECEIPT\n" + "receipt-id:" + id +'\n' + '\u0000';
    }
}

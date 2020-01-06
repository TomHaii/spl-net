package bgu.spl.net.impl.stomp;

public class ConnectedFrame implements Frame {
    private String version;


    public ConnectedFrame(String version) {
        this.version = version;
    }

    @Override
    public String toString(){
        return "CONNECTED\n" + "version:" + version + "\n^@";
    }
}

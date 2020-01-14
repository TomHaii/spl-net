package bgu.spl.net.impl.stomp.frames;

public class ConnectedFrame implements Frame {
    private String version;


    public ConnectedFrame(String version) {
        this.version = version;
    }

    @Override
    public String toString(){
        return "CONNECTED\n" +
                "version:" + version +
                "\n" + '\u0000';
    }
}

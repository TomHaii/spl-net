package bgu.spl.net.impl.stomp.frames;
;
import java.util.LinkedList;

public class ConnectFrame implements Frame {
    private String host;
    private String login;
    private String passcode;
    private String version;

    public ConnectFrame(LinkedList<String> message) {
        for(String msg: message){
            if(msg.contains("accept-version")) {
                version = msg.split(":")[1];
            }
            else if(msg.contains("host")){
                host = msg.split(":")[1];
            }
            else if(msg.contains("login")){
                login = msg.split(":")[1];
            }
            else if(msg.contains("passcode")){
                passcode = msg.split(":")[1];
            }
        }
    }


    public String getHost() {
        return host;
    }

    public String getLogin() {
        return login;
    }

    public String getPasscode() {
        return passcode;
    }

    public String getVersion() {
        return version;
    }
}

package bgu.spl.net.impl.stomp;
;
import java.util.LinkedList;

public class ConnectFrame implements Frame {
    private String host;
    private String login;
    private String passcode;
    private String version;

    public ConnectFrame(LinkedList<String> message) {
        for(String msg: message){
            if(msg.startsWith("accept-version"))
                version = msg.substring(14);
            else if(msg.startsWith("host")){
                host = msg.substring(4);
            }
            else if(msg.startsWith("login")){
                login = msg.substring(5);
            }
            else if(msg.startsWith("passcode")){
                passcode = msg.substring(8);
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

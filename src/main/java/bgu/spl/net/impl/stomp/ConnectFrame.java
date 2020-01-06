package bgu.spl.net.impl.stomp;

import bgu.spl.net.impl.rci.Command;

import java.io.Serializable;

public class ConnectFrame implements Command<String> {
    private String host;
    private String login;
    private String passcode;

    public ConnectFrame(String host, String login, String passcode) {
        this.host = host;
        this.login = login;
        this.passcode = passcode;
    }

    @Override
    public Serializable execute(String arg) {
        return null;
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
}

package bgu.spl.net.impl.stomp.Delete;

import bgu.spl.net.impl.rci.Command;

import java.io.Serializable;


public class LoginCommand implements Command<String> {
    private String hostPort;
    private String username;
    private String password;

    public LoginCommand(String hostPort, String username, String password) {
        this.hostPort = hostPort;
        this.username = username;
        this.password = password;
    }

    @Override
    public Serializable execute(String arg) {
        return null;
    }

    public String getHostPort() {
        return hostPort;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

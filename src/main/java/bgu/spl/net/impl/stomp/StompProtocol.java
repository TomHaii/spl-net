package bgu.spl.net.impl.stomp;

import bgu.spl.net.api.StompMessagingProtocol;
import bgu.spl.net.srv.Connections;

import java.sql.Connection;
import java.util.HashMap;
import java.util.LinkedList;

public class StompProtocol implements StompMessagingProtocol {
    private boolean terminate;
    private int connectionId;
    private ConnectionsImp<String> connections;


    @Override
    public void start(int connectionId, Connections<String> connections) {
        this.connectionId = connectionId;
        this.connections = (ConnectionsImp<String>) connections;
        this.terminate = false;
    }

    @Override
    public void process(Frame message) {
        if(message instanceof ConnectFrame)
        {
            connections.
        }
    }

    private void login(String userName, String password){
        HashMap<String, String> users = connections.getUsers();
        HashMap<String, Boolean> loggedUsers = connections.getLoggedUsers();
        users.putIfAbsent(userName, password);
        loggedUsers.putIfAbsent(userName, false);
        if(connections.getLoggedUsers().get(userName)) {
            Frame frame = new ErrorFrame(connectionId, "User is already logged in");
            boolean result = connections.send(connectionId, frame);
        }else if(users.get(userName).equals(password))
            connections.send(connectionId, "Wrong password");

    }

    @Override
    public boolean shouldTerminate() {
        return terminate;
    }
}

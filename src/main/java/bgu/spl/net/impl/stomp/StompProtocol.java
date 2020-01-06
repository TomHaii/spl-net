package bgu.spl.net.impl.stomp;

import bgu.spl.net.api.StompMessagingProtocol;
import bgu.spl.net.srv.Connections;

import java.sql.Connection;
import java.util.HashMap;
import java.util.LinkedList;

public class StompProtocol implements StompMessagingProtocol {
    private boolean terminate;
    private int connectionId;
    private ConnectionsImp<Frame> connections;


    @Override
    public void start(int connectionId, Connections<Frame> connections) {
        this.connectionId = connectionId;
        this.connections = (ConnectionsImp<Frame>) connections;
        this.terminate = false;
    }

    @Override
    public void process(Frame message) {
        if(message instanceof ConnectFrame)
        {
            ConnectFrame connectFrame = (ConnectFrame) message;
            login(connectFrame.getLogin(), connectFrame.getPasscode(), connectFrame.getVersion());
        }
        if(message instanceof SubscribeFrame){

        }
    }

    private void login(String userName, String password, String version){
        HashMap<String, String> users = connections.getUsers();
        HashMap<String, Boolean> loggedUsers = connections.getLoggedUsers();
        users.putIfAbsent(userName, password);
        loggedUsers.putIfAbsent(userName, false);
        if(connections.getLoggedUsers().get(userName))
            connections.send(connectionId, new ErrorFrame(connectionId, "User is already logged in"));
        else if(users.get(userName).equals(password))
            connections.send(connectionId, new ErrorFrame(connectionId, "Wrong password"));
        else{
            loggedUsers.replace(userName, true);
            connections.send(connectionId, new ConnectedFrame(version));
        }

    }

    @Override
    public boolean shouldTerminate() {
        return terminate;
    }
}

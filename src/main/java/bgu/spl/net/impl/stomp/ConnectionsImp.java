package bgu.spl.net.impl.stomp;
import bgu.spl.net.srv.ConnectionHandler;
import bgu.spl.net.srv.Connections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class ConnectionsImp<T> implements Connections<T> {

    private HashMap<Integer, ConnectionHandler<T>> connectionHandlerConcurrentHashMap;

    private HashMap<String, String> users;
    private HashMap<String, Boolean> loggedUsers;
    private HashMap<String, LinkedList<String>> topicList;



    public ConnectionsImp() {
        connectionHandlerConcurrentHashMap = new HashMap<>();;
        topicList = new HashMap<>();
        users = new HashMap<>();
        loggedUsers = new HashMap<>();
    }


    @Override
    public boolean send(int connectionId, T msg) {
        return false;
    }

    @Override
    public void send(String channel, T msg) {

    }

    @Override
    public void disconnect(int connectionId) {

    }

    public HashMap<String, String> getUsers() {
        return users;
    }

    public HashMap<String, Boolean> getLoggedUsers() {
        return loggedUsers;
    }


}

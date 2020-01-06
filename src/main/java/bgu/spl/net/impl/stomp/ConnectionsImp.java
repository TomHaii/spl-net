package bgu.spl.net.impl.stomp;
import bgu.spl.net.srv.ConnectionHandler;
import bgu.spl.net.srv.Connections;
import java.util.HashMap;
public class ConnectionsImp<T> implements Connections<T> {

    private HashMap<Integer, ConnectionHandler<T>> connectionHandlerConcurrentHashMap = new HashMap<>();

    private HashMap<String, String> users;
    private HashMap<String, Boolean> loggedUsers;

    public ConnectionsImp(HashMap<Integer, ConnectionHandler<T>> connectionHandlerConcurrentHashMap, HashMap<String, String> users, HashMap<String, Boolean> loggedUsers) {
        this.connectionHandlerConcurrentHashMap = connectionHandlerConcurrentHashMap;
        this.users = users;
        this.loggedUsers = loggedUsers;
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

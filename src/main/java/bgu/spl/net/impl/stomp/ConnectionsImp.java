package bgu.spl.net.impl.stomp;
import bgu.spl.net.srv.ConnectionHandler;
import bgu.spl.net.srv.Connections;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionsImp<T> implements Connections<T> {

    private HashMap<Integer, ConnectionHandler<T>> connectionHandlerConcurrentHashMap;

    private HashMap<String, String> users;
    private ConcurrentHashMap<Integer, String> userIds;
    private ConcurrentHashMap<String, Boolean> loggedUsers;
    private ConcurrentHashMap<String, LinkedList<Integer>>topicList;
    private ConcurrentHashMap<Integer, String> topicsBySubscriptionsId;
    private AtomicInteger connectionId = new AtomicInteger(0);


    public ConnectionsImp() {
        connectionHandlerConcurrentHashMap = new HashMap<>();;
        topicsBySubscriptionsId = new ConcurrentHashMap<>();
        topicList = new ConcurrentHashMap<>();
        users = new HashMap<>();
        loggedUsers = new ConcurrentHashMap<>();
        userIds = new ConcurrentHashMap<>();
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

    public ConcurrentHashMap<String, Boolean> getLoggedUsers() {
        return loggedUsers;
    }


    public ConcurrentHashMap<String, LinkedList<Integer>>getTopicList() {
        return topicList;
    }

    public ConcurrentHashMap<Integer, String> getUserIds() {
        return userIds;
    }

    public ConcurrentHashMap<Integer, String> getTopicsBySubscriptionsId() {
        return topicsBySubscriptionsId;
    }
}

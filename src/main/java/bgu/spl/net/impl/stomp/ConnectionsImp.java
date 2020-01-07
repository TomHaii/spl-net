package bgu.spl.net.impl.stomp;
import bgu.spl.net.srv.ConnectionHandler;
import bgu.spl.net.srv.Connections;
import javafx.util.Pair;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionsImp<T> implements Connections<T> {

    private HashMap<Integer, ConnectionHandler<T>> connectionHandlerConcurrentHashMap;

    private HashMap<String, String> users;
    private ConcurrentHashMap<String, Boolean> loggedUsers;
    private ConcurrentHashMap<String, LinkedList<Pair<Integer, Integer>>> topicList;
    private ConcurrentHashMap<Integer, String> topicsBySubscriptionsId;
    private AtomicInteger messageId = new AtomicInteger(0);


    public ConnectionsImp() {
        connectionHandlerConcurrentHashMap = new HashMap<>();;
        topicsBySubscriptionsId = new ConcurrentHashMap<>();
        topicList = new ConcurrentHashMap<>();
        users = new HashMap<>();
        loggedUsers = new ConcurrentHashMap<>();
    }


    @Override
    public boolean send(int connectionId, T msg) {
        System.out.println("Sending on connectionsImp");
        if(connectionHandlerConcurrentHashMap.containsKey(connectionId)){
            System.out.println("found connectionsId");
            connectionHandlerConcurrentHashMap.get(connectionId).send(msg);
            return true;
        }
        return false;
    }

    @Override
    public void send(String channel, T msg) {
        MessageFrame msgFrame = (MessageFrame) msg;
        for (Pair<Integer,Integer> pair : topicList.get(channel)){
            int connectionId = pair.getKey();
            int subscriberId = pair.getValue();
            msgFrame.setSubscription(subscriberId);
            connectionHandlerConcurrentHashMap.get(connectionId).send(msg);
        }

    }

    @Override
    public void disconnect(int connectionId) {
        connectionHandlerConcurrentHashMap.remove(connectionId);
    }

    public void connect(int connectionId, ConnectionHandler<T> connectionHandler){
        connectionHandlerConcurrentHashMap.putIfAbsent(connectionId, connectionHandler);
    }

    public HashMap<String, String> getUsers() {
        return users;
    }

    public ConcurrentHashMap<String, Boolean> getLoggedUsers() {
        return loggedUsers;
    }


    public ConcurrentHashMap<String, LinkedList<Pair<Integer, Integer>>> getTopicList() {
        return topicList;
    }



    public ConcurrentHashMap<Integer, String> getTopicsBySubscriptionsId() {
        return topicsBySubscriptionsId;
    }


    public int getMessageId() {
        return messageId.get();
    }
    public void incMessageId(){
        messageId.compareAndSet(messageId.get(), messageId.get()+1);
    }
}
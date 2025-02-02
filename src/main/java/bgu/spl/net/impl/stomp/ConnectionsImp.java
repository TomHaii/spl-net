package bgu.spl.net.impl.stomp;
import bgu.spl.net.impl.stomp.frames.Frame;
import bgu.spl.net.impl.stomp.frames.MessageFrame;
import bgu.spl.net.srv.ConnectionHandler;
import bgu.spl.net.srv.Connections;
import javafx.util.Pair;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionsImp<T> implements Connections<T> {

    private HashMap<Integer, ConnectionHandler<T>> connectionHandlers;

    private HashMap<String, String> users;
    private ConcurrentHashMap<String, Boolean> loggedUsers;
    private HashMap<String, LinkedList<Pair<Integer, Integer>>> topicList;
    private HashMap<Integer, String> topicsBySubscriptionsId;
    private AtomicInteger messageId = new AtomicInteger(1);


    public ConnectionsImp() {
        connectionHandlers = new HashMap<>();;
        topicsBySubscriptionsId = new HashMap<>();
        topicList = new HashMap<>();
        users = new HashMap<>();
        loggedUsers = new ConcurrentHashMap<>();
    }


    @Override
    public boolean send(int connectionId, T msg) {
        if(connectionHandlers.containsKey(connectionId)){
            connectionHandlers.get(connectionId).send(msg);
            return true;
        }
        return false;
    }

    @Override
    public void send(String channel, T msg) {
        int i = 0;
        if (topicList.get(channel) != null) {
            for (Pair<Integer, Integer> pair : topicList.get(channel)) {
                int connectionId = pair.getKey();
                int subscriberId = pair.getValue();
                ((MessageFrame)msg).setSubscription(subscriberId);
                send(connectionId, msg);
            }
        }
    }

    @Override
    public void disconnect(int connectionId) {
        connectionHandlers.remove(connectionId);
    }

    public void connect(int connectionId, ConnectionHandler<T> connectionHandler){
        connectionHandlers.putIfAbsent(connectionId, connectionHandler);
    }

    public HashMap<String, String> getUsers() {
        return users;
    }

    public ConcurrentHashMap<String, Boolean> getLoggedUsers() {
        return loggedUsers;
    }


    public HashMap<String, LinkedList<Pair<Integer, Integer>>> getTopicList() {
        return topicList;
    }



    public HashMap<Integer, String> getTopicsBySubscriptionsId() {
        return topicsBySubscriptionsId;
    }


    public int getMessageId() {
        return messageId.get();
    }
    public void incMessageId(){
        messageId.compareAndSet(messageId.get(), messageId.get()+1);
    }
}
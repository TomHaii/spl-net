package bgu.spl.net.impl.stomp;

import bgu.spl.net.api.StompMessagingProtocol;
import bgu.spl.net.impl.stomp.frames.*;
import bgu.spl.net.srv.Connections;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

public class StompProtocol implements StompMessagingProtocol {
    private boolean terminate;
    private int connectionId;
    private String currentUser;
    private ConnectionsImp<Frame> connections;
    private LinkedList<String> topics;
    private HashMap<Integer, String> topicsBySubscriptionsId;

    @Override
    public void start(int connectionId, Connections<Frame> connections) {
        this.connectionId = connectionId;
        this.connections = (ConnectionsImp<Frame>) connections;
        terminate = false;
        topics = new LinkedList<>();
        topicsBySubscriptionsId = new HashMap<>();

    }


    @Override
    public void process(Frame message) {
        if (message instanceof ConnectFrame) {
            ConnectFrame connectFrame = (ConnectFrame) message;
            login(connectFrame.getLogin(), connectFrame.getPasscode(), connectFrame.getVersion());
        } else if (message instanceof SubscribeFrame) {
            SubscribeFrame subscribeFrame = (SubscribeFrame) message;
            String topic = subscribeFrame.getDestination();
            if(!topics.contains(topic))
                topics.add(topic);
            connections.getTopicList().putIfAbsent(subscribeFrame.getDestination(), new LinkedList<>());
            connections.getTopicList().get(subscribeFrame.getDestination()).add(new Pair<Integer, Integer>(connectionId, subscribeFrame.getId()));
            topicsBySubscriptionsId.putIfAbsent(subscribeFrame.getId(),topic);
            connections.send(connectionId, new ReceiptFrame(subscribeFrame.getReceipt()));
            System.out.println("User " +currentUser +" has subscribed to topic " + topic);
        } else if (message instanceof DisconnectFrame) {
            System.out.println("User " +currentUser +" is trying to logout");
            DisconnectFrame disconnectFrame = (DisconnectFrame) message;
//            for (String topic : topics) {
//                connections.getTopicList().get(topic).removeIf(pair -> pair.getKey() == connectionId);
//            }
            connections.send(connectionId, new ReceiptFrame(disconnectFrame.getReceipt()));
            connections.disconnect(connectionId);
            connections.getLoggedUsers().replace(currentUser, false);
            System.out.println("User " +currentUser +" has logged out successfully");
            terminate = true;

        }
        else if (message instanceof UnsubscribeFrame) {
            UnsubscribeFrame unsubscribeFrame = (UnsubscribeFrame) message;
            int subscriptionId = unsubscribeFrame.getId();
            String topic = topicsBySubscriptionsId.get(subscriptionId);
            connections.getTopicList().get(topic).removeIf(pair -> pair.getKey() == connectionId);
            topics.remove(topic);
            connections.send(connectionId, new ReceiptFrame(subscriptionId));
            System.out.println("User "+currentUser + " has unsubscribed from topic" + topic);

        }
        else if (message instanceof SendFrame) {
            SendFrame sendFrame = (SendFrame) message;
            int msgId = connections.getMessageId();
            String dest = sendFrame.getDestination();
            String body = sendFrame.getBody();
            connections.send(dest, new MessageFrame(msgId, dest, body));
            connections.incMessageId();
            System.out.println(currentUser + " has sent a message: " + body.substring(0, body.length() -2));

        }
    }


    private void login(String userName, String password, String version){
        HashMap<String, String> users = connections.getUsers();
        ConcurrentHashMap<String, Boolean> loggedUsers = connections.getLoggedUsers();
        users.putIfAbsent(userName, password);
        System.out.println("User " + userName + " is trying to login");
        loggedUsers.putIfAbsent(userName, false);
        if(!userName.isEmpty())
            currentUser = userName;
        if(connections.getLoggedUsers().get(userName))
            connections.send(connectionId, new ErrorFrame(connectionId, "User is already logged in"));
        else if(!users.get(userName).equals(password))
            connections.send(connectionId, new ErrorFrame(connectionId, "Wrong password"));
        else{
            loggedUsers.replace(userName, true);
            connections.send(connectionId, new ConnectedFrame(version));
            System.out.println("User " + userName + " has logged in successfully");

        }

    }

    @Override
    public boolean shouldTerminate() {
        return terminate;
    }
}
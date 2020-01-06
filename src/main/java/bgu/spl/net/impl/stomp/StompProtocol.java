package bgu.spl.net.impl.stomp;

import bgu.spl.net.api.StompMessagingProtocol;
import bgu.spl.net.srv.Connections;
import javafx.util.Pair;

import java.sql.Connection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

public class StompProtocol implements StompMessagingProtocol {
    private boolean terminate;
    private int connectionId;
    private ConnectionsImp<Frame> connections;
    private LinkedList<String> topics;

    @Override
    public void start(int connectionId, Connections<Frame> connections) {
        this.connectionId = connectionId;
        this.connections = (ConnectionsImp<Frame>) connections;
        terminate = false;
        topics = new LinkedList<>();
    }

    @Override
    public void process(Frame message) {
        if (message instanceof ConnectFrame) {
            ConnectFrame connectFrame = (ConnectFrame) message;
            login(connectFrame.getLogin(), connectFrame.getPasscode(), connectFrame.getVersion());
        } else if (message instanceof SubscribeFrame) {
            SubscribeFrame subscribeFrame = (SubscribeFrame) message;
            String topic = subscribeFrame.getDestination();
            topics.add(topic);
            connections.getTopicList().putIfAbsent(subscribeFrame.getDestination(), new LinkedList<>());
            connections.getTopicList().get(subscribeFrame.getDestination()).add(connectionId);
            connections.getTopicsBySubscriptionsId().putIfAbsent(subscribeFrame.getId(),topic);
            connections.send(connectionId, new ReceiptFrame(subscribeFrame.getReceipt()));
        } else if (message instanceof DisconnectFrame) {
            DisconnectFrame disconnectFrame = (DisconnectFrame) message;
            for (String topic : topics) {
                connections.getTopicList().get(topic).remove((Integer)connectionId);
            }
            connections.send(connectionId, new ReceiptFrame(disconnectFrame.getReceipt()));
        }
        else if (message instanceof UnsubscribeFrame) {
            UnsubscribeFrame unsubscribeFrame = (UnsubscribeFrame) message;
            int subscriptionId = unsubscribeFrame.getId();
            String topic = connections.getTopicsBySubscriptionsId().get(subscriptionId);
            connections.getTopicList().get(topic).remove((Integer)connectionId);
            topics.remove(topic);
            connections.send(connectionId, new ReceiptFrame(subscriptionId));
        }
        else if (message instanceof SendFrame) {

        }
    }


    private void login(String userName, String password, String version){
        HashMap<String, String> users = connections.getUsers();
        ConcurrentHashMap<String, Boolean> loggedUsers = connections.getLoggedUsers();
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

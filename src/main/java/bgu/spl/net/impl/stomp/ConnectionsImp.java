package bgu.spl.net.impl.stomp;

import bgu.spl.net.srv.ConnectionHandler;
import bgu.spl.net.srv.Connections;

import java.util.concurrent.ConcurrentHashMap;

public class ConnectionsImp<T> implements Connections<T> {
    private ConcurrentHashMap<Integer, ConnectionHandler<T>> connectionHandlerConcurrentHashMap = new ConcurrentHashMap<>();

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
}

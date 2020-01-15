package bgu.spl.net.srv;

import bgu.spl.net.impl.stomp.ConnectionsImp;
import bgu.spl.net.impl.stomp.frames.Frame;
import bgu.spl.net.impl.stomp.StompEncoderDecoder;
import bgu.spl.net.impl.stomp.StompProtocol;

import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class BaseServer<T> implements Server<T> {

    private final int port;
    private final Supplier<StompProtocol> protocolFactory;
    private final Supplier<StompEncoderDecoder> encdecFactory;
    private ServerSocket sock;
    private ConnectionsImp<Frame> connections;
    private AtomicInteger connectionsNum;

    public BaseServer(
            int port,
            Supplier<StompProtocol> protocolFactory,
            Supplier<StompEncoderDecoder> encdecFactory) {
        connectionsNum = new AtomicInteger(1);
        connections = new ConnectionsImp<>();
        this.port = port;
        this.protocolFactory = protocolFactory;
        this.encdecFactory = encdecFactory;
        this.sock = null;
    }

    @Override
    public void serve() {
        try (ServerSocket serverSock = new ServerSocket(port)) {
            System.out.println("Server started");
            this.sock = serverSock; //just to be able to close
            while (!Thread.currentThread().isInterrupted()) {
                Socket clientSock = serverSock.accept();
                BlockingConnectionHandler handler = new BlockingConnectionHandler(
                        clientSock,
                        encdecFactory.get(),
                        protocolFactory.get(), connectionsNum.get(), connections);
                connections.connect(connectionsNum.getAndIncrement(), handler);
                execute(handler);
            }
        } catch (IOException ex) {
        }

        System.out.println("server closed!!!");
    }

    @Override
    public void close() throws IOException {
        if (sock != null)
            sock.close();
    }


    protected void execute(BlockingConnectionHandler handler){
        new Thread(handler).start();
    }

}
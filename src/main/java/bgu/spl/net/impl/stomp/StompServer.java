package bgu.spl.net.impl.stomp;

import bgu.spl.net.srv.BaseServer;
import bgu.spl.net.srv.BlockingConnectionHandler;

import java.util.function.Supplier;

public class StompServer extends BaseServer {

    public StompServer(int port, Supplier protocolFactory, Supplier encdecFactory) {
        super(port, protocolFactory, encdecFactory);
    }

    public static void main(String[] args) {


    }


    @Override
    protected void execute(BlockingConnectionHandler handler) {

    }
}

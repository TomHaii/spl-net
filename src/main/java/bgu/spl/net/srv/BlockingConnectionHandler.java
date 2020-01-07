package bgu.spl.net.srv;
import bgu.spl.net.impl.stomp.ConnectionsImp;
import bgu.spl.net.impl.stomp.Frame;
import bgu.spl.net.impl.stomp.StompEncoderDecoder;
import bgu.spl.net.impl.stomp.StompProtocol;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

public class BlockingConnectionHandler implements Runnable, ConnectionHandler<Frame> {
    private ConnectionsImp<Frame> connections;
    private final StompProtocol protocol;
    private final StompEncoderDecoder encdec;
    private final Socket sock;
    private BufferedInputStream in;
    private BufferedOutputStream out;
    private volatile boolean connected = true;
    private int id;

    public BlockingConnectionHandler(Socket sock, StompEncoderDecoder reader, StompProtocol protocol, int id, ConnectionsImp<Frame> connections) {
        this.sock = sock;
        this.encdec = reader;
        this.protocol = protocol;
        this.id = id;
        this.connections = connections;
    }

    @Override
    public void run() {
        try (Socket sock = this.sock) { //just for automatic closing
            this.protocol.start(id, connections);
            int read;
            in = new BufferedInputStream(sock.getInputStream());
            out = new BufferedOutputStream(sock.getOutputStream());
            while (!protocol.shouldTerminate() && connected && (read = in.read()) >= 0) {
                System.out.println("I am here on the connection waiting :(");
                Frame nextMessage = encdec.decodeNextByte((byte) read);
                if (nextMessage != null) {
                    protocol.process(nextMessage);
                    out.flush();
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void close() throws IOException {
        connected = false;
        sock.close();
    }

    @Override
    public void send(Frame msg) {
        System.out.println("I AM SENDING  " + msg.toString());
        byte[] bytesMsg = encdec.encode(msg);
        try {
            out.write(bytesMsg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
package bgu.spl.net.impl.stomp;
import bgu.spl.net.srv.Server;

public class StompServer {

    public static void main(String[] args) {
        switch (args[1]){
            case "tpc":
                System.out.println("starting tpc server");
                Server.threadPerClient(Integer.parseInt(args[0]), StompProtocol::new, StompEncoderDecoder::new).serve();
                break;
            case "reactor":
                System.out.println("Starting reactor server");
                Server.reactor(
                        Runtime.getRuntime().availableProcessors(),
                        Integer.parseInt(args[0]), StompProtocol::new, StompEncoderDecoder::new).serve();
                break;
        }
    }
}

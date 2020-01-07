package bgu.spl.net.impl.stomp;
import bgu.spl.net.srv.Server;

public class StompServer {

    public static void main(String[] args) {
        switch (args[1]){
            case "tpc":
                Server.threadPerClient(Integer.parseInt(args[0]), StompProtocol::new, StompEncoderDecoder::new).serve();
                break;
            case "reactor":
//                Server.reactor(
//                        Runtime.getRuntime().availableProcessors(),
//                        Integer.parseInt(args[0]), StompProtocol::new, StompEncoderDecoder::new).serve();
                break;
        }


    }
}

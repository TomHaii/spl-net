package bgu.spl.net.impl.stomp;

import bgu.spl.net.api.MessageEncoderDecoder;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedList;

public class StompEncoderDecoder implements MessageEncoderDecoder<Frame> {

    private byte[] bytes = new byte[1 << 10]; //start with 1k
    private LinkedList<String> message = new LinkedList<>();
    private int len = 0;

    @Override
    public Frame decodeNextByte(byte nextByte) {
        //notice that the top 128 ascii characters have the same representation as their utf-8 counterparts
        //this allow us to do the following comparison
        if (nextByte == '\u0000') {
            pushByte(nextByte);
            message.addLast(popString());
            return buildFrame(message);
        }
        if (nextByte == '\n') {
            message.addLast(popString());
        }
        pushByte(nextByte);
        return null; //not a full message yet
    }

    private Frame buildFrame(LinkedList<String> message) {
        String firstWord = message.getFirst();
        System.out.println(firstWord);
        switch (firstWord) {
            case "CONNECT":
                return new ConnectFrame(message);
            case "SEND":
                return new SendFrame(message);
            case "SUBSCRIBE":
                return new SubscribeFrame(message);
            case "DISCONNECT":
                return new DisconnectFrame(message);
            case "UNSUBSCRIBE":
                return new UnsubscribeFrame(message);
            default:
                System.out.println("Invalid Stomp Command");
                return null;
        }
    }


    @Override
    public byte[] encode(Frame message) {
        return message.toString().getBytes(); //uses utf8 by default
    }

    private void pushByte(byte nextByte) {
        if (len >= bytes.length) {
            bytes = Arrays.copyOf(bytes, len * 2);
        }
        bytes[len++]=nextByte;
    }

    private String popString() {
        //notice that we explicitly requesting that the string will be decoded from UTF-8
        //this is not actually required as it is the default encoding in java.
        String result = new String(bytes, 0, len, StandardCharsets.UTF_8);
        len = 0;
        return result;
    }
}
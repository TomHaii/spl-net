package bgu.spl.net.impl.stomp;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.impl.stomp.frames.*;

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
            message.addLast(popString());
            return buildFrame();
        }
        if (nextByte == '\n') {
            message.addLast(popString());
        }
        pushByte(nextByte);
        return null; //not a full message yet
    }

    private Frame buildFrame() {
        String firstWord = message.getFirst();
        switch (firstWord) {
            case "CONNECT":
                ConnectFrame connectFrame = new ConnectFrame(message);
                message.clear();
                return connectFrame;
            case "SEND":
                SendFrame sendFrame = new SendFrame(message);
                message.clear();
                return sendFrame;
            case "SUBSCRIBE":
                SubscribeFrame subscribeFrame = new SubscribeFrame(message);
                message.clear();
                return subscribeFrame;
            case "DISCONNECT":
                DisconnectFrame disconnectFrame = new DisconnectFrame(message);
                message.clear();
                return disconnectFrame;
            case "UNSUBSCRIBE":
                UnsubscribeFrame unsubscribeFrame = new UnsubscribeFrame(message);
                message.clear();
                return unsubscribeFrame;
            default:
                message.clear();
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
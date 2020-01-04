package bgu.spl.net.impl.stomp;

import bgu.spl.net.api.MessageEncoderDecoder;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedList;

public class StompEncoderDecoder implements MessageEncoderDecoder<LinkedList<String>> {

    private byte[] bytes = new byte[1 << 10]; //start with 1k
    private int len = 0;

    @Override
    public LinkedList<String> decodeNextByte(byte nextByte) {
        //notice that the top 128 ascii characters have the same representation as their utf-8 counterparts
        //this allow us to do the following comparison
        LinkedList<String> message = new LinkedList<>();
        if (nextByte == '\u0000') {
            pushByte(nextByte);
            message.addLast(popString());
            return message;
        }
        if (nextByte == '\n') {
             message.addLast(popString());
        }
        pushByte(nextByte);
        return null; //not a full message yet
    }

    @Override
    public byte[] encode(LinkedList<String> message) {
        String toBytes = "";
        for (String s: message){
            toBytes = toBytes + s +"\n";
        }
        return toBytes.getBytes(); //uses utf8 by default
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

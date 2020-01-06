package bgu.spl.net.impl.stomp.Delete;

import bgu.spl.net.impl.rci.Command;

import java.io.Serializable;

public class ReturnBookCommand implements Command<String> {
    private String bookName;
    private String genreName;

    public ReturnBookCommand(String bookName, String genreName) {
        this.bookName = bookName;
        this.genreName = genreName;
    }

    @Override
    public Serializable execute(String arg) {
        return null;
    }

    public String getBookName() {
        return bookName;
    }

    public String getGenreName() {
        return genreName;
    }
}

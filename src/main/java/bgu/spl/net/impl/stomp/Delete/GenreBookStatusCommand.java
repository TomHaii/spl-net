package bgu.spl.net.impl.stomp.Delete;

import bgu.spl.net.impl.rci.Command;

import java.io.Serializable;

public class GenreBookStatusCommand implements Command<String> {
    private String genreName;

    public GenreBookStatusCommand(String genreName) {
        this.genreName = genreName;
    }

    @Override
    public Serializable execute(String arg) {
        return null;
    }

    public String getGenreName() {
        return genreName;
    }
}

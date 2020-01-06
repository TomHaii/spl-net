package bgu.spl.net.impl.stomp.Delete;

import bgu.spl.net.impl.rci.Command;

import java.io.Serializable;

public class JoinGenreCommand implements Command<String> {
    private String genreName;

    public JoinGenreCommand(String genreName) {
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

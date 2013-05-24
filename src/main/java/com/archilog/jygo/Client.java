package com.archilog.jygo;

public class Client {
    private int id;
    private String pseudo;

    public Client(String pseudo) {
        this.id = 1;
        this.pseudo = pseudo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
}

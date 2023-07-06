package com.example.projeto;

import java.util.UUID;

public class User {

    UUID userID;
    String email;
    String nome;

    public UUID getUserID() {
        return userID;
    }

    public void setUserID(UUID userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public User(UUID userID, String email, String nome) {
        this.userID = userID;
        this.email = email;
        this.nome = nome;
    }

    public User(String email, String nome) {
        this.email = email;
        this.nome = nome;
    }
}

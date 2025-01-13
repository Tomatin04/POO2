package org.example.Model.objects;

public class Email {
    
    private int id;
    private String email;
    private int user;    // iduser

    public Email(String email) {
        this.email = email;
    }

    public Email(int id, String email) {
        this.id = id;
        this.email = email;
    }

    public Email(int id, String email, int user) {
        this.id = id;
        this.email = email;
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

}

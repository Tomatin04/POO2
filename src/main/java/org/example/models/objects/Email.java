package org.example.models.objects;

public class Email {
    
    private int id;
    private String email;
    private int user;    
    private int organizer;    

    public Email(String email) {
        this.email = email;
    }

    public Email(int id, String email) {
        this.id = id;
        this.email = email;
        this.user = 0;
        this.organizer = 0;
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

    public int getOrganizer() {
        return organizer;
    }

    public void setOrganizer(int organizer) {
        this.organizer = organizer;
    }

}

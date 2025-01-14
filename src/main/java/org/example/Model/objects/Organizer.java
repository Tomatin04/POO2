package org.example.Model.objects;

public class Organizer{

    private int id;
    private String organizer;
    private int user;
    private Email email;
    
    public Organizer() {
        this.id = 0;
        this.organizer = "";
        this.user = 0;
        this.email = new Email("");
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getOrganizer() {
        return this.organizer;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getUser() {
        return this.user;
    }

    public void setEmail(String email) {
        this.email.setEmail(email);
    }

    public String getEmail() {
        return this.email.getEmail();
    }

    public void setEmailObject(Email email) {
        this.email = email;
    }

    public Email getEmailObject() {
        return this.email;
    }

    public void setEmailId(int id) {
        this.email.setId(id);
    }

    public int getEmailId() {
        return this.email.getId();
    }

}

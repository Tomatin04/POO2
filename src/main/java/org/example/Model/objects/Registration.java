package org.example.Model.objects;

public class Registration {
 
    private int id;
    private int user;
    private Event event;
    private String status;
    private String registrationDate;
    
    public Registration() {
        this.id = 0;
        this.user = 0;
        this.event = new Event();
        this.status = "";
        this.registrationDate = "";
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getUser() {
        return this.user;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Event getEvent() {
        return this.event;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getRegistrationDate() {
        return this.registrationDate;
    }
}
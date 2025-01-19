package org.example.models.objects;

public class Registration {
 
    private int id;
    private int user;
    private int event;
    private enum Status {
        CALCELLED, WAITING_PAYMENT, WAITING_RSVP, CONFIRMED
    }
    private Status status;
    private String registrationDate;
    
    public Registration() {
        this.id = 0;
        this.user = 0;
        this.event = 0;
        this.status = Status.WAITING_PAYMENT;
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

    public void setEvent(int event) {
        this.event = event;
    }

    public int getEvent() {
        return this.event;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getRegistrationDate() {
        return this.registrationDate;
    }
}
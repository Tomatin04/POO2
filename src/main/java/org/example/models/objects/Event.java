package org.example.models.objects;

public class Event {
 
    private int id;
    private String title;
    private String description;
    private String category;
    private String date;
    private String time;
    private int duration;
    private String location;
    private String adress;
    private float price;
    private int organizer;
    private int maxCapacity;
    public enum Status {
        CLOSED, OPEN, TERMINATED, CANCEL, DESACTIVATED
    }
    private Status status;

    public Event() {
        this.id = 0;
        this.title = "";
        this.description = "";
        this.category = "";
        this.date = "";
        this.time = "";
        this.duration = 0;
        this.location = "";
        this.adress = "";
        this.price = 0;
        this.status = Status.CLOSED;
        this.maxCapacity = 0;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return this.category;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return this.date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return this.time;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return this.location;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getAdress() {
        return this.adress;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getPrice() {
        return this.price;
    }

    public void setOrganizer(int organizer) {
        this.organizer = organizer;
    }

    public int getOrganizer() {
        return this.organizer;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getMaxCapacity() {
        return this.maxCapacity;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return this.status;
    }
}
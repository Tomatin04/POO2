package org.example.controllers;

import java.util.List;

import org.example.models.objects.Event;

public class EventController {
 
    public static String createEvent() {
        String sqlQuery;
        sqlQuery = "INSERT INTO events (title, description, category, date, time, duration, location, adress, price, organizer, maxcapacity, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return sqlQuery;
    }

    public static List<?> createEventList(Event event){
        return List.of(event.getTitle(), event.getDescription(), event.getCategory(), event.getDate(), event.getTime(), event.getDuration(), event.getLocation(), event.getAdress(), event.getPrice(), event.getOrganizer(), event.getMaxCapacity(), event.getStatus());            
    }

    public static String deleteEvent() {
        String sqlQuery;
        sqlQuery = "DELETE FROM events LEFT JOIN registrations ON events.idevent = registration.event WHERE idorganizer = ? AND(registrations.status = 'cancelled' OR (registations.status = 'CANCELLED' (registations.status = 'CONFIRMED' events.date < NOW()))";
        return sqlQuery;
    }

    public static List<?> deleteEventList(Event event) {
        return List.of(event.getId());
    }

    public static String updateEvent() {
        String sqlQuery;
        sqlQuery = "UPDATE events SET title = ?, description = ?, category = ?, date = ?, time = ?, duration = ?, location = ?, adress = ?, price = ?, organizer = ?, maxcapacity = ?, status = ? WHERE id = ?";
        return sqlQuery;
    }

    public static List<?> updateEventList(Event event) {
        return List.of(event.getTitle(), event.getDescription(), event.getCategory(), event.getDate(), event.getTime(), event.getDuration(), event.getLocation(), event.getAdress(), event.getPrice(), event.getOrganizer(), event.getMaxCapacity(), event.getStatus(), event.getId());
    }

    public static String findEvent(int id) {
        String sqlQuery;
        sqlQuery = "SELECT * FROM events WHERE id = " + id + ";";
        return sqlQuery;
    }

    public static String listEvents() {
        String sqlQuery;
        sqlQuery = "SELECT * FROM events";
        return sqlQuery;
    }

}
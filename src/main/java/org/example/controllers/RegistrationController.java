package org.example.controllers;

import java.util.List;

import org.example.models.objects.Registration;

public class RegistrationController {
    
    public static String createRegistration() {
        String sqlQuery;
        sqlQuery = "INSERT INTO registrations (event, user, status) VALUES (?, ?, ?)";
        return sqlQuery;
    }

    public static List<?> createRegistrationList(Registration registration) {
        return List.of(registration.getEvent(), registration.getUser(), registration.getStatus());
    }

    public static String deleteRegistration() {
        String sqlQuery;
        sqlQuery = "DELETE FROM registrations WHERE event = ? AND user = ?";
        return sqlQuery;
    }

    public static List<?> deleteRegistrationList(Registration registration) {
        return List.of(registration.getEvent(), registration.getUser());
    }

    public static String updateRegistration() {
        String sqlQuery;
        sqlQuery = "UPDATE registrations SET status = ? WHERE event = ? AND user = ?";
        return sqlQuery;
    }

    public static List<?> updateRegistrationList(Registration registration) {
        return List.of(registration.getStatus(), registration.getEvent(), registration.getUser());
    }

    public static String findRegistration() {
        String sqlQuery;
        sqlQuery = "SELECT * FROM registrations WHERE event = ? AND user = ?";
        return sqlQuery;
    }

    public static List<?> findRegistrationList(Registration registration) {
        return List.of(registration.getEvent(), registration.getUser());
    }

    public static String listRegistrations() {
        String sqlQuery;
        sqlQuery = "SELECT * FROM registrations";
        return sqlQuery;
    }

}
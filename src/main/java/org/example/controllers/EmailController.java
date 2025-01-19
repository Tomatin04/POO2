package org.example.controllers;

import java.util.List;

import org.example.models.objects.Email;

public class EmailController {
    
    public static String listEmails() {
        String sqlQuery;
        sqlQuery = "SELECT * FROM emails;";
        return sqlQuery;
    }

    public static String findEmail(int id) {
        String sqlQuery = "SELECT * FROM emails WHERE idemail = " + id + ";";
        return sqlQuery;
    }

    public static String findEmail(String email) {
        String sqlQuery = "SELECT * FROM emails WHERE email = '" + email + "';";
        return sqlQuery;
    }

    public static String createEmail() {
        String sqlQuery;
        sqlQuery = "INSERT INTO emails (email) VALUES (?);";
        return sqlQuery;
    }

    public static String updateEmail() {
        String sqlQuery;
        sqlQuery = "UPDATE emails SET email = ? WHERE email = ?;";
        return sqlQuery;
    }

    public static List<?> updateEmailList(String email, String email2) {
        return List.of(email, email2);
    }

    public static String updateUser() {
        String sqlQuery;
        sqlQuery = "UPDATE emails SET user = ? WHERE email = ?;";
        return sqlQuery;
    }

    public static List<?> updateUserList(Email email) {
        return List.of(email.getUser(), email.getEmail());
    }

    public static String updateOrganizer() {
        String sqlQuery;
        sqlQuery = "UPDATE emails SET organizer = ? WHERE email = ?;";
        return sqlQuery;
    }

    public static List<?> updateOrganizerList(Email email) {
        return List.of(email.getOrganizer(), email.getEmail());
    }

    public static String updateAllInformationEmail() {
        String sqlQuery;
        sqlQuery = "UPDATE emails SET email = ?, user = ?, organizer = ? WHERE email = ?;";
        return sqlQuery;
    }

    public static List<?> updateAllInformationEmailList(Email email) {
        return List.of(email.getEmail(), email.getUser(), email.getOrganizer(), email.getEmail());
    }

    public static String deleteEmail() {
        String sqlQuery;
        sqlQuery = "DELETE FROM emails WHERE email = ?;";
        return sqlQuery;
    }

    public static List<?> createEmailList(String email) {
        return List.of(email);
    }

    public static List<?> createEmailList(String email, String email2) {
        return List.of(email, email2);
    }
}
package org.example.Contorlers;

import java.util.List;

public class EmailController {
    
    public static String listEmails() {
        String sqlQuery;
        sqlQuery = "SELECT * FROM emails;";
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
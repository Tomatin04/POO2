package org.example.Contorlers;

public class EmailController {
    
    public String listEmails() {
        String sqlQuery;
        sqlQuery = "SELECT * FROM emails;";
        return sqlQuery;
    }

    public String findEmail(String email) {
        String sqlQuery = "SELECT * FROM emails WHERE email = '" + email + "';";
        return sqlQuery;
    }

    public String createEmail(String email) {
        String sqlQuery;
        sqlQuery = "INSERT INTO emails (email) VALUES ('" + email + "');";
        return sqlQuery;
    }

    public String updateEmail(String updatedEmail, String outdatedEmail) {
        String sqlQuery;
        sqlQuery = "UPDATE emails SET email = '" + updatedEmail + "' WHERE email = '" + outdatedEmail + "';";
        return sqlQuery;
    }

    public String deleteEmail(String email) {
        String sqlQuery;
        sqlQuery = "DELETE FROM emails WHERE email = '" + email + "';";
        return sqlQuery;
    }

}

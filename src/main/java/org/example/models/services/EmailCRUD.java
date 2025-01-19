package org.example.models.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

import org.example.controllers.EmailController;
import org.example.controllers.TokenController;
import org.example.models.objects.Email;
import org.example.models.objects.Token;
import org.example.models.objects.User;
import org.example.controllers.DbController;

public class EmailCRUD {
 
    private static String avoidingDuplicates(Connection conn, String email){
        try {
            ResultSet rs = DbController.executeQuery(conn, EmailController.findEmail(email));
            if (rs.next()) {
                return "Email already exists.";
            }else{
                return "Email not found.";
            }
        } catch (Exception e) {
            return "Error while creating email.";
        }
    }


    public static int createEmail(Connection conn, String email){
        try {
            String result = avoidingDuplicates(conn, email);
            if (result == "Email not found.") {
                return DbController.executeStatment(conn, EmailController.createEmail(), java.util.Collections.singletonList(email)) ? 200 : 400;
            }else{
                return 400;
            }
        } catch (Exception e) {
            return 400;
        }
    }

    public static List<Email> listAllEmails(Connection conn, User user, Token tokenModel){
        boolean authorizate = TokenController.authorizate(user.getToken(), tokenModel, user.getId());
        if (!authorizate) {
            return null;
        }
        try {
            ResultSet rs = DbController.executeQuery(conn, EmailController.listEmails());
            List<Email> emails = new java.util.ArrayList<Email>();
            while (rs.next()) {
                Email email = new Email(rs.getInt("idemail"), rs.getString("email"));
                int userid;
                int organizer;
                try {
                    userid = rs.getInt("user");
                } catch (Exception e) {
                    userid = 0;
                }
                try {
                    organizer = rs.getInt("organizer");
                } catch (Exception e) {
                    organizer = 0;
                }
                email.setUser(userid);
                email.setOrganizer(organizer);
                emails.add(email);
            }
            return emails;
        } catch (Exception e) {
            return null;
        }
    }

    public static Email findEmail(Connection conn, String email, User user, Token tokenModel){
        boolean authorizate = TokenController.authorizate(user.getToken(), tokenModel, user.getId());
        if (!authorizate) {
            return null;
        }
        try {
            ResultSet rs = DbController.executeQuery(conn, EmailController.findEmail(email));
            if (rs.next()) {
                Email emailAux = new Email(rs.getInt("idemail"), rs.getString("email"));
                int iduser;
                int organizer;
                try {
                    iduser = rs.getInt("user");
                } catch (Exception e) {
                    iduser = 0;
                }
                try {
                    organizer = rs.getInt("organizer");
                } catch (Exception e) {
                    organizer = 0;
                }
                emailAux.setUser(iduser);
                emailAux.setOrganizer(organizer);
                return emailAux;
            }else{
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean updateEmail(Connection conn, Email email, User user, Token tokenModel){
        boolean authorizate = TokenController.authorizate(user.getToken(), tokenModel, user.getId());
        if (!authorizate) {
            return false;
        }
        if (email.getUser() > 1) {
            if (email.getOrganizer() > 0) {
                try {
                    return DbController.executeStatment(conn, EmailController.updateAllInformationEmail(), EmailController.updateAllInformationEmailList(email));
                } catch (Exception e) {
                    return false;
                }
            }else{
                try {
                    return DbController.executeStatment(conn, EmailController.updateUser(), EmailController.updateUserList(email));
                } catch (Exception e) {
                    return false;
                }
            }
        }else{
            if (email.getOrganizer() > 0) {
                try {
                    return DbController.executeStatment(conn, EmailController.updateOrganizer(), EmailController.updateOrganizerList(email));
                } catch (Exception e) {
                    return false;
                }
            }else{
                return false;
            }
        }
    }

    public static boolean deleteEmail(Connection conn, String email, User user, Token tokenModel){
        boolean authorizate = TokenController.authorizate(user.getToken(), tokenModel, user.getId());

        if (!authorizate) {
            return false;
        }
        try {
            return DbController.executeStatment(conn, EmailController.deleteEmail(), java.util.Collections.singletonList(email));
        } catch (Exception e) {
            return false;
        }
    }

}
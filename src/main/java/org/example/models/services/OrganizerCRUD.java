package org.example.models.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

import org.example.controllers.DbController;
import org.example.controllers.EmailController;
import org.example.controllers.OrganizerController;
import org.example.controllers.TokenController;
import org.example.models.objects.Email;
import org.example.models.objects.Organizer;
import org.example.models.objects.Token;
import org.example.models.objects.User;

public class OrganizerCRUD {
    
    private static boolean avoidNullBlankValues(Organizer organizer){
        if (organizer == null || organizer.getOrganizer() == null || organizer.getUser() <= 1 || organizer.getEmailObject().getId() <= 1) {
            return true;
        }else{
            return false;
        }            
    }

    private static String avoidingDuplicates(Connection conn, Organizer organizer){
        if (avoidNullBlankValues(organizer)) {
            return "Error while creating organizer.";
        }
        try {
            ResultSet rs = DbController.executeQuery(conn, OrganizerController.findOrganizerByName(organizer.getOrganizer()));
            if (rs.next()) {
                return "Organizer already exists.";
            }else{
                return "Organizer not found.";
            }
        } catch (Exception e) {
            return "Error while creating organizer.";
        }
    }

    public static int createOrganizer(Connection conn, Organizer organizer, User user, Token tokenModel){
        boolean authorizate = TokenController.authorizate(user.getToken(), tokenModel, organizer.getUser());
        if (!authorizate) {
            return 403;
        }
        String description = avoidingDuplicates(conn, organizer);
        if (description == "Organizer already exists." || description == "Error while creating organizer.") {
            return 401;
        }
        try {
            ResultSet rs = DbController.executeQuery(conn, EmailController.findEmail(organizer.getEmailObject().getId()));
            if (rs.next()) {
                Email email = new Email(rs.getInt("idemail"), rs.getString("email"));
                email.setOrganizer(rs.getInt("organizer"));
                rs.close();
                if (email.getOrganizer() > 0) {
                    System.out.println("Email already in use.");
                    return 401;
                }else{
                    return DbController.executeStatment(conn, OrganizerController.createOrganizer(), OrganizerController.createOrganizerList(organizer)) ? 200 : 402;
                }
            }
            return 401;
        } catch (Exception e) {
            System.out.println("Error while creating organizer." + e.getMessage());
            return 402;
        }
    }

    public static List<?> listOrganizers(Connection conn){
        try {
            ResultSet rs = DbController.executeQuery(conn, OrganizerController.listOrganizers());
            List<Organizer> organizers = new java.util.ArrayList<Organizer>();
            while (rs.next()) {
                Organizer organizer = new Organizer();
                organizer.setId(rs.getInt("idorganizer"));
                organizer.setOrganizer(rs.getString("organizer"));
                organizer.setUser(rs.getInt("iduser"));
                organizer.setEmailId(rs.getInt("idemail"));
                organizers.add(organizer);
            }
            return organizers;
        } catch (Exception e) {
            return null;
        }
    }
    
    public static Organizer findOrganizer(Connection conn, int id){
        if (id <= 0) {
            return null;
        }
        try {
            ResultSet rs = DbController.executeQuery(conn, OrganizerController.findOrganizer(id));
            if (rs.next()) {
                Organizer organizer = new Organizer();
                organizer.setId(rs.getInt("idorganizer"));
                organizer.setOrganizer(rs.getString("organizer"));
                organizer.setUser(rs.getInt("user"));
                organizer.setEmailId(rs.getInt("email"));
                return organizer;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public static int updateOrganizer(Connection conn, Organizer organizer, User user, Token tokenModel){
        String description = avoidingDuplicates(conn, organizer);
        if (description == "Organizer not found." || description == "Error while creating organizer.") {
            return 401;
        }
        boolean authorizate = TokenController.authorizate(user.getToken(), tokenModel, organizer.getUser());
        if (!authorizate) {
            return 403;
        }
        try {
            ResultSet rs = DbController.executeQuery(conn, EmailController.findEmail(organizer.getEmailObject().getId()));
            if (rs.next()) {
                Email email = new Email(rs.getInt("idemail"), rs.getString("email"));
                email.setOrganizer(rs.getInt("organizer"));
                rs.close();
                if (email.getOrganizer() > 0) {
                    System.out.println("Email already in use.");
                    return 401;
                }else{
                    return DbController.executeStatment(conn, OrganizerController.updateOrganizer(), OrganizerController.updateOrganizerList(organizer)) ? 200 : 402;
                }
            }
            return 401;
        } catch (Exception e) {
            System.out.println("Error while creating organizer." + e.getMessage());
            return 402;
        }
    }

    public static boolean deleteOrganizer(Connection conn, Organizer organizer, User user, Token tokenModel){
        boolean authorizate = TokenController.authorizate(user.getToken(), tokenModel, organizer.getUser());
        if (!authorizate) {
            System.out.println("Unauthorized.");
            return false;
        }
        if (organizer == null || organizer.getId() <= 0) {
            return false;
        }
        try {
            return DbController.executeStatment(conn, OrganizerController.deleteOrganizer(), OrganizerController.deleteOrganizerList(organizer));
        } catch (Exception e) {
            System.out.println("Error while deleting organizer: " + e.getMessage());
            return false;
        }
    }

}
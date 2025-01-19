package org.example.models.services;

import java.sql.Connection;
import java.sql.ResultSet;

import org.example.controllers.DbController;
import org.example.controllers.OrganizerController;
import org.example.models.objects.Organizer;

public class OrganizerCRUD {
    
    private static boolean avoidNullBlankValues(Organizer organizer){
        if (organizer == null || organizer.getOrganizer() == null || organizer.getUser() <= 1 || organizer.getEmail() == null) {
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

    public static int createOrganizer(Connection conn, Organizer organizer){
        String description = avoidingDuplicates(conn, organizer);
        if (avoidNullBlankValues(organizer)) {
            return 400;
        }
        try {
            String result = avoidingDuplicates(conn, organizer);
            if (result == "Organizer not found.") {
                return DbController.executeStatment(conn, OrganizerController.createOrganizer(), OrganizerController.createOrganizerList(organizer)) ? 200 : 400;
            }else{
                return 400;
            }
        } catch (Exception e) {
            return 400;
        }
    }

}
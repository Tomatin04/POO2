package org.example.models.services;

import java.sql.Connection;
import java.sql.ResultSet;

import org.example.models.objects.Event;
import org.example.models.objects.Token;
import org.example.models.objects.User;
import org.example.controllers.DbController;
import org.example.controllers.EventController;
import org.example.controllers.OrganizerController;
import org.example.controllers.TokenController;

public class EventCRUD {
 
    private static String avoidNullBlankValues(Event event){
        if (event == null || event.getTitle() == null || event.getDescription() == null || event.getCategory() == null || event.getDate() == null || event.getTime() == null || event.getDuration() < 0 || event.getLocation() == null || event.getAdress() == null || event.getPrice() < 0 || event.getOrganizer() < 1 || event.getMaxCapacity() < 1 || event.getStatus() == null) {
            return "Error while creating event.";
        }else{
            return "Event created.";
        }            
    }

    private static String avoidingDuplicates(Connection conn, Event event){
        if (avoidNullBlankValues(event) == "Error while creating event.") {
            return "Error while creating event.";
        }
        try {
            ResultSet rs = DbController.executeQuery(conn, EventController.findEvent(event.getId()));
            if (rs.next()) {
                return "Event already exists.";
            }else{
                return "Event not found.";
            }
        } catch (Exception e) {
            return "Error while creating event.";
        }
    }

    public static int createEvent(Connection conn, Event event, User user, Token tokenModel){
        try{
            ResultSet rs = DbController.executeQuery(conn, OrganizerController.findOrganizer(user.getId()));
            int iduser;
            if (rs.next()) {
                iduser = rs.getInt("iduser");
            }else{
                return 402;
            }
            boolean authorizate = TokenController.authorizate(user.getToken(), tokenModel, iduser);
            if (!authorizate) {
                return 403;
            }
        }
        catch(Exception e){
            return 401;
        }
        String description = avoidingDuplicates(conn, event);
        if (description == "Event already exists." || description == "Error while creating event.") {
            return 401;
        }
        try {
            if(DbController.executeStatment(conn, EventController.createEvent(), EventController.createEventList(event))){
                return 201;
            }
            return 402;
        } catch (Exception e) {
            return 401;
        }
    }

    public static int deleteEvent(Connection conn, Event event, User user, Token tokenModel){
        try{
            ResultSet rs = DbController.executeQuery(conn, OrganizerController.findOrganizer(user.getId()));
            int iduser;
            if (rs.next()) {
                iduser = rs.getInt("iduser");
            }else{
                return 402;
            }
            boolean authorizate = TokenController.authorizate(user.getToken(), tokenModel, iduser);
            if (!authorizate) {
                return 403;
            }
        }catch(Exception e){
            return 401;
        }
        try {
            if(DbController.executeStatment(conn, EventController.deleteEvent(), EventController.deleteEventList(event))){
                return 201;
            }
            return 402;
        } catch (Exception e) {
            return 401;
        }
    }

    public static ResultSet listEvents(Connection conn, User user, Token tokenModel){
        boolean authorizate = TokenController.authorizate(user.getToken(), tokenModel, user.getId());
        if (!authorizate) {
            return null;
        }
        try {
            ResultSet rs = DbController.executeQuery(conn, EventController.listEvents());
            return rs;
        } catch (Exception e) {
            return null;
        }
    }

    public static ResultSet findEvent(Connection conn, int id){
        try {
            ResultSet rs = DbController.executeQuery(conn, EventController.findEvent(id));
            return rs;
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean updateEvent(Connection conn, Event event, User user, Token tokenModel){
        try{
            ResultSet rs = DbController.executeQuery(conn, OrganizerController.findOrganizer(user.getId()));
            int iduser;
            if (rs.next()) {
                iduser = rs.getInt("iduser");
            }else{
                return false;
            }
            boolean authorizate = TokenController.authorizate(user.getToken(), tokenModel, iduser);
            if (!authorizate) {
                return false;
            }
        }catch(Exception e){
            return false;
        }
        try {
            return DbController.executeStatment(conn, EventController.updateEvent(), EventController.updateEventList(event));
        } catch (Exception e) {
            return false;
        }
    }

}
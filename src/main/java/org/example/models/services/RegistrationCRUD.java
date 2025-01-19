package org.example.models.services;

import java.sql.Connection;
import java.sql.ResultSet;

import org.example.controllers.TokenController;
import org.example.models.objects.Registration;
import org.example.models.objects.Token;
import org.example.models.objects.User;
import org.example.controllers.DbController;
import org.example.controllers.RegistrationController;

public class RegistrationCRUD {
    
    private static boolean avoidNullBlankValues(Registration registration){
        if (registration == null || registration.getEvent() <= 1 || registration.getUser() <= 1 || registration.getStatus() == null) {
            return true;
        }else{
            return false;
        }            
    }

    private static String avoidingDuplicates(Connection conn, Registration registration){
        if (avoidNullBlankValues(registration)) {
            return "Error while creating registration.";
        }
        try {
            ResultSet rs = DbController.executeQuery(conn, RegistrationController.findRegistration());
            if (rs.next()) {
                return "Registration already exists.";
            }else{
                return "Registration not found.";
            }
        } catch (Exception e) {
            return "Error while creating registration.";
        }
    }

    public static int createRegistration(Connection conn, Registration registration, User user, Token tokenModel){
        boolean authorizate = TokenController.authorizate(user.getToken(), tokenModel, registration.getUser());
        if (!authorizate) {
            return 403;
        }
        String description = avoidingDuplicates(conn, registration);
        if (description == "Registration already exists." || description == "Error while creating registration.") {
            return 401;
        }
        if (registationsAvaiable(conn, registration.getEvent()) <= 0) {
            return 401;
        }
        try {
            return DbController.executeStatment(conn, RegistrationController.createRegistration(), RegistrationController.createRegistrationList(registration)) ? 200 : 400;
        } catch (Exception e) {
            return 401;
        }
    }

    public static int deleteRegistration(Connection conn, Registration registration, User user, Token tokenModel){
        boolean authorizate = TokenController.authorizate(user.getToken(), tokenModel, registration.getUser());
        if (!authorizate) {
            return 403;
        }
        try {
            return DbController.executeStatment(conn, RegistrationController.deleteRegistration(), RegistrationController.deleteRegistrationList(registration)) ? 200 : 400;
        } catch (Exception e) {
            return 401;
        }
    }

    public static int updateRegistration(Connection conn, Registration registration, User user, Token tokenModel){
        boolean authorizate = TokenController.authorizate(user.getToken(), tokenModel, registration.getUser());
        if (!authorizate) {
            return 403;
        }
        if (registationsAvaiable(conn, registration.getEvent()) <= 0) {
            return 401;
        }
        try {
            return DbController.executeStatment(conn, RegistrationController.updateRegistration(), RegistrationController.updateRegistrationList(registration)) ? 200 : 400;
        } catch (Exception e) {
            return 401;
        }
    }   

    public static int findRegistration(Connection conn, Registration registration, User user, Token tokenModel){
        boolean authorizate = TokenController.authorizate(user.getToken(), tokenModel, registration.getUser());
        if (!authorizate) {
            return 403;
        }
        try {
            ResultSet rs = DbController.executeQuery(conn, RegistrationController.findRegistration());
            if (rs.next()) {
                return 200;
            }else{
                return 400;
            }
        } catch (Exception e) {
            return 401;
        }
    }

    public static int listRegistrations(Connection conn, User user, Token tokenModel){
        boolean authorizate = TokenController.authorizate(user.getToken(), tokenModel, user.getId());
        if (!authorizate) {
            return 403;
        }
        try {
            ResultSet rs = DbController.executeQuery(conn, RegistrationController.listRegistrations());
            if (rs.next()) {
                return 200;
            }else{
                return 400;
            }
        } catch (Exception e) {
            return 401;
        }
    }

    public static int registationsAvaiable(Connection conn, int event){
        try {
            ResultSet rs = DbController.executeQuery(conn, "SELECT COUNT(*) FROM registrations WHERE event = " + event + " AND status != 'CANCELED';");
            if (rs.next()) {
                return rs.getInt("count");
            }
            return -1;
        } catch (Exception e) {
            return -1;
        }
    }

}

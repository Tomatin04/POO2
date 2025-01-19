package org.example.controllers;

import java.util.ArrayList;
import java.util.List;

import org.example.models.objects.Organizer;

public class OrganizerController {
 
    public static String createOrganizer() {
        String sqlQuery;
        sqlQuery = "INSERT INTO organizers (organizer, user, email) VALUES (?, ?, ?);";
        return sqlQuery;
    }

    public static List<?> createOrganizerList(Organizer organizer) {
        List<Object> list = new ArrayList<>();
        list.add(organizer.getOrganizer());
        list.add(organizer.getUser());
        list.add(organizer.getEmail());
        return list;
    }

    public static String updateOrganizer() {
        String sqlQuery;
        sqlQuery = "UPDATE organizers SET name = ?, user = ?, email = ? WHERE idorganizer = ?;";
        return sqlQuery;
    }

    public static List<?> updateOrganizerList(Organizer organizer) {
        List<Object> list = new ArrayList<>();
        list.add(organizer.getOrganizer());
        list.add(organizer.getUser());
        list.add(organizer.getEmail());
        list.add(organizer.getId());
        return list;
    }

    public static String deleteOrganizer() {
        String sqlQuery;
        sqlQuery = "DELETE organizers.idorganizer FROM organizers LEFT JOIN events ON " +
        "organizers.idorganizer = events.organizer WHERE organizers.idorganizer = ? AND(" +
        "events.organizer IS NULL OR events.status IN ('CLOSED', 'DESACTIVATED');";
        return sqlQuery;
    }

    public static List<?> deleteOrganizerList(Organizer organizer) {
        List<Object> list = new ArrayList<>();
        list.add(organizer.getId());
        return list;
    }

    public static String listOrganizers() {
        String sqlQuery;
        sqlQuery = "SELECT * FROM organizers;";
        return sqlQuery;
    }

    public static String listAllOrganizersInformations(){
        String sqlQuery;
        sqlQuery = "SELECT organizers.idorganizer, organizers.name, organizers.user AS iduser, " +
        "users.fullname, organizers.email AS idemail, emails.email, events.idevent, events.title " +
        "FROM organizers JOIN users ON organizers.user = users.iduser JOIN emails ON " +
        "organizers.email = emails.idemail JOIN events ON events.organizer = organizers.idorganizer;";
        return sqlQuery;
    }

    public static String findOrganizer(int id) {
        String sqlQuery;
        sqlQuery = "SELECT * FROM organizers WHERE idorganizer = " + id + ";";
        return sqlQuery;
    }

    public static String findOrganizerByName(String name) {
        String sqlQuery;
        sqlQuery = "SELECT * FROM organizers WHERE name = " + name + ";";
        return sqlQuery;
    }

    public static String findOrganizerByEmail(){
        String sqlQuery;
        sqlQuery = "SELECT * FROM organizers WHERE email = ?";
        return sqlQuery;
    }

    public static String findAllOrganizerInformations(){
        String sqlQuery;
        sqlQuery = "SELECT organizers.idorganizer, organizers.name, organizers.user AS iduser, " +
            "users.fullname, organizers.email AS idemail, emails.email, events.idevent, events.title " +
            "FROM organizers JOIN users ON organizers.user = users.iduser JOIN emails ON " +
            "organizers.email = emails.idemail JOIN events ON events.organizer = organizers.idorganizer " +
            "WHERE organizers.idorganizer = ?;";
        return sqlQuery;
    }
}
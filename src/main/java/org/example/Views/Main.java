package org.example.views;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.example.Contorlers.DbController;
import org.example.Contorlers.EmailController;
import org.example.Contorlers.UserController;
import org.example.Model.services.DbOperations;
import org.example.Model.services.UserCrud;
import org.example.Model.objects.User;

public class Main {
    public static void main(String[] args){

        DbOperations dbop = new DbOperations();
        dbop.initDbConnection();

        System.out.printf("Hello and welcome!");
        User user = new User();
        user.setFullName("John Joe");
        user.setEmail("jhonjoe@gmail.com");
        user.setPassword("123456");
        user.setBirthDate("2000-01-01");
        user.setCpf("12345678900");

        if (dbop.dbStatus.equals("connected")) {
            UserCrud.createUser(user, dbop.getDbConnection());
            System.out.println(user.getEmail());
            try {
                ResultSet rs = DbController.executeQuery(conn, EmailController.findEmail(user.getEmail()));
                if (rs.next()) {
                    user.setEmailId(rs.getInt("idemail"));
                }
                System.out.println(user.getEmailObject().getId());
                rs.close();
                if (dbop = DbController.executeStatment(conn, UserController.createUser(user), UserController.createUserList(user))) {
                    System.out.println("User created successfully.");
                } else {
                    System.out.println("Error while creating user.");
                }
            } catch (IOException e) {
                System.out.println("Error while creating user: " + e.getMessage());
            } catch (SQLException e) {
                System.out.println("SQL Error while creating user: " + e.getMessage());
            }
        }
    }
}
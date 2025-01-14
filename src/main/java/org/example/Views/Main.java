package org.example.views;

import org.example.Model.services.DbOperations;
import org.example.Model.services.UserCrud;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.example.Contorlers.DbController;
import org.example.Model.objects.User;

public class Main {
    public static void main(String[] args) throws IOException, java.io.IOException, SQLException {

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
            int status = UserCrud.createUser(user, dbop.getDbConnection());
            if (status == 200) {
                System.out.println("User created successfully.");
                try {
                    Connection conn = dbop.getDbConnection();
                    String sqlQuery = "SELECT users.iduser, users.fullname, emails.idemail, emails.email, users.cpf, users.birthdate, users.role FROM users JOIN emails ON users.email = emails.idemail;";
                    ResultSet rs = DbController.executeQuery(conn, sqlQuery);
                    if (rs.next()) {
                        user.setId(rs.getInt("iduser"));
                        user.setRole(rs.getString("role"));
                        user.setEmailId(rs.getInt("idemail"));
                    }
                    String token = org.example.Model.services.token.generateToken(user, true);
                    System.out.println("Token: " + token);                    
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
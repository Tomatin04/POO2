package org.example.Contorlers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.example.Model.objects.Email;
import org.example.Model.objects.User;

public class UserController {
 
    public static String listUsers() throws IOException {
        String sqlQuery;
        sqlQuery = "SELECT users.iduser, users.fullname, emails.idemail, emails.email, users.cpf, users.birthdate, users.role FROM users JOIN emails ON users.email = emails.idemail;";
        return sqlQuery;
    }

    public static String findUserById(String id) throws IOException {
        String sqlQuery;
        sqlQuery = listUsers() + " WHERE users.iduser = " + id + ";";
        return sqlQuery;
    }

    public static String findUserByCPF(String cpf) throws IOException {
        String sqlQuery = listUsers().substring(0, listUsers().length() - 1);
        sqlQuery += " WHERE users.cpf = '" + cpf + "';";
        return sqlQuery;
    }

    public static String findUserByEmail(String email) throws IOException {
        String sqlQuery = listUsers().substring(0, listUsers().length() - 1);
        sqlQuery += " WHERE emails.email = '" + email + "';";
        return sqlQuery;
    }

    public static String createUser(User user, Email email, Connection conn) throws IOException, SQLException {
        boolean success = DbController.executeStatment(conn, EmailController.createEmail(), EmailController.createEmailList(email.getEmail()));
        if (success) {
            ResultSet rs = DbController.executeQuery(conn, EmailController.findEmail(email.getEmail()));
            rs.next();
            user.setEmail(rs.getString("idemail"));            
            rs.close();
            success = DbController.executeStatment(conn, "INSERT INTO users (fullname, email, password, role, birthdate, cpf) VALUES (?, ?, ?, ?, ?, ?);", createUserList(user));
            if (success) {
                return "Usuário criado com sucesso";
            } else {
                return "Erro na criação do usuário";
            }
        } else {
            return "Erro na criação do email";
        }

    }

    public static String createUser(User user) throws IOException {
        String sqlQuery;
        sqlQuery = "INSERT INTO users (fullname, email, password, birthdate, cpf) VALUES (?, ?, ?, ?, ?);";
        return sqlQuery;
    }

    public static List<?> createUserList(User user) throws IOException {
        System.out.println();
        return Arrays.asList(user.getFullName(), user.getEmailObject().getId(), user.getPassword(), user.getBirthDate(), user.getCpf());
    }
    
    public static String updateUser(User user) throws IOException {
        if (user == null || user.getId() <= 0) {
            return null;
        }
        List<String> options = listValidItens(user);
        if (options.isEmpty()) {
            System.out.println("No valid options found.");
            return null;
        }
        String sqlQuery = "UPDATE users SET ";
        for (String op : options) {
            switch (op) {
                case "name":
                    sqlQuery += "name = ?";
                    break;
                case "email":
                    sqlQuery += "email = ?";
                    break;
                case "password":
                    sqlQuery += "password = ?";
                    break;
                case "cpf":
                    sqlQuery += "cpf = ?";
                    break;
                case "birthDate":
                    sqlQuery += "birthdate = ?";
                    break;
            }
            if (options.indexOf(op) < options.size() - 1) {
                sqlQuery += ", ";
            }
        }
        sqlQuery += " WHERE idUser = ?;";
        if (sqlQuery == "UPDATE users SET WHERE idUser = ?;") {
            return null;
        }
        return sqlQuery;
    }
    
    public static List<?> updateUserList(User user) throws IOException {
        List<String> options = listValidItens(user);
        List<Object> list = new ArrayList<>();
        for (String op : options) {
            switch (op) {
                case "name":
                    list.add(user.getFullName());
                    break;
                case "email":
                    list.add(user.getEmailObject().getId());
                    break;
                case "password":
                    list.add(user.getPassword());
                    break;
                case "cpf":
                    list.add(user.getCpf());
                    break;
                case "birthDate":
                    list.add(user.getBirthDate());
                    break;
            }
        }
        list.add(user.getId());
        return list;
    }

    private static List<String> listValidItens(User user) {
        List<String> options = new ArrayList<>();
        if (user.getId() > 0) {
            options.add("id");
        }
        if (user.getFullName().length() > 0) {
            options.add("name");
        }
        if (user.getEmailObject().getId() > 0) {
            options.add("email");
        }
        if (user.getPassword().length() > 0) {
            options.add("password");
        }
        if (user.getCpf().length() > 0) {
            options.add("cpf");
        }
        if (user.getBirthDate().length() > 0) {
            options.add("birthDate");
        }
        return options;
    }

    public static String deleteUser(int id) throws IOException {
        String sqlQuery;
        sqlQuery = "DELETE FROM users WHERE id = ?";
        return sqlQuery;
    }

    public static List<?> deleteUserList(int id) throws IOException {
        return Arrays.asList(id);
    }

    
}

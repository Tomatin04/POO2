package org.example.Contorlers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.example.Model.objects.Email;
import org.example.Model.objects.User;

public class UserController {
 
    public static String listUsers() throws IOException {
        String sqlQuery;
        sqlQuery = "SELECT users.fullname, emails.email, users.cpf, users.birthdate, users.role FROM users JOIN emails ON users.email = emails.idemail;";
        return sqlQuery;
    }

    public static String findUser(String cpf) throws IOException {
        String sqlQuery = listUsers().substring(0, listUsers().length() - 1);
        sqlQuery += " WHERE users.cpf = '" + cpf + "';";
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
        String sqlQuery;
        sqlQuery = "UPDATE users SET name = ?, email = ?, WHERE idUser  = ?;";
        return sqlQuery;
    }

    public static String updateUserSensibleInformation(User user, String option) throws IOException {
        String sqlQuery = "UPDATE users SET ";
        String[] options;
        if (option.contains(" ")) {
            options = option.split(" ");
        }else{
            options = new String[]{option};
        }
        for (String opt : options) {
            switch (opt) {
                case "password":
                    sqlQuery += "password = ?, ";
                    break;
                case "cpf":
                    sqlQuery += "cpf = ?, ";
                    break;
                case "birthDate":
                    sqlQuery += "birthdate = ?, ";
                    break;
                default:
                    break;
            }
        }
        return sqlQuery += " WHERE idUser  = ?";
    }

    public static List<?> updateUserList(User user) throws IOException {
        return Arrays.asList(user.getFullName(), user.getEmail());
    }

    public List<?> updateUserSensibleInformationList(User user, String option) throws IOException {
        String[] options;
        if (option.contains(" ")) {
            options = option.split(" ");
        }else{
            options = new String[]{option};
        }
        for (String opt : options) {
            switch (opt) {
                case "password":
                    return Arrays.asList(user.getPassword(), user.getId());
                case "cpf":
                    return Arrays.asList(user.getCpf(), user.getId());
                case "birthDate":
                    return Arrays.asList(user.getBirthDate(), user.getId());
                default:
                    break;
            }
        }
        return null;
    }

    public static String deleteUser(String id) throws IOException {
        String sqlQuery;
        sqlQuery = "DELETE FROM users WHERE id = ?";
        return sqlQuery;
    }

    public List<?> deleteUserList(String id) throws IOException {
        return Arrays.asList(id);
    }

    
}

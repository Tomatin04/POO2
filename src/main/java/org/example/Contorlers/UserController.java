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
 
    public String listUsers() throws IOException {
        String sqlQuery;
        sqlQuery = "SELECT users.fullname, emails.email, users.cpf, users.birthdate, users.role FROM users JOIN emails ON users.email = emails.idemail;";
        return sqlQuery;
    }

    public String getUser(String cpf) throws IOException {
        String sqlQuery = listUsers().substring(0, listUsers().length() - 1);
        sqlQuery += " WHERE users.cpf = '" + cpf + "';";
        return sqlQuery;
    }

    public String createUser(User user, Email email, Connection conn) throws IOException, SQLException {
        EmailController emailController = new EmailController();
        boolean success = DbController.executeStatment(conn, emailController.createEmail(email.getEmail()), Arrays.asList(email.getEmail()));
        if (success) {
            ResultSet rs = DbController.executeQuery(conn, emailController.findEmail(email.getEmail()));
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

    public String createUser(User user) throws IOException {
        String sqlQuery;
        sqlQuery = "INSERT INTO users (fullname, email, password, birthdate, cpf) VALUES (?, ?, ?, ?, ?);";
        return sqlQuery;
    }

    public List<?> createUserList(User user) throws IOException {
        return Arrays.asList(user.getFullName(), user.getEmail(), user.getPassword(), user.getBirthDate(), user.getCpf());
    }
    
    public String updateUser(User user) throws IOException {
        String sqlQuery;
        sqlQuery = "UPDATE users SET name = ?, email = ?, WHERE idUser  = ?";
        return sqlQuery;
    }

    public String updateUserSensibleInformation(User user, String option) throws IOException {
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

    public List<?> updateUserList(User user) throws IOException {
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

    public String deleteUser(String id) throws IOException {
        String sqlQuery;
        sqlQuery = "DELETE FROM users WHERE id = ?";
        return sqlQuery;
    }

    public List<?> deleteUserList(String id) throws IOException {
        return Arrays.asList(id);
    }

    
}

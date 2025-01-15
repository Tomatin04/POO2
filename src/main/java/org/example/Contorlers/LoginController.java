package org.example.Contorlers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.example.Model.objects.Token;
import org.example.Model.objects.User;

public class LoginController {
 
    public static String login(String email, String password, Connection conn, Token token) throws SQLException{
        if (email == null || email.isBlank() || password == null || password.isBlank()) {
            return "";
        }
        try {
            ResultSet rs = DbController.executeQuery(conn, "SELECT users.iduser, users.fullname, emails.email, users.role FROM users JOIN emails ON users.email = emails.idemail where emails.email = '" + email + "' AND users.password = '" + password + "';");
            if (rs.next()) {
                User userAux = new User();
                userAux.setId(rs.getInt("iduser"));
                userAux.setFullName(rs.getString("fullname"));
                userAux.setEmail(rs.getString("email"));
                userAux.setRole(rs.getString("role"));
                return TokenController.generateToken(userAux, true, token);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean logout(String token, Token tokenModel) {
        if (tokenModel.isBlackListed(token)) {
            return false;
        }
        tokenModel.addToBlackList(token);
        return true;
    }

}
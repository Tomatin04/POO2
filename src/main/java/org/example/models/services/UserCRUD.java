package org.example.models.services;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.example.controllers.DbController;
import org.example.controllers.EmailController;
import org.example.controllers.TokenController;
import org.example.controllers.UserController;
import org.example.models.objects.Token;
import org.example.models.objects.User;

public class UserCRUD {
 
    private static boolean avoidNullBlankValues(User user) {
        if (user.getFullName().isBlank() || user.getEmail().isBlank() || user.getPassword().isBlank() || user.getBirthDate().isBlank() || user.getCpf().isBlank() || 
         user.getFullName() == null || user.getEmail() == null || user.getPassword() == null || user.getBirthDate() == null || user.getCpf() == null || user.getEmailObject().getEmail() == null) {
            return true;
        } else {
            return false;
        }
    }

    private static String avoidingDuplicates(User user, Connection conn) {
        if (avoidNullBlankValues(user)) {
            return "Null or blank values found.";
        }
        try {
            ResultSet rs = DbController.executeQuery(conn, EmailController.findEmail(user.getEmail()));
            if (rs.next()) {
                return "'Email already in use.'";
            } else {
                rs.close();
                rs = DbController.executeQuery(conn, UserController.findUserByCPF(user.getCpf()));
                if (rs.next()) {
                    return "CPF already in use.";
                } else {
                    rs.close();
                    return "No duplicates found.";
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while checking for duplicates: " + e.getMessage());
            return "SQLException";
        } catch (IOException e) {
            System.out.println("Error while checking for duplicates: " + e.getMessage());
            return "IOException";
        }
    }

    public static int createUser(User user, Connection conn) {
        String description = avoidingDuplicates(user, conn);
        if ( description != "No duplicates found.") {
            switch (description) {
                case "Null or blank values found.":
                    System.out.println("Null or blank values found.");
                    return 401;
                case "'Email already in use.'":
                    System.out.println("Email already in use.");
                    return 405;
                case "CPF already in use.":
                    System.out.println("CPF already in use.");
                    return 405;
                case "SQLException":
                    System.out.println("Error while checking for duplicates: SQLException.");
                    return 403;
                case "IOException":
                    System.out.println("Error while checking for duplicates: IOException.");
                    return 403;
            }
        }
        description = "Checked for duplicates or NULL/BLANK values.\nStarting user creation.";
        try {
            @SuppressWarnings("unused")
            boolean status = false;
            if (status = DbController.executeStatment(conn, EmailController.createEmail(), EmailController.createEmailList(user.getEmail()))) {
                System.out.println("Email created successfully.");
                description += "\nEmail created successfully.";
                ResultSet rs = DbController.executeQuery(conn, EmailController.findEmail(user.getEmail()));
                if (rs.next()) {
                    user.setEmailId(rs.getInt("idemail"));
                    description += "\nidEmail atribuited.";
                    rs.close();
                    try {
                        if (status = DbController.executeStatment(conn, UserController.createUser(user), UserController.createUserList(user))) {
                            return 200;
                        } else {
                            description += "\nError while creating user.";
                            System.out.println(user.getFullName() + "\n" + description);
                            return 401;
                        } 
                    }catch (Exception e) {
                        description += "\nError while creating user.";
                        System.out.println(user.getFullName() + "\n" + description + "\n" + e.getMessage());
                        return 401;
                    }
                }else {
                    description += "\nEmail not found.";
                    System.out.println(user.getFullName() + "\n" + description);
                    return 404;
                }
            } else {
                description += "\nError while creating email.";
                System.out.println(user.getFullName() + "\n" + description);
                return 402;
            }
        } catch (SQLException sqle) {
            description += "\nError while creating email.";
            System.out.println(user.getFullName() + "\n" + description + "\n" + sqle.getMessage());
            return 402;
        } catch (IOException e) {
            description += "\nError while creating email.";
            System.out.println(user.getFullName() + "\n" + description + "\n" + e.getMessage());
            return 402;
        }
    }

    public static int updateUser(User user, Connection conn, String token, Token tokenModel) {
        if (user == null || conn == null || token == null || tokenModel == null) {
            System.out.println("Null values found on updateUser parametrers.");
            return 401;
        }
        User userToken = TokenController.getUserDataFromToken(token, tokenModel);
        if (userToken == null || userToken.getId() <= 1) {
            System.out.println("Token invalid or null values found on updateUser parametrers.");
            return 401;
        }
        if (user.getId() <= 1 && (!userToken.getRole().equals("admin") || user.getId() != userToken.getId()) ) {
            System.out.println("Unauthorized user.");
            return 403;
        }
        try {
            String sqlQuery = UserController.updateUser(user);
            if (sqlQuery == null) {
                System.out.println("Error while updating user.");
                return 401;
            }
            if (DbController.executeStatment(conn, sqlQuery, UserController.updateUserList(user))) {
                return 200;
            } else {
                System.out.println("Error while updating user.");
                return 402;
            }
        } catch (Exception e) {
            System.out.println("Error while updating user: " + e.getMessage());
            return 401;
        }
    }

    public static int deleteUser(User user, Connection conn, String token, Token tokenModel) {
        if (user == null || conn == null || token == null || tokenModel == null) {
            System.out.println("Null values found on deleteUser parametrers.");
            return 401;
        }
        User userToken = TokenController.getUserDataFromToken(token, tokenModel);
        if (userToken == null || userToken.getId() <= 1 || userToken.getRole() == null || userToken.getRole().isBlank() || userToken.getRole().isEmpty()) {
            System.out.println("Token invalid or null values found on deleteUser parametrers.");
            return 401;
        }
        if (user.getId() <= 1 && (!userToken.getRole().equals("admin") || user.getId() != userToken.getId()) ) {
            System.out.println("Unauthorized user.");
            return 403;
        }
        try {
            if (DbController.executeStatment(conn, UserController.deleteUser(user.getId()), UserController.deleteUserList(user.getId()))) {
                return 200;
            } else {
                System.out.println("Error while deleting user.");
                return 402;
            }
        } catch (Exception e) {
            System.out.println("Error while deleting user: " + e.getMessage());
            return 401;
        }
    }

    public static List<User> listAllUsers(Connection conn, String token, Token tokenModel) {
        if (conn == null || token == null || tokenModel == null) {
            System.out.println("Null values found on listAllUsers parametrers.");
            return null;
        }
        User userToken = TokenController.getUserDataFromToken(token, tokenModel);
        if (userToken == null || userToken.getId() <= 1 || userToken.getRole() == null || !userToken.getRole().equals("admin")) {
            System.out.println("Token invalid or null values found on listAllUsers parametrers.");
            return null;
        }
        try {
            ResultSet rs = DbController.executeQuery(conn, UserController.listUsers());
            if (rs == null) {
                System.out.println("Error while listing users.");
                return null;
            }
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("iduser"));
                user.setFullName(rs.getString("fullname"));
                user.setEmail(rs.getString("email"));
                user.setBirthDate(rs.getString("birthdate"));
                user.setCpf(rs.getString("cpf"));
                users.add(user);
            }
            return users;
        } catch (Exception e) {
            System.out.println("Error while listing users: " + e.getMessage());
            return null;
        }
    }

    public static User listUser(Connection conn, String token, Token tokenModel) {
        if (conn == null || token == null || tokenModel == null) {
            System.out.println("Null values found on listUser parametrers.");
            return null;
        }
        User userToken = TokenController.getUserDataFromToken(token, tokenModel);
        if (userToken == null || userToken.getId() <= 1) {
            System.out.println("Token invalid or null values found on listUser parametrers.");
            return null;
        }
        try {
            ResultSet rs = DbController.executeQuery(conn, UserController.findUserById(userToken.getId()));
            if (rs == null) {
                System.out.println("Error while listing user.");
                return null;
            }
            User user = new User();
            if (rs.next()) {
                user.setId(rs.getInt("iduser"));
                user.setFullName(rs.getString("fullname"));
                user.setEmail(rs.getString("email"));
                user.setBirthDate(rs.getString("birthdate"));
                user.setCpf(rs.getString("cpf"));
                return user;
            } else {
                System.out.println("User not found.");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error while listing user: " + e.getMessage());
            return null;
        }
    }

    public static User listUser(Connection conn, String token, Token tokenModel, User user){
        if (conn == null || token == null || tokenModel == null || user == null) {
            System.out.println("Null values found on listUser parametrers.");
            return null;
        }
        User userToken = TokenController.getUserDataFromToken(token, tokenModel);
        if (userToken == null || userToken.getId() <= 1) {
            System.out.println("Token invalid or null values found on listUser parametrers.");
            return null;
        }
        if (!userToken.getRole().equals("admin")){
            System.out.println("Unauthorized" + userToken.getRole());
            return null;
        }
        if(user.getId() > 1){
            try {
                ResultSet rs = DbController.executeQuery(conn, UserController.findUserById(user.getId()));
                if (rs == null) {
                    System.out.println("User not found");
                    return null;
                }
                User userReturn = new User();
                if (rs.next()) {
                    userReturn.setId(rs.getInt("iduser"));
                    userReturn.setFullName(rs.getString("fullname"));
                    userReturn.setEmail(rs.getString("email"));
                    userReturn.setBirthDate(rs.getString("birthdate"));
                    userReturn.setCpf(rs.getString("cpf"));
                    return userReturn;
                } else {
                    System.out.println("User not found.");
                    return null;
                }
            } catch (Exception e) {
                System.out.println("Error while listing user: " + e.getMessage());
                return null;
            }
        }
        if (user.getCpf() != "") {
            try {
                ResultSet rs = DbController.executeQuery(conn, UserController.findUserByCPF(user.getCpf()));
                if (rs == null) {
                    System.out.println("User not found");
                    return null;
                }
                User userReturn = new User();
                if (rs.next()) {
                    userReturn.setId(rs.getInt("iduser"));
                    userReturn.setFullName(rs.getString("fullname"));
                    userReturn.setEmail(rs.getString("email"));
                    userReturn.setBirthDate(rs.getString("birthdate"));
                    userReturn.setCpf(rs.getString("cpf"));
                    return userReturn;
                } else {
                    System.out.println("User not found.");
                    return null;
                }
            } catch (Exception e) {
                System.out.println("Error while listing user: " + e.getMessage());
                return null;
            }
        }
        if (user.getEmail() != "") {
            try {
                ResultSet rs = DbController.executeQuery(conn, UserController.findUserByEmail(user.getEmail()));
                if (rs == null) {
                    System.out.println("User not found");
                    return null;
                }
                User userReturn = new User();
                if (rs.next()) {
                    userReturn.setId(rs.getInt("iduser"));
                    userReturn.setFullName(rs.getString("fullname"));
                    userReturn.setEmail(rs.getString("email"));
                    userReturn.setBirthDate(rs.getString("birthdate"));
                    userReturn.setCpf(rs.getString("cpf"));
                    return userReturn;
                } else {
                    System.out.println("User not found.");
                    return null;
                }
            } catch (Exception e) {
                System.out.println("Error while listing user: " + e.getMessage());
                return null;
            }
        }
        System.out.println("Without needed information to make the search");
        return null;
    }

}
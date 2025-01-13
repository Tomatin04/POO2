package org.example.Model.services;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.example.Contorlers.DbController;
import org.example.Contorlers.EmailController;
import org.example.Contorlers.UserController;
import org.example.Model.objects.User;

public class UserCrud {
 
    public static boolean avoidNullBlankValues(User user) {
        if (user.getFullName().isBlank() || user.getEmail().isBlank() || user.getPassword().isBlank() || user.getBirthDate().isBlank() || user.getCpf().isBlank() || 
         user.getFullName() == null || user.getEmail() == null || user.getPassword() == null || user.getBirthDate() == null || user.getCpf() == null || user.getEmailObject().getEmail() == null) {
            return true;
        } else {
            return false;
        }
    }

    public static String avoidingDuplicates(User user, Connection conn) {
        if (avoidNullBlankValues(user)) {
            return "Null or blank values found.";
        }
        try {
            ResultSet rs = DbController.executeQuery(conn, EmailController.findEmail(user.getEmail()));
            if (rs.next()) {
                return "'Email already in use.'";
            } else {
                rs.close();
                rs = DbController.executeQuery(conn, UserController.findUser(user.getCpf()));
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

    // public static int updateUser(User user, Connection conn) {
    //     if (avoidNullBlankValues(user)) {
    //         return 401;
    //     }
    //     try {
    //         ResultSet rs = DbController.executeQuery(conn, UserController.findUser(user.getCpf()));
    //         if (rs.next()) {
    //             user.setId(rs.getInt("iduser"));
    //             rs.close();
    //             try {
    //                 if (DbController.executeStatment(conn, UserController.updateUser(user), UserController.updateUserList(user))) {
    //                     return 200;
    //                 } else {
    //                     return 401;
    //                 }
    //             } catch (IOException e) {
    //                 return 401;
    //             }
    //         } else {
    //             return 404;
    //         }
    //     } catch (SQLException e) {
    //         return 403;
    //     } catch (IOException e) {
    //         return 403;
    //     }
    // }
}
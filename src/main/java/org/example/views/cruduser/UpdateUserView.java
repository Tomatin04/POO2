package org.example.views.cruduser;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.example.components.ClearConsole;
import org.example.controllers.TokenController;
import org.example.models.objects.Token;
import org.example.models.objects.User;
import org.example.models.services.DbOperations;
import org.example.models.services.UserCRUD;

public class UpdateUserView {
 
    public static void updateUser(Token tokenModel, DbOperations dbop, Scanner scanner, String token) {
        if (dbop == null || dbop.dbStatus == "disconnected") {
            System.out.println("Error while connecting to the database.");
            return;
        }
        if (tokenModel == null || tokenModel.getToken() == null) {
            System.out.println("Error in authorization method");
            return;
        }
        if( token == null || token.isBlank() || token.isEmpty() ) {
            System.out.println("You must be logged in to update your information.");
            return;
        }
        User user = new User();
        user = TokenController.getUserDataFromToken(token, tokenModel);
        if (user.getRole().equals("admin")) {
            ClearConsole.clearConsole();
            int option = 0;
            while (option != 1 && option != 2) {
                System.out.print("Would you like to update your own information or another user? (1 - Own, 2 - Another user)\nOption:");
                option = scanner.nextInt();
                scanner.nextLine();
                if (option == 1) {
                    updateUserManager(user.getId(), scanner, dbop, tokenModel, token);
                } else if (option == 2) {
                    option = 0;
                    ListUserView.lisAlltUsers(tokenModel, dbop, scanner, token);
                    System.out.print("Enter the ID of the user you want to update: ");
                    option = scanner.nextInt();
                    scanner.nextLine();
                    updateUserManager(option, scanner, dbop, tokenModel, token);
                    option = 2;
                } else {
                    System.out.println("Invalid option.");
                }
            }
        }else{

        }
    }

    public static void updateUserManager(int id, Scanner scanner, DbOperations dbop, Token tokenModel, String token) {
        User user = new User();
        user.setId(id);
        List<String> userFields = new ArrayList<>();
        int option = -1;
        ClearConsole.clearConsole();
        while (option != 0) {
            System.out.println("Witch field would you like to update?");
            if(!userFields.contains("full_name")) {
                System.out.println("1 - Full name");
            }
            if(!userFields.contains("email")) {
            System.out.println("2 - Email");
            }
            if(!userFields.contains("password")) {
                System.out.println("3 - Password");
            }
            if(!userFields.contains("cpf")) {
                System.out.println("4 - CPF");
            }
            if(!userFields.contains("birthdate")) {
                System.out.println("5 - Birth date");
            }
            System.out.println("0 - Exit");
            System.out.print("Option: ");
            option = scanner.nextInt();
            if (option > 0 && option < 6) {
                if (option == 1) {
                    userFields.add("full_name");
                } else if (option == 2) {
                    userFields.add("email");
                } else if (option == 3) {
                    userFields.add("password");
                } else if (option == 4) {
                    userFields.add("cpf");
                } else if (option == 5) {
                    userFields.add("birthdate");
                } else {
                    System.out.println("Invalid option.");
                }
            }
            scanner.nextLine();
        }
        if (userFields.isEmpty()) {
            System.out.println("No valid options found.");
            return;
        }
        user.setId(id);
        if (userFields.contains("full_name")) {
            System.out.print("Full name: ");
            user.setFullName(scanner.nextLine());
        }
        if (userFields.contains("email")) {
            System.out.print("Email ID: ");
            user.setEmailId(scanner.nextInt());
            scanner.nextLine();
        }
        if (userFields.contains("password")) {
            System.out.print("Password: ");
            user.setPassword(scanner.nextLine());
        }
        if (userFields.contains("cpf")) {
            System.out.print("CPF: ");
            user.setCpf(scanner.nextLine());
        }
        if (userFields.contains("birthdate")) {
            System.out.print("Birth date[YYYY-MM-DD]: ");
            user.setBirthDate(scanner.nextLine());
        }
        int status = UserCRUD.updateUser(user, dbop.getDbConnection(), token, tokenModel);
        if (status == 200) {
            System.out.println("User updated successfully.");
        } else {
            System.out.println("Error while updating user.");
        }
    }
}

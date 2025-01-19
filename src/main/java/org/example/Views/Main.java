package org.example.views;

import org.example.controllers.TokenController;
import org.example.models.objects.Token;
import org.example.models.objects.User;
import org.example.models.services.DbOperations;
import org.example.views.cruduser.DeleteUserView;
import org.example.views.cruduser.ListUserView;
import org.example.views.cruduser.RegisterUserView;
import org.example.views.cruduser.UpdateUserView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, java.io.IOException, SQLException {

        if (!TokenController.initializeKey()) {
            System.out.println("Error while generating key.");
        }

        Token token = TokenController.createTokenModel();
        DbOperations dbop = new DbOperations();
        dbop.initDbConnection();
        Scanner scanner = new Scanner(System.in);
        User user = new User();
        int option = -1;
        do {
            if (option != -1) {
                System.out.println("Hello and welcome!");
            }
            if (user.getToken() == null || user.getToken() == "") {
                System.out.println("Please login or register to continue.");
                System.out.println("1 - Login");
                System.out.println("2 - Register");
            }else{
                if (user.getToken() != null) {
                    System.out.println("1 - Logout");
                    System.out.println("2 - Update");
                    System.out.println("3 - List your information");
                    System.out.println("4 - Delete user");
                    if (user.getRole() == "admin") {
                        System.out.println("5 - List all users");
                    }else{
                    }
                    System.out.println("10 - Exit");
                }
            }
            System.out.print("Option: ");
            option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1:
                    if (user.getToken() != null && user.getToken() != "") {
                        user.logout(token);
                    }else{
                        user.setToken(LoginView.login(token, dbop, scanner));
                    }
                    break;
                case 2:
                    if (user.getToken() != null && user.getToken() != "") {
                        UpdateUserView.updateUser(token, dbop, scanner, user.getToken());
                    }else{
                        RegisterUserView.register(dbop, scanner);
                    }
                    break;
                case 3:
                    ListUserView.listUser(token, dbop, scanner, user.getToken());
                    break;
                case 4:
                    int id;
                    id = DeleteUserView.deleteUser(user, token, dbop, scanner);
                    if (user.getId() == id) {
                        user.logout(token);                        
                    }
                    break;
                case 5:
                    if (user.getRole() == "admin") {
                        ListUserView.lisAlltUsers(token, dbop, scanner, user.getToken());
                    }else{
                        System.out.println("Invalid option.");
                    }
                case 10:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option.");
                    break;
            }
        } while (option != 3);
    }
}
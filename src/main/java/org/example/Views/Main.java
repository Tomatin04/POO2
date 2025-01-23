package org.example.views;

import org.example.components.ClearConsole;
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
                System.out.println("10 - Exit");
            }else{
                if (user.getToken() != null) {
                    System.out.println("1 - Logout");
                    System.out.println("2 - Update");
                    System.out.println("3 - List user information");
                    System.out.println("4 - Delete user");
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
                        user.setToken(null);
                    }else{
                        user.setToken(LoginView.login(token, dbop, scanner));
                    }
                    option = -1;
                    break;
                case 2:
                    if (user.getToken() != null && user.getToken() != "") {
                        UpdateUserView.updateUser(token, dbop, scanner, user.getToken());
                    }else{
                        RegisterUserView.register(dbop, scanner);
                    }
                    option = -1;
                    break;
                case 3:
                    ClearConsole.clearConsole();
                    ListUserView.listUser(token, dbop, scanner, user.getToken());
                    option = -1;
                    break;
                case 4:
                    int id;
                    id = DeleteUserView.deleteUser(user, token, dbop, scanner);
                    if (user.getId() == id) {
                        user.logout(token);                        
                    }
                    option = -1;
                    break;
                case 10:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option.");
                    option = -1;
                    break;
            }
        } while (option != 10);
    }
}
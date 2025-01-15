package org.example.views;

import org.example.Model.services.DbOperations;
import org.example.Model.services.UserCrud;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import org.example.Components.ClearConsole;
import org.example.Contorlers.DbController;
import org.example.Contorlers.TokenController;
import org.example.Model.objects.Token;
import org.example.Model.objects.User;

public class Main {
    public static void main(String[] args) throws IOException, java.io.IOException, SQLException {

        if (!TokenController.initializeKey()) {
            System.out.println("Error while generating key.");
        }

        Token token = TokenController.createTokenModel();
        DbOperations dbop = new DbOperations();
        dbop.initDbConnection();
        Scanner scanner = new Scanner(System.in);
        int option = -1;
        do {
            if (option != -1) {
                System.out.println("Hello and welcome!");
            }
            System.out.println("1 - Login");
            System.out.println("2 - Register");
            System.out.println("3 - Exit");
            System.out.print("Option: ");
            option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1:
                    LoginView.login(token, dbop, scanner);
                    break;
                case 2:
                    RegisterUserView.register(dbop, scanner);
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option.");
                    break;
            }
        } while (option != 3);
    }
}
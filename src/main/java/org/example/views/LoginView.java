package org.example.views;

import java.util.Scanner;

import org.example.Components.ClearConsole;
import org.example.Model.objects.Token;
import org.example.Model.objects.User;
import org.example.Model.services.DbOperations;

public class LoginView {

    public static void login(Token token, DbOperations dbop, Scanner scanner) {
        if (dbop.dbStatus == "disconnected") {
            System.out.println("Error while connecting to the database.");
            return;
        }
        boolean login = false;
        User user = new User();
        ClearConsole.clearConsole();
        do {
            System.out.print("Email: ");
            String email = scanner.nextLine();
            user.setEmail(email);
            System.out.print("Password: ");
            user.setPassword(scanner.nextLine());
            if (user.login(dbop.getDbConnection(), token)) {
                login = true;
                ClearConsole.clearConsole();
                System.out.println("Login successful.");
            }
            else {
                ClearConsole.clearConsole();
                System.out.println("Login failed.");
                System.out.println("Do you want to try again? (y/n)");
                String option = System.console().readLine();
                if (option.equals("n")) {
                    return;
                }
            }
        } while (!login);
    }

}

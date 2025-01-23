package org.example.views.cruduser;

import java.util.Scanner;

import org.example.components.ClearConsole;
import org.example.models.objects.User;
import org.example.models.services.DbOperations;
import org.example.models.services.UserCRUD;

public class RegisterUserView {
 
    public static void register(DbOperations dbop, Scanner scanner) {
        if (dbop.dbStatus == "disconnected") {
            System.out.println("Error while connecting to the database.");
            return;
        }
        boolean register = false;
        User user = new User();
        do {
            ClearConsole.clearConsole();
            System.out.print("Full name: ");
            user.setFullName(scanner.nextLine());
            System.out.print("Email: ");
            user.setEmail(scanner.nextLine());
            System.out.print("Password: ");
            user.setPassword(scanner.nextLine());
            boolean birthdateValid = false;
            do {
                System.out.print("Birth date[YYYY-MM-DD]: ");
                user.setBirthDate(scanner.nextLine());
            } while (birthdateValid);
            System.out.print("CPF: ");
            user.setCpf(scanner.nextLine());
            ClearConsole.clearConsole();
            int status = UserCRUD.createUser(user, dbop.getDbConnection());
            if (status == 200) {
                System.out.println("User created successfully.");
                register = true;
            }
            else {
                System.out.println("User registration failed.");
                System.out.println("Do you want to try again? (y/n)");
                String option = System.console().readLine();
                if (option.equals("n")) {
                    return;
                }else{
                    if (option.equals("y")) {
                        register = false;
                    }
                }
            }
        } while (!register);
    }
}
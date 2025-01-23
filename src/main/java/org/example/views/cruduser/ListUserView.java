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

public class ListUserView {
    
    public static void lisAlltUsers(Token tokenModel, DbOperations dbop, Scanner scanner, String token) {
        List<User> users = new ArrayList<User>();
        users = UserCRUD.listAllUsers(dbop.getDbConnection(), token, tokenModel);
        if (users == null) {
            System.out.println("Error while listing users.");
            return;
        }
        ClearConsole.clearConsole();
        for (User user : users) {
            System.out.println(user.toString());
        }
    }

    public static void listUser(Token tokenModel, DbOperations dbop, Scanner scanner, String token) {
        if (tokenModel == null || dbop == null || scanner == null || token == null) {
            System.out.println("Valores nulos encontrados");
            return;
        }
        User user = TokenController.getUserDataFromToken(token, tokenModel);
        user.setToken(token);
        ClearConsole.clearConsole();
        int option = -1;
        if (user.getRole().equals("admin")) {
            System.out.println("Would you like to find information about one person(1) or list all users(2)?");
            option = scanner.nextInt();
            scanner.nextLine();
            if (option == 1) {
                ClearConsole.clearConsole();
                System.out.println("How would you like to search?\n1 - ID\n2 - CPF\n3 - Email");
                option = scanner.nextInt();
                scanner.nextLine();
                switch (option) {
                    case 1:
                        ClearConsole.clearConsole();
                        System.out.print("ID:");
                        option = scanner.nextInt();
                        scanner.nextLine();
                        ClearConsole.clearConsole();
                        User userSeachId = new User();
                        userSeachId.setId(option);
                        userSeachId = UserCRUD.listUser(dbop.getDbConnection(), token, tokenModel, userSeachId);
                        userSeachId.toString();
                        break;
                    case 2:
                        ClearConsole.clearConsole();
                        System.out.print("CPF:");
                        String optionCPF = scanner.nextLine();
                        ClearConsole.clearConsole();
                        User userSeachCPF = new User();
                        userSeachCPF.setCpf(optionCPF);
                        userSeachCPF = UserCRUD.listUser(dbop.getDbConnection(), token, tokenModel, userSeachCPF);
                        userSeachCPF.toString();
                    case 3:
                        ClearConsole.clearConsole();
                        System.out.print("Email:");
                        String optionEmail = scanner.nextLine();
                        ClearConsole.clearConsole();
                        User userSearch = new User();
                        userSearch.setEmail(optionEmail);
                        userSearch = UserCRUD.listUser(dbop.getDbConnection(), token, tokenModel, userSearch);
                        userSearch.toString();
                    default:
                        System.out.println("Opção inválida");
                        break;
                }
            }else{
                if (option == 2) {
                    List<User> users = UserCRUD.listAllUsers(dbop.getDbConnection(), token, tokenModel);
                    ClearConsole.clearConsole();
                    for (User u : users) {
                        System.out.println(u.toString());
                    }
                    return;
                }
            }
            user = UserCRUD.listUser(dbop.getDbConnection(), token, tokenModel);
            if (user == null) {
                System.out.println("Error while listing user.");
                return;
            }
            ClearConsole.clearConsole();
            System.out.println(user.toString());
            return;
        }
    }

}
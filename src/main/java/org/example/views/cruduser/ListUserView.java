package org.example.views.cruduser;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.example.components.ClearConsole;
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
        User user = new User();
        user = UserCRUD.listUser(dbop.getDbConnection(), token, tokenModel);
        if (user == null) {
            System.out.println("Error while listing user.");
            return;
        }
        ClearConsole.clearConsole();
        System.out.println(user.toString());
    }
}
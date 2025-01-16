package org.example.views;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.example.Components.ClearConsole;
import org.example.Model.objects.Token;
import org.example.Model.objects.User;
import org.example.Model.services.DbOperations;
import org.example.Model.services.UserCrud;

public class ListUserView {
    
    public static void lisAlltUsers(Token tokenModel, DbOperations dbop, Scanner scanner, String token) {
        List<User> users = new ArrayList<User>();
        users = UserCrud.listAllUsers(dbop.getDbConnection(), token, tokenModel);
        if (users == null) {
            System.out.println("Error while listing users.");
            return;
        }
        ClearConsole.clearConsole();
        for (User user : users) {
            System.out.println(user.toString());
        }
    }

}

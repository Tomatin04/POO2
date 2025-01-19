package org.example.views.cruduser;

import java.util.Scanner;

import org.example.components.ClearConsole;
import org.example.models.objects.Token;
import org.example.models.objects.User;
import org.example.models.services.DbOperations;
import org.example.models.services.UserCRUD;

public class DeleteUserView {
    
    public static int deleteUser(User user, Token tokenModel, DbOperations dbop, Scanner scanner) {
        int id;
        int status;
        if (user.getRole() == "admin") {
            System.out.println("Would you like to delete your own account or another user? (1 - Own, 2 - Another user)");
            int deleteOption = scanner.nextInt();
            scanner.nextLine();
            if (deleteOption == 2) {
                ClearConsole.clearConsole();
                ListUserView.lisAlltUsers(tokenModel, dbop, scanner, user.getToken());
                System.out.print("Enter the ID of the user you want to delete: ");
                id = scanner.nextInt();
                scanner.nextLine();
                if (id == user.getId()) {
                    status = deleteConfirmation(user, tokenModel, dbop, scanner, true);
                }else{
                    status = deleteConfirmation(user, tokenModel, dbop, scanner, false);
                }
            } else {
                id = user.getId();
                status = deleteConfirmation(user, tokenModel, dbop, scanner, true);
            }
        }else{
            id = user.getId();
            status = deleteConfirmation(user, tokenModel, dbop, scanner, true);
        }
        System.out.println(status == 200 ? "User deleted successfully." : "User deletion failed.");
        return id;
    }

    private static int deleteConfirmation(User user, Token tokenModel, DbOperations dbop, Scanner scanner, boolean ownAccount) {
        String optionDelete = "Question";
        while (optionDelete != "y" && optionDelete != "n") {
            if (ownAccount) {
                System.out.println("are you sure you want to delete your own account? (y/n)");
            } else {
                System.out.println("are you sure you want to delete this account? (y/n)");
            }
            optionDelete = scanner.nextLine();
        }
        if (optionDelete.equals("y")) {
            return UserCRUD.deleteUser(user, dbop.getDbConnection(), user.getToken(), tokenModel);
        }
        return 401;
    }
}

package org.example.Components;

public class ClearConsole {
    
    public static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                ClearConsole.clearConsole();
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
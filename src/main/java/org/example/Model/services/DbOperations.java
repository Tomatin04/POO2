package org.example.Model.services;

import java.sql.Connection;

import org.example.Contorlers.DbController;

public class DbOperations {
    
    private Connection conn = null;
    public String dbStatus = "disconnected";

    public void initDbConnection() {
        try {
            conn = DbController.conectDb();
            if (conn == null) {
                System.out.println("Error while initializing database: Connection is null.");
            } else {
                System.out.println("Database initialized successfully.");
                dbStatus = "connected";
            }
        } catch (Exception e) {
            System.out.println("Error while initializing database: " + e.getMessage());
        }
    }

    public Connection getDbConnection() {
        return conn;
    }

    public void closeDbConnection() {
        try {
            DbController.closeConnection(conn);
            System.out.println("Database connection closed successfully.");
            dbStatus = "disconnected";
        } catch (Exception e) {
            System.out.println("Error while closing database connection: " + e.getMessage());
        }
    }
    
}
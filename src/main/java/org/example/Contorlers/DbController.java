package org.example.Contorlers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import java.sql.Statement;

public class DbController {
        
    private static Properties loadProps() throws IOException {
        try {
            InputStream input = DbController.class.getClassLoader().getResourceAsStream("props.properties");
            if (input == null) {
                return null;
            }
            Properties props = new Properties();
            props.load(input);
            return props;
        } catch (FileNotFoundException fnfe) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }
    
    public static Connection conectDb() throws IOException {
        try {
            Properties props = loadProps();
            if (props == null) {
                return null;
            }
            String URL = props.getProperty("URL");
            String USUARIO = props.getProperty("USER");
            String SENHA = props.getProperty("PASSWORD");
            DriverManager.getConnection(URL, USUARIO, SENHA);
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (SQLException e) {
            System.out.println("Erro ao conectar com o banco de dados: " + e.getMessage() + "\nPesquise por mais informações no log.");
            return null;
        } catch (Exception e) {
            System.out.println("Erro ao conectar com o banco de dados: " + e.getMessage() + "\nPesquise por mais informações no log.");
            return null;
        }
    }

    public static void closeConnection(Connection conn) throws IOException {
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("Erro ao fechar a conexão com o banco de dados: " + e.getMessage() + "\nPesquise por mais informações no log.");
        } catch (Exception e) {
            System.out.println("Erro ao fechar a conexão com o banco de dados: " + e.getMessage() + "\nPesquise por mais informações no log.");
        }
    }

    public static ResultSet executeQuery(Connection conn, String query) throws IOException {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            return rs;
        } catch (SQLException e) {
            System.out.println("Erro ao executar a query: " + e.getMessage() + "\nPesquise por mais informações no log.");
            return null;
        } catch (Exception e) {
            System.out.println("Erro ao executar a query: " + e.getMessage() + "\nPesquise por mais informações no log.");
            return null;
        }
    }

    public static boolean executeStatment(Connection conn, String query, List<?> dataList) throws IOException{
        boolean result = false;
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            System.out.println(query + "\n" + dataList); 
            for(int i = 0; i < dataList.size(); i++){
                System.out.println(dataList.get(i) + ": " + dataList.get(i).getClass().getSimpleName());
                switch (dataList.get(i).getClass().getSimpleName()) {
                    case "Integer":
                        pstmt.setInt(i+1, Integer.parseInt(dataList.get(i).toString()));
                        break;
                    case "String":
                        pstmt.setString(i+1, dataList.get(i).toString());
                        break;
                    default:
                        System.out.println("Erro ao criar o statment, tipo não encontrado");
                        pstmt.close();
                        return false;
                }
                System.out.println(pstmt.toString());
            }
            try {
                pstmt.execute();
                result = true;
            } catch (SQLException e) {
                System.out.println("Erro ao executar o statment: " + e.getMessage() + "\nPesquise por mais informações no log.");
            } catch (Exception e) {
                System.out.println("Erro ao executar o statment: " + e.getMessage() + "\nPesquise por mais informações no log.");
            } finally {
                pstmt.close();
            }
        } catch (Exception e) {
            System.out.println("Erro ao criar o statment: " + e.getMessage() + "\nPesquise por mais informações no log.");
        }
        return result;
    }
}

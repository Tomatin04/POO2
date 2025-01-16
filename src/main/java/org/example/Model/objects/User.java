package org.example.Model.objects;

import java.sql.Connection;
import java.sql.SQLException;

import org.example.Contorlers.LoginController;

public class User {
 
    private int id;
    private String fullName;
    private Email email;
    private String password;
    private String role;
    private String birthDate;
    private String cpf;
    private String token;

    public User() {
        this.id = 0;
        this.fullName = "";
        this.email = new Email("");
        this.password = "";
        this.role = "users";
        this.birthDate = "";
        this.cpf = "";
        this.token = "";
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return this.email.getEmail();
    }

    public void setEmail(String email) {
        this.email.setEmail(email);
    }

    public Email getEmailObject() {
        return this.email;
    }

    public void setEmailObject(Email email) {
        this.email = email;
    }

    public void setEmailId(int id) {
        System.out.println("Setting email id: " + id);
        this.email.setId(id);
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthDate() {
        return this.birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getCpf() {
        return this.cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean login(Connection conn, Token token) {
        try {
            this.token = LoginController.login(this.email.getEmail(), this.password, conn, token);
        } catch (SQLException e) {
            System.out.println("Error while trying to login: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
        if (this.token != null && !this.token.isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean logout(Token tokenModel) {
        return LoginController.logout(this.token, tokenModel);
    }

    @Override
    public String toString() { 
        return "User [id=" + id + ", fullName=" + fullName + ", email=" + email.getEmail() + ", password=" + password + ", role=" + role + ", birthDate=" + birthDate + ", cpf=" + cpf + "]";
    }

}

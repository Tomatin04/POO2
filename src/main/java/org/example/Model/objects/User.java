package org.example.Model.objects;

public class User {
 
    private int id;
    private String fullName;
    private String email;
    private String password;
    private String role;
    private String birthDate;
    private String cpf;

    public User(String fullName, String email, String password, String birthDate, String cpf) {
        this.id = 0;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.role = "users";
        this.birthDate = birthDate;
        this.cpf = cpf;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}

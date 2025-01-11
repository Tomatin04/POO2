package org.example.Model.objects;

public class User {
 
    private int id;
    private String fullName;
    private Email email;
    private String password;
    private String role;
    private String birthDate;
    private String cpf;

    public User(String fullName, Email email, String password, String birthDate, String cpf) {
        this.id = 0;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.role = "users";
        this.birthDate = birthDate;
        this.cpf = cpf;
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

    public String getEmail() {
        return this.email.getEmail();
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email.setEmail(email);
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

}

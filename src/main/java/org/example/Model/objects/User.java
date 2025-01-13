package org.example.Model.objects;

public class User {
 
    private int id;
    private String fullName;
    private Email email;
    private String password;
    private String role;
    private String birthDate;
    private String cpf;

    public User() {
        this.id = 0;
        this.fullName = "";
        this.email = new Email("");
        this.password = "";
        this.role = "users";
        this.birthDate = "";
        this.cpf = "";
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

}

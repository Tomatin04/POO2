/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.view;

import java.util.List;
import java.util.Scanner;
import javax.swing.JOptionPane;
import org.example.controllers.TokenController;
import org.example.models.objects.Token;
import org.example.models.objects.User;
import org.example.models.services.DbOperations;
import org.example.models.services.UserCRUD;

/**
 *
 * @author matheus.sampaio
 */
public class ViewController {
    Token token;
    DbOperations dbop;
    //dbop.initDbConnection();
    //Scanner scanner = new Scanner(System.in);
    User user;

    public ViewController(){
        this.token = TokenController.createTokenModel();
        dbop  = new DbOperations();
        this.user = new User();
    }


    /*Acesso ao sistema*/
    public void loginView(String email, String password){
        try{
            dbop.initDbConnection();
            user.setEmail(email);
            user.setPassword(password);
            if(user.login(dbop.getDbConnection(), token)){
                user = TokenController.getUserDataFromToken(user.getToken(), token);
                funcAlert("Login realizado com sucesso");
            }else{
                funcAlert("Erro ao realizar login");
                dbop.closeDbConnection();
            }
        }catch(Exception e){
            dbop.closeDbConnection();
            funcAlert(e.getMessage());

        }
    }

    public void logoutView(){
        try{
            user.logout(token);
            user = new User();
            dbop.closeDbConnection();
        }catch(Exception e){
            dbop.closeDbConnection();
            funcAlert(e.getMessage());  
        }
    }

    /*Interações de usuário */
    public void registerView(String name, String email, String password, String birthDate, String cpf){
       try{
           User userRegister = new User();

            userRegister.setFullName(name);
            userRegister.setEmail(email);
            userRegister.setBirthDate(birthDate);
            userRegister.setCpf(cpf);
            userRegister.setPassword(password);

            dbop.initDbConnection();
            if(UserCRUD.createUser(userRegister, dbop.getDbConnection()) == 200){
                funcAlert("User cadastrado com sucesso ");
                dbop.closeDbConnection();
            }else{
                funcAlert("Erro ao registrar usuario usuario");
            }
       }catch(Exception e ){
           dbop.closeDbConnection();
           funcAlert(e.getMessage()); 
       }
    }
    
    public void updateView(int id, String name, String email, String password, String cpf, String birthDate){
        //user = TokenController.getUserDataFromToken(user.getToken(), token);
        User userAltera = new User();
        if(user.getRole().equals("admin")){
            userAltera.setId(id);
            userAltera.setFullName(name);
            userAltera.setCpf(cpf);
            userAltera.setPassword(password);
            userAltera.setEmail(email);
            userAltera.setBirthDate(birthDate);
         
            if(UserCRUD.updateUser(user, dbop.getDbConnection(), user.getToken(), token) == 200){
                funcAlert("Update realizado com sucesso "); 
            }else{
                funcAlert("Erro ao realizar update"); 
            }
        }
        funcAlert("Sem autorizaçã para  a alteração "); 
    }
        
    
    public void listUsers(String id){
        if(user.getRole().equals("admin")){
            User userSearch = new User();
            userSearch.setId(Integer.parseInt(id));
            userSearch = UserCRUD.listUser(dbop.getDbConnection(), user.getToken(), token, userSearch);
            funcAlert(userSearch.toString());
        }else{
            funcAlert("Sem autorização para a ação ");
        }
    }
    
    public void listAllUsers(){
        if(user.getRole().equals("admin")){
            List<User> users = UserCRUD.listAllUsers(dbop.getDbConnection(), user.getToken(), token);
            
        }else{
            funcAlert("Sem autorização para a ação ");
        }
    }
    
    public void deleteUser(String id){
        if(funcOption()){
            User userAux = new User();
            userAux.setId(Integer.parseInt(id));
            if(UserCRUD.deleteUser(userAux, dbop.getDbConnection() , user.getToken(), token) == 200){
                funcAlert("Usuario excluido com sucesso");
                if(!user.getRole().equals("admin")) ;
            }else{
                funcAlert("Não foi possivel deletar o usuario");
            }
        }
    }
       
        
        
    public User getUser(){
        return user;
    }
        
    public void funcAlert(String msg){
        JOptionPane.showMessageDialog(null, msg, "Menssagem", JOptionPane.WARNING_MESSAGE);
    }
    
    public boolean funcOption(){
        int resposta = JOptionPane.showConfirmDialog(null, "Você deseja continuar?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (resposta == JOptionPane.YES_OPTION) {
            System.out.println("Usuário escolheu sim.");
            return true;
        } else {
            System.out.println("Usuário escolheu não.");
            return false;
        }
    }
}

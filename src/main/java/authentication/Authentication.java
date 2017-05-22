/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package authentication;

/**
 *
 * @author kodexx
 */
public class Authentication extends Database{
    
    public static Database dbConnection = new Database();
    public boolean authenticate(String submittedUsername,  String submittedpassword){
    
    boolean check = dbConnection.login(submittedUsername, submittedpassword); 
    
    return check;
    }    
   
}

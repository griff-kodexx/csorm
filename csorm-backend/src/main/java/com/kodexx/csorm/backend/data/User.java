package com.kodexx.csorm.backend.data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity("users")

public class User {
    @Id
    @NotNull
    private int id = -1;
    @NotNull
    @Size(min = 10, message = "Enter a valid registration number")
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String email;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public void reset(){
        //this.id = -1;
        this.username  = "";
        this.password ="";
        this.email ="";        
        this.firstName = "";
        this.lastName ="";
        
    }
}

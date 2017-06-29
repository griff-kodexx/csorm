package com.kodexx.csorm.backend.service;

import com.kodexx.csorm.backend.data.User;
import java.io.Serializable;
import java.util.Collection;

/**
 * Back-end service interface for retrieving and updating users
 */
public abstract class UserService implements Serializable{
    public static UserService get(){
        return UserServiceImpl.getInstance();
    }
    
    public abstract Collection<User> getAllUsers();
    
    public abstract void updateUser(User u);
    
    public abstract void deleteUser(int userId);
    
    public abstract User getUserById(int userId);
}

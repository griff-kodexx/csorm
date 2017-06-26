    package com.kodexx.csorm.authentication;

import com.kodexx.csorm.backend.database.CheckDatabase;

/**
 * Default mock implementation of {@link AccessControl}. This implementation
 * accepts any string as a password, and considers the user "admin" as the only
 * administrator.
 */
public class BasicAccessControl extends CheckDatabase implements AccessControl {

    @Override
    public boolean signIn(String username, String password) {
        
       /*if(username ==null || username.isEmpty() || password==null || password.isEmpty()){
            return false;
        }else{        
            if( !new CheckDatabase().login(username, password)){
                } else {
                    CurrentUser.set(username);
                    return true;
                }
                return false;
        }
        */
       
        /*
        quick authentication that doesn't check the database. For testing only :)
        the username is needed for setting session attributes so msee usiitoe
        */
        
        if (username == null || username.isEmpty())
            return false;

        CurrentUser.set(username);
        return true; 
    }

    @Override
    public boolean isUserSignedIn() {
        return !CurrentUser.get().isEmpty();
    }

    @Override
    public boolean isUserInRole(String role) {
        if ("admin".equals(role)) {
            // Only the "admin" user is in the "admin" role
            return getPrincipalName().equals("admin");
        }

        // All users are in all non-admin roles
        return true;
    }

    @Override
    public String getPrincipalName() {
        return CurrentUser.get();
    }

}

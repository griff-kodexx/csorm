package com.kodexx.csorm.authentication;

import com.kodexx.csorm.backend.database.CheckDatabase;

public class BasicAccessControl extends CheckDatabase implements AccessControl {  //CheckDatabase should be abstract

    @Override
    public boolean signIn(String username, String password) {
        
      if(username ==null || username.isEmpty() || password==null || password.isEmpty())
        {
            return false;
        }
      else
        {
            if( !new CheckDatabase().login(username, password)){
                } else {
                    CurrentUser.set(username);
                    return true;
                }
                return false;
        }       
       
        /*
         * quick authentication that doesn't check the database. For testing only.
         * the username is needed for setting session attributes so msee usiitoe
      
        if (username == null || username.isEmpty() || username == "admin")
            return false;

        CurrentUser.set(username);
        return true; 
      
         */
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

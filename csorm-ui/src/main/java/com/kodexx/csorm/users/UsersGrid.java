package com.kodexx.csorm.users;

import com.kodexx.csorm.backend.data.User;
import com.vaadin.ui.Grid;

public class UsersGrid extends Grid<User> {
    
    public UsersGrid(){
    
        setSizeFull();
        
        addColumn(User::getUsername).setCaption("Registration Number");
        addColumn(User::getFirstName).setCaption("First Name");
        addColumn(User::getLastName).setCaption("Last Name");        
        addColumn(User::getEmail).setCaption("Email");
    }
    
    public User getSelectedRow(){
        return asSingleSelect().getValue();
    }
    
    public void refresh(User user){
        getDataCommunicator().refresh(user);
    }
    
}

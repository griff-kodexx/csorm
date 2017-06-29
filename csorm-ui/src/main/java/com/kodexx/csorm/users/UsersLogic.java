package com.kodexx.csorm.users;

import com.kodexx.csorm.MyUI;
import com.kodexx.csorm.backend.data.User;
import com.kodexx.csorm.backend.service.UserServiceImpl;
import com.vaadin.server.Page;
import java.io.Serializable;

public class UsersLogic implements Serializable{
    private UsersView view;

    UsersLogic(UsersView simpleUsersView) {
        view = simpleUsersView;
    }
    
    public void init(){
        editUser(null);
        //Hide and disable if not admin (or not permitted)
        if(!MyUI.get().getAccessControl().isUserInRole("admin")){
            view.setNewUserEnabled(false);
        }
    }
    
    public void cancelUser(){
        setFragmentParameter("");
        view.clearSelection();
    }
    
    /**
     * Update the fragment without causing navigator to change view
     */

    private void setFragmentParameter(String userId){
        String fragmentParamter;
        if(userId == null || userId.isEmpty()){
            fragmentParamter = "";
        }else{
            fragmentParamter = userId;
        }

        Page page = MyUI.get().getPage();
        page.setUriFragment("!" + UsersView.VIEW_NAME + "/" + fragmentParamter, false);

    }
    
    public void enter(String userId){
        if(userId != null || !userId.isEmpty()){
            if(userId.equals("new")){
                newUser();
            }else{
                //ensure it is selected even when coming directly from login
                try{
                    int uid = Integer.parseInt(userId);
                    User user =findUser(uid);
                    view.selectRow(user);
                }catch(NumberFormatException e){

                }
            }
        }
    }
    
    private User findUser(int userId){
        return UserServiceImpl.get().getUserById(userId);
    }
    
    public void saveUser(User user){
        view.showSaveNotification(user.getUsername()+ "(" + user.getLastName()+ ") updated");
        view.clearSelection();
        view.updateUser(user);
        setFragmentParameter("");
    }
    
    public void deleteUser(User user){
        view.showSaveNotification(user.getUsername()+ "("
         + user.getLastName()+ ") removed");
        view.clearSelection();
        view.removeUser(user);
        setFragmentParameter("");
    }
    
    public void editUser(User user){
        if(user == null){
            setFragmentParameter("");
        }else{
            setFragmentParameter(user.getUsername() + "");
        }
        view.editUser(user);
    }
    
    public void newUser(){
        view.clearSelection();
        setFragmentParameter("new");
        view.editUser(new User());
    }
    
    public void rowSelected(User user){
        if(MyUI.get().getAccessControl().isUserInRole("admin")){
            view.editUser(user);
        }
    }
   
}

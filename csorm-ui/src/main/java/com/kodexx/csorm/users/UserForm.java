package com.kodexx.csorm.users;

import com.kodexx.csorm.backend.data.User;
import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.Binder;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.Upload;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * a form for editing a single user
 */
public class UserForm extends UsersFormDesign implements View,Upload.Receiver,Upload.SucceededListener, Upload.ProgressListener {

    private  UsersLogic viewLogic;
    private Binder<User> binder;
    private User currentUser;
    private ByteArrayOutputStream outputStream;
    
    public UserForm(UsersLogic usersLogic){
        super();
        addStyleName("product-form");
        viewLogic = usersLogic;
        
        binder = new BeanValidationBinder<>(User.class);
        binder.bindInstanceFields(this);

        // enable/disable save button while editing
        binder.addStatusChangeListener(event-> {
            boolean isValid = !event.hasValidationErrors();
            boolean hasChanges = binder.hasChanges();
            save.setEnabled(hasChanges && isValid);
            discard.setEnabled(hasChanges);
        });
        
        save.addClickListener(event -> {
            if(currentUser != null && binder.writeBeanIfValid(currentUser)){
                viewLogic.saveUser(currentUser);
            }
        });
        
        discard.addClickListener(event -> viewLogic.editUser(currentUser));
        cancel.addClickListener(event -> viewLogic.cancelUser());
        
        delete.addClickListener(event ->{
            if (currentUser !=null) {
                viewLogic.deleteUser(currentUser);
            }
        });
        
        //upload area
        //upload = new Upload();
        upload.setReceiver(this);
        upload.addSucceededListener(this);
        upload.addProgressListener(this);
        bar.setCaption("Progress");
        bar.setSizeFull();
        
        
    }
    
    public void editUser(User user){
        if (user == null) {
            user = new User();
        }        
        currentUser = user;
        binder.readBean(user);
        
        //Scroll to the top
        // As this is not a Panel, using JavaScript
        String scrollScript = "window.document.getElementById('" + getId()
                + "').scrollTop = 0;";
        Page.getCurrent().getJavaScript().execute(scrollScript);
    }
    
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        
    }

    @Override
    public OutputStream receiveUpload(String filename, String mimeType) {
        this.outputStream = new ByteArrayOutputStream();
        return outputStream;
    }

    @Override
    public void uploadSucceeded(Upload.SucceededEvent event) {
       OutputStream out = null;
        java.io.File tfile = new java.io.File("/tmp/thefilename");
        try {
            out = new FileOutputStream(tfile);
            outputStream.writeTo(out);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(UserForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UserForm.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(UserForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void updateProgress(long readBytes, long contentLength) {
         //Update the progress bar (bar.setValue()) accordingly
        //how?? sijui
    }
    
}

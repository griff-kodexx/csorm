/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kodexx.csorm.files;

import com.kodexx.csorm.samples.backend.data.File;
import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.Binder;
import com.vaadin.server.Page;

/**
 *
 * a form for editing a single file
 */
public class FileForm extends FilesFormDesign{
    
    private FilesLogic viewLogic;
    private Binder<File> binder;
    private File currentFile;
    
    public FileForm(FilesLogic filesLogic){
        super();
        addStyleName("product-form");
        viewLogic = filesLogic;
        
        binder = new BeanValidationBinder<>(File.class);
        
        // enable/disable save button while editing
        binder.addStatusChangeListener(Event-> {
            boolean isValid = !Event.hasValidationErrors();
            boolean hasChanges = binder.hasChanges();
            save.setEnabled(hasChanges && isValid);
            discard.setEnabled(hasChanges);
        });
        
        save.addClickListener(event -> {
            if(currentFile != null && binder.writeBeanIfValid(currentFile)){
                viewLogic.saveFile(currentFile);
            }
        });
        
        discard.addClickListener(event -> viewLogic.editFile(currentFile));
        cancel.addClickListener(event -> viewLogic.cancelFile());
        
        delete.addClickListener(event -> {
            if(currentFile !=null ){
                viewLogic.deleteFile(currentFile);
            }
        });
        
    }
    
    public void editFile(File file){
        if(file == null){
            file = new File();
        }
        currentFile = file;
        binder.readBean(file);
        
        // Scroll to the top
        // As this is not a Panel, using JavaScript
        String scrollScript = "window.document.getElementById('" + getId()
                + "').scrollTop = 0;";
        Page.getCurrent().getJavaScript().execute(scrollScript);
        
    }
}
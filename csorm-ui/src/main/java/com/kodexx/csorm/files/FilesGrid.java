/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kodexx.csorm.files;

import com.kodexx.csorm.backend.data.File;
import com.vaadin.ui.Grid;
import com.vaadin.ui.renderers.NumberRenderer;


/**
 *
 * @author kodexx
 */
public class FilesGrid extends Grid<File>{
    
    public FilesGrid(){
        setSizeFull();
        
        
        addColumn(File::getCode).setCaption("Unit Code");
        addColumn(File::getTitle).setCaption("Unit Title");
        addColumn(File::getDescription).setCaption("Description");
        addColumn(File::getSize).setCaption("Size");
        addColumn(File::getMime).setCaption("File Type");
        addColumn(File::getUploadDate).setCaption("Uploaded On");
        addColumn(File::getUploadedBy).setCaption("Uploaded By");
        
        
        
        
                
    }
    
    public File getSelectedRow(){
        return asSingleSelect().getValue();
    }
    
    public void refresh(File file){
        getDataCommunicator().refresh(file);
    }
    
      
}

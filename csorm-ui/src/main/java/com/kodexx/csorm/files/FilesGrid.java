/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kodexx.csorm.files;

import com.kodexx.csorm.samples.backend.data.File;
import com.vaadin.ui.Grid;
import com.vaadin.ui.renderers.NumberRenderer;


/**
 *
 * @author kodexx
 */
public class FilesGrid extends Grid<File>{
    
    public FilesGrid(){
        setSizeFull();
        
        addColumn(File::getId).setCaption("Id");
        addColumn(File::getTitle).setCaption("File Name");
                
    }
    
    public File getSelectedRow(){
        return asSingleSelect().getValue();
    }
    
    public void refresh(File file){
        getDataCommunicator().refresh(file);
    }
    
      
}

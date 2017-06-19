/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kodexx.csorm.files;

import com.kodexx.csorm.MyUI;
import com.kodexx.csorm.samples.backend.DataService;
import com.kodexx.csorm.samples.backend.data.File;
import com.vaadin.server.Page;
import java.io.Serializable;

/**
 *
 * @author kodexx
 */
public class FilesLogic implements Serializable{
    private FilesView view;

    public FilesLogic(FilesView simpleFilesView) {
        view = simpleFilesView;
    }
    
    
    
    public void init(){
        editFile(null);
        //Hide and disable if not admin (or not permitted)
        if(!MyUI.get().getAccessControl().isUserInRole("admin")){
            view.setNewFileEnabled(false);            
        }
    }
    
    public void cancelFile(){
        setFragmentParameter("");
        view.clearSelection();
    }
    
    
    /**
     * Update the fragment without causing navigator to change view
     */
    
    private void setFragmentParameter(String fileId){
        String fragmentParamter;
        if(fileId == null || fileId.isEmpty()){
            fragmentParamter = "";
        }else{
            fragmentParamter = fileId;
        }
        
        Page page = MyUI.get().getPage();
        page.setUriFragment("!" + FilesView.VIEW_NAME + "/" + fragmentParamter, false);
        
    }
    
    public void enter(String fileId){
        if(fileId != null || !fileId.isEmpty()){
            if(fileId.equals("new")){
                newFile();
                //ensure it is selected even when coming directly from login
                try{
                    int fid = Integer.parseInt(fileId);
                    File file =findFile(fid);
                    view.selectRow(file);
                }catch(NumberFormatException e){
                    
                }
            }
        }
    }
    
    private File findFile(int fileId){
        return DataService.get().getFileById(fileId);
    }
    
    public void saveFile(File file){
        view.showSaveNotification(file.getTitle()+ "(" + file.getId() + ") update");
        view.clearSelection();
        view.updateFile(file);
        setFragmentParameter("");
    }
    
    public void deleteFile(File file){
        view.showSaveNotification(file.getTitle()+ "("
         + file.getId() + ") removed");
    }
    
    public void editFile(File file){
        if(file == null){
            setFragmentParameter("");
        }else{
            setFragmentParameter(file.getId() + "");
        }
        view.editFile(file);
    }
    
    public void newFile(){
        view.clearSelection();
        setFragmentParameter("new");
        view.editFile(new File());
    }
    
    public void rowSelected(File file){
        if(MyUI.get().getAccessControl().isUserInRole("admin")){
        view.editFile(file);
    }
    }
}



















































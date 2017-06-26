package com.kodexx.csorm.files;

import com.kodexx.csorm.backend.data.File;
import com.vaadin.ui.Grid;

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

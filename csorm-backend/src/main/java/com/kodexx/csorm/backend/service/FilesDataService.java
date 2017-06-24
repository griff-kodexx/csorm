/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kodexx.csorm.backend.service;

import com.kodexx.csorm.backend.service.DataService;
import com.kodexx.csorm.backend.data.File;
import com.mongodb.MongoClient;
import java.util.Collection;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;


/**
 *
 * @author kodexx
 */
public class FilesDataService extends DataService{
    
    final Morphia morphia = new Morphia();
    final Datastore datastore = morphia.createDatastore(new MongoClient(), "csorm");
    
    private FilesDataService(){
        morphia.mapPackage("com.kodexx.csorm.backend.data");
        
    }
    
    
    
    @Override
    public Collection<File> getAllFiles() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateFile(File p) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteFile(int fileId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public File getFileById(int fileId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

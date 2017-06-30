package com.kodexx.csorm.backend.service;

import com.kodexx.csorm.backend.data.File;
import com.kodexx.csorm.backend.database.CheckDatabase;
import com.mongodb.MongoClient;
import java.util.List;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.FindAndModifyOptions;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.QueryResults;
import org.mongodb.morphia.query.UpdateOperations;

public class FilesDataService extends DataService{

    private static FilesDataService INSTANCE;


    private static CheckDatabase theDatabase = new CheckDatabase();
    public synchronized static DataService getInstance(){
        if(INSTANCE == null){
            INSTANCE = new FilesDataService();
        }
        return INSTANCE;
    }
    private int nextFileId =0;
    final Morphia morphia = new Morphia();
    final Datastore datastore = morphia.createDatastore(new MongoClient(theDatabase.getAddress(), theDatabase.getPort()), "csorm");

    private FilesDataService(){
        morphia.mapPackage("com.kodexx.csorm.backend.data");
        datastore.ensureIndexes();
        nextFileId = getNumberOfFiles();        
    }

    private int getNumberOfFiles(){
        try {
            final QueryResults<File> query = datastore.createQuery(File.class);
            return (int) query.countAll();
        } catch (Exception e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    @Override
    public synchronized List<File> getAllFiles() {
        try {
            final Query<File> query = datastore.createQuery(File.class);
            return query.asList();
        } catch (Exception e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    }

    @Override
    public synchronized void updateFile(File p) {
        if (p.getId() < 0) {
            //new file
            p.setId(nextFileId++);
            datastore.save(p);
            return;
        }
        
        try {
            final Query<File> fromDb = datastore.createQuery(File.class).filter("id ==", p.getId());            
            datastore.delete(fromDb);
            datastore.save(p);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }       
    }

    @Override
    public synchronized void deleteFile(int fileId) {
       final Query<File> fileInDb = datastore.createQuery(File.class).filter("id ==", fileId);       
       datastore.delete(fileInDb);
    }

    @Override
    public synchronized File getFileById(int fileId) {
        final Query<File> fileInDb = datastore.createQuery(File.class).filter("id ==", fileId);
        return fileInDb.asList().iterator().next();
    }

}

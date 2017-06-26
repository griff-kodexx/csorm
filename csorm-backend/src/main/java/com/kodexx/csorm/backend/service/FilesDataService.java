package com.kodexx.csorm.backend.service;

import com.kodexx.csorm.backend.data.File;
import com.kodexx.csorm.backend.database.CheckDatabase;
import com.mongodb.MongoClient;
import java.util.List;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;


/**
 *
 * @author kodexx
 */
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
        //fetch the numberOfRecordsFrom Database = theNumberOfRecordsInDatabase
        // nextFileId = theNumberOfRecordsInDatabase + 1;


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


        /*instead of looping through mongodb records,
        *use some sort of query to find file id and update accordingly
        */

        /*for (int i = 0; i < files.size(); i++) {
            if (files.get(i).getId() == p.getId()) {
                files.set(i, p);
                return;
            }*/
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public synchronized void deleteFile(int fileId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public synchronized File getFileById(int fileId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

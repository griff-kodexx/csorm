package com.kodexx.csorm.backend.service;

import com.kodexx.csorm.backend.data.User;
import com.kodexx.csorm.backend.database.CheckDatabase;
import com.kodexx.csorm.backend.database.HashPasswords;
import com.mongodb.MongoClient;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.QueryResults;

public class UserServiceImpl extends UserService{
    
    private static UserServiceImpl INSTANCE;
    
    private static CheckDatabase theDatabase = new CheckDatabase();
    public synchronized static UserService getInstance(){
        if(INSTANCE == null){
            INSTANCE = new UserServiceImpl();
        }
        return INSTANCE;
    }
    
    private int nextUserId = 0;
    final Morphia morphia = new Morphia();
    final Datastore datastore = morphia.createDatastore(new MongoClient(theDatabase.getAddress(), theDatabase.getPort()), "csorm");
    
    private UserServiceImpl(){
        morphia.mapPackage("com.kodexx.csorm.backend.data");
        datastore.ensureIndexes();
        nextUserId = getNumberOfUsers();        
    }
    
     private int getNumberOfUsers(){
        try {
            final QueryResults<User> query = datastore.createQuery(User.class);
            return (int) query.countAll();
        } catch (Exception e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    @Override
    public Collection<User> getAllUsers() {
        try{
            final Query<User> query = datastore.createQuery(User.class);
            return query.asList(); 
        } catch(Exception e){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    @Override
    public void updateUser(User u) {
        try {
            
            String generatedSecuredPasswordHash = HashPasswords.generateStrongPasswordHash(u.getPassword());
            u.setPassword(generatedSecuredPasswordHash);            
            if(u.getId() < 0){
                //new user
                u.setId(nextUserId++);
                datastore.save(u);
                return;
            }
            try {
                
            } catch (Exception e) {
                final Query<User> fromDb = datastore.createQuery(User.class).filter("id==", u.getId());
                User userInDb = fromDb.asList().iterator().next();
                datastore.delete(userInDb);
                datastore.save(u);
            }
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null,ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public void deleteUser(int userId) {
        final Query<User> userInDb = datastore.createQuery(User.class).filter("id ==", userId);
        datastore.delete(userInDb);
    }

    @Override
    public User getUserById(int userId) {
        final Query<User> userInDb = datastore.createQuery(User.class).filter("id ==", userId);
        return userInDb.asList().iterator().next();
    }
    
}


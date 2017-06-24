//this class should be implemented as an interface in the back-end module
//Also authenticatin SHOULD be implemented using JAAS (Java Authentication and Authorization Service)
//probably should use spring security (spring + vaadin) ama vipi?????

package com.kodexx.csorm.backend.database;


import com.kodexx.csorm.authentication.HashPasswords;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CheckDatabase{
	@SuppressWarnings("deprecation")
        public String realpass;         
        private String address="localhost";
        private int port=27017;
        private String database = "csorm";
        private String collection = null;        
        private static MongoClient mongoClient;   //for multiples access?? Make more than one object
        protected BasicDBObject basicDBObject = new BasicDBObject();
        protected boolean userIsPriviledged;
        
        
        //use this method if the parameters are the predefined above
        protected void setParameters(String collection){
            this.collection = collection;
        }
        //override if you need to set specifics of everything
        protected void setParameters(String address, int port, String database, String collection){
            this.address = address;
            this.port = port;
            this.database = database;
            this.collection = collection;
        }
        
        //need to set up authentication on mongo for security
	protected DBCollection connectDB(){
            mongoClient =  new MongoClient(address,port);
            DB db = mongoClient.getDB(database); //make this global perhaps to reduce number of connection to mongodb
            DBCollection dbCollection = db.getCollection(collection);            
            //mongoClient.close();             
            return dbCollection;
        }
        protected void closeConnection(){
            CheckDatabase.mongoClient.close(); //could it kill all the unnecessary connections? Bado iko implented below
            
        }
        
        protected boolean login(final String submittedUsername, final String submittedpassword){
           
            setParameters("users");
            
            boolean flag = false;
            BasicDBObject searchQuery = new BasicDBObject();
            
            searchQuery.put("ID",submittedUsername);
           
            
            DBCursor cursor = connectDB().find(searchQuery);     
            
            try{
                DBObject oneRecord = cursor.next();
                //LoggedIn Name to be displayed = (String) oneRecord.get("First Name") + " " + (String) oneRecord.get("Last Name");                
                String dbpassword = (String) oneRecord.get("Password");
                String dbclearance = (String) oneRecord.get("Clearance");
            
            try {
                if( !HashPasswords.validatePassword(submittedpassword, dbpassword)){
                    System.out.println("Wrong details");
                    flag = false;
                }
                else{
                    
                    if(null == dbclearance) {
                        System.out.println("Return null from db...no record");
                    } else {
                        
                        switch (dbclearance) {
                            case "Admin":
                                System.out.println("User is Admin");  //such output should redirected to the log file with other login details e.g time
                                flag =true;
                                userIsPriviledged = true;
                                break;
                            case "Basic":
                                System.out.println("User is Basic");
                                flag =true;
                                userIsPriviledged = false;
                                break;
                            default:
                                System.out.println("User is NOT yet allowed to Log in");
                                flag =false;
                                userIsPriviledged = false;
                                break;
                        }
                    }
                }
                
                
            } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
                Logger.getLogger(CheckDatabase.class.getName()).log(Level.SEVERE, null, ex); //say whaaaat?
            }
            }
            catch(Exception NoSuchElementException){
                flag = false;
                //do something..
            }  
            
            //closeConnection(); //kill connection to database and return authentication flag
            closeConnection();
            return flag;
        }
        
}
        
  
 
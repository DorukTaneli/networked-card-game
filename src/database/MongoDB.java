package database;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.MongoClient; 
import com.mongodb.MongoCredential;  
import org.bson.Document; 

public class MongoDB {

	MongoClient mongo;
	MongoCredential credential; 
	MongoDatabase database;
	MongoCollection<Document> collection;
	
	public MongoDB() {
	      // Creating a Mongo client 
	      mongo = new MongoClient( "localhost" , 27017 ); 
	   
	      // Creating Credentials 
	      credential = MongoCredential.createCredential("sampleUser", "myDb", 
	         "password".toCharArray()); 
	      
	      // Accessing the database 
	      database = mongo.getDatabase("myDb"); 
	      System.out.println("Credentials ::"+ credential); 
	      
	      //Create collection
	      database.createCollection("sampleCollection");
	      
	      //Get collection
	      collection = database.getCollection("myCollection"); 
	}

	 
}

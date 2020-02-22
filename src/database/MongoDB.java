package database;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.MongoClient; 
import com.mongodb.MongoCredential;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import org.bson.Document; 

public class MongoDB {

	MongoClient mongo;
	MongoCredential credential; 
	MongoDatabase database;
	MongoCollection<Document> collection;
	
	public MongoDB() {
		super();
	      // Creating a Mongo client 
	      mongo = new MongoClient( "localhost" , 27017 ); 
	     
	      // Creating Credentials 
	      credential = MongoCredential.createCredential("sampleUser", "myDb", 
	         "password".toCharArray()); 
	      
	      // Accessing the database 
	      MongoDatabase database = mongo.getDatabase("myDb");  
	      
	      // Retrieving a collection
	      collection = database.getCollection("myCollection"); 
	 
	}
	
	//call this only the first time you use the database
	public void initiateDatabase() {
		createCollection();
	}
	
	public void createCollection() {
	      database.createCollection("Collection1");
	}
	
	//call this at the start of every game
	public void insert(String P1, String P2, int round, int cardsRemaining, int scoreP1, int scoreP2) {
		Document document = new Document("id", 1)
				  .append("Player1", P1)
			      .append("Player2", P2)
			      .append("round", round) 
			      .append("RemainingCards", cardsRemaining) 
			      .append("Player1Score", scoreP1) 
			      .append("Player2Score", scoreP2);
		collection.insertOne(document);
	}
	
	public void update(int round, int cardsRemaining, int scoreP1, int scoreP2) {
		collection.updateOne(Filters.eq("id", 1), 
				Updates.combine(Updates.set("round", round),
								Updates.set("RemainingCards", cardsRemaining),
								Updates.set("Player1Score", scoreP1),
								Updates.set("Player2Score", scoreP2)));
	}

	public void delete() {
		collection.deleteOne(Filters.eq("id", 1));
	}
		
	public String getPlayer1Name() {
		FindIterable<Document> findIterable = collection.find(Filters.eq("id", 1));
		return (String)findIterable.iterator().next().get("Player1");
	}
	
	public void printDatabaseContents() {
		FindIterable<Document> iterDoc = collection.find();
		Iterator<Document> it = iterDoc.iterator(); 
		while (it.hasNext()) {  
	         System.out.println(it.next());  
	    }
	}
	
	public MongoCredential getCredential() {
		return credential;
	}
	
	//Call this function every 30 seconds
	public String syncDB(int round, int cardsRemaining, int scoreP1, int scoreP2) {
		Date now = new Date();
		String time = new SimpleDateFormat("HH:mm:ss").format(now);
		String str = "Current time: " + time;
			
		FindIterable<Document> findIterable = collection.find(Filters.eq("id", 1));
		Document doc = findIterable.iterator().next();
				
		if ((int)doc.get("round") != round) {		
			str = str.concat(", Database will be synchronized");
			update(round, cardsRemaining, scoreP1, scoreP2);
			str = str.concat("\nSynchronization done with MongoDB");
		} else {
			str = str.concat(", no update is needed. Already synced!");
		}

		return str;
	}
	
}

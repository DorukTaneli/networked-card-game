package database;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient; 
import com.mongodb.MongoCredential;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.bson.Document; 

public class MongoDB {

	MongoClient mongo;
	MongoCredential credential; 
	MongoDatabase database;
	MongoCollection<Document> collection;
	
    /**
     * Creates a MongoClient in localhost with port number 27017.
     * Sets credentials to 'myDb' database as username: 'sampleuser' and password: 'password'
     * Creates 'myDb' database and creates 'myCollection' collection inside it.
     */
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
		
	public void dropCollection() {
		collection.drop();
	}
	
	//call this at the start of every game
	public void insert(int gameID, String P1, String P2, int round, int cardsRemaining, int scoreP1, int scoreP2) {
		Document document = new Document("gameID", 1)
				  .append("Player1", P1)
			      .append("Player2", P2)
			      .append("round", round) 
			      .append("RemainingCards", cardsRemaining) 
			      .append("Player1Score", scoreP1) 
			      .append("Player2Score", scoreP2);
		collection.insertOne(document);
	}
	
	public void update(int gameID, int round, int cardsRemaining, int scoreP1, int scoreP2) {
		collection.updateOne(Filters.eq("gameID", gameID), 
				Updates.combine(Updates.set("round", round),
								Updates.set("RemainingCards", cardsRemaining),
								Updates.set("Player1Score", scoreP1),
								Updates.set("Player2Score", scoreP2)));
	}

	public void delete(int gameID) {
		collection.deleteOne(Filters.eq("gameID", gameID));
	}
	
	public void deleteAll() {
		BasicDBObject document = new BasicDBObject();
		collection.deleteMany(document);
	}
		
	public String getPlayer1Name(int gameID) {
		FindIterable<Document> findIterable = collection.find(Filters.eq("gameID", gameID));
		return (String)findIterable.iterator().next().get("Player1");
	}
	
	public void printDatabaseContents() {
		FindIterable<Document> iterDoc = collection.find();
		Iterator<Document> it = iterDoc.iterator(); 
		while (it.hasNext()) {  
	         System.out.println(it.next());  
	    }
	}
	
	public Document getGameInfo(int gameID) {
		FindIterable<Document> findIterable = collection.find(Filters.eq("gameID", gameID));
		return findIterable.iterator().next();
	}
	
	public MongoCredential getCredential() {
		return credential;
	}
	
	//Call this function every 30 seconds
	public String syncDB(int gameID, int round, int cardsRemaining, int scoreP1, int scoreP2) {
		Date now = new Date();
		String time = new SimpleDateFormat("HH:mm:ss").format(now);
		String str = "Current time: " + time;
			
		FindIterable<Document> findIterable = collection.find(Filters.eq("gameID", gameID));
		Document doc = findIterable.iterator().next();
				
		if ((int)doc.get("round") != round) {		
			str = str.concat(", Database will be synchronized for game " + gameID);
			update(gameID, round, cardsRemaining, scoreP1, scoreP2);
			str = str.concat("\nSynchronization done with MongoDB");
		} else {
			str = str.concat(", no update is needed. Already synced!");
		}

		return str;
	}
	
}

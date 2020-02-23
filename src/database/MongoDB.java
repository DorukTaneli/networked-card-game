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

/**
 * @author DorukTaneli
 *
 *	Class for interaction with MongoDB Database from the rest of the program.
 */
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
	
	public MongoCredential getCredential() {
		return credential;
	}
	
    /**
     * Drops the collection. Only for development purposes, do not call from the rest of the program.
     */
	@SuppressWarnings("unused")
	private void dropCollection() {
		collection.drop();
	}
	

	/**
	 * Insert the game info to the database. Only use this function when you first start the game.
	 * See syncDB function to synchronize the database contents with current game info as the game continues.
	 * 
	 * @param gameID id of the game, typically 1, increase as there are more games being played concurrently
	 * @param P1 name of player1 as string
	 * @param P2 name of player2 as string
	 * @param round which round of the game
	 * @param cardsRemaining remaining number of cards in each player's hands
	 * @param scoreP1 score of player1 as int
	 * @param scoreP2 score of player2 as int
	 */
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
	
	/**
	 * Private function that updates the information of the game with given gameID.
	 * To be used by syncDB.
	 * 
	 * @param gameID id of the game
	 * @param round which round of the game
	 * @param cardsRemaining remaining number of cards in each player's hands
	 * @param scoreP1 score of player1 as int
	 * @param scoreP2 score of player2 as int
	 */
	private void update(int gameID, int round, int cardsRemaining, int scoreP1, int scoreP2) {
		collection.updateOne(Filters.eq("gameID", gameID), 
				Updates.combine(Updates.set("round", round),
								Updates.set("RemainingCards", cardsRemaining),
								Updates.set("Player1Score", scoreP1),
								Updates.set("Player2Score", scoreP2)));
	}

	/**
	 * Deletes the information in the database associated with the given gameID.
	 * 
	 * @param gameID id of the game
	 */
	public void delete(int gameID) {
		collection.deleteOne(Filters.eq("gameID", gameID));
	}
	
	/**
	 * Clears all contents of the database
	 */
	public void deleteAll() {
		BasicDBObject document = new BasicDBObject();
		collection.deleteMany(document);
	}
	
	
	/**
	 * returns the name of Player1 as a string in the game with given gameID.
	 * 
	 * Outdated way to get name of player, see getGameInfo function for the new way.
	 * 
	 * @param gameID gameID of the game you want to get player1Name from
	 * @return string of player1Name
	 */
	@SuppressWarnings("unused")
	private String getPlayer1Name(int gameID) {
		FindIterable<Document> findIterable = collection.find(Filters.eq("gameID", gameID));
		return (String)findIterable.iterator().next().get("Player1");
	}
	
	/**
	 * Prints the contents of the database, including internal id of the contents.
	 */
	public void printDatabaseContents() {
		FindIterable<Document> iterDoc = collection.find();
		Iterator<Document> it = iterDoc.iterator(); 
		while (it.hasNext()) {  
	         System.out.println(it.next());  
	    }
	}
	
	/**
	 * Returns information of the game as Document.
	 * Use .get("field") to get individual fields.
	 * 
	 * @param gameID id of the game
	 * @return game info as Document
	 */
	public Document getGameInfo(int gameID) {
		FindIterable<Document> findIterable = collection.find(Filters.eq("gameID", gameID));
		return findIterable.iterator().next();
	}
	
	//Call this function every 30 seconds
	/**
	 * Call this function every 30 seconds or when the game state changes.
	 * Updates database according to given parameters and returns string with the details of synchronization.
	 * 
	 * @param gameID id of the game
	 * @param round which round of the game
	 * @param cardsRemaining remaining number of cards in each player's hands
	 * @param scoreP1 score of player1 as int
	 * @param scoreP2 score of player2 as int
	 * @return string with details of synchronization
	 */
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

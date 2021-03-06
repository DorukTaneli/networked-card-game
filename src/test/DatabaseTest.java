package test;

import org.bson.Document;

import database.MongoDB;

public class DatabaseTest {

	public static void main(String[] args) {
		
		MongoDB db = new MongoDB();
		
		db.insert(1, "Mike", "George", 5, 21, 10, 15);
		System.out.println("Inserted game1 info");
		db.printDatabaseContents();
		
		String s = db.syncDB(1, 5, 21, 10, 15);
		System.out.println(s);
		db.printDatabaseContents();
		
		String s2 = db.syncDB(1, 7, 19, 20, 25);
		System.out.println(s2);
		db.printDatabaseContents();
		
		Document gameInfo = db.getGameInfo(1);
		int rc = (int) gameInfo.get("RemainingCards");
		System.out.println("Remaining Cards: " + rc);
		int p1score = (int) gameInfo.get("Player1Score");
		System.out.println("Player 1 score: " + p1score);
		
		db.deleteAll();
		System.out.println("Deleted all game info");		
		db.printDatabaseContents();
	}

}

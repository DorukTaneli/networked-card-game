package test;

import database.MongoDB;

public class DatabaseTest {

	public static void main(String[] args) {
		
		MongoDB db = new MongoDB();
		
		//db.insert("Mike", "George", 5, 21, 10, 15);
		
		System.out.println(db.getPlayer1Name());
		
		db.printDatabaseContents();
	}

}

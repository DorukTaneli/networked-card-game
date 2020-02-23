package test;

import database.MongoDB;

public class DatabaseTest {

	public static void main(String[] args) {
		
		MongoDB db = new MongoDB();
		
		//db.insert(1, "Mike", "George", 5, 21, 10, 15);
		
		//System.out.println(db.getPlayer1Name());
		
		String s = db.syncDB(1, 7, 19, 20, 25);
		
		System.out.println(s);
		
		//db.deleteAll();
		
		//db.dropCollection();
				
		db.printDatabaseContents();
		
	}

}

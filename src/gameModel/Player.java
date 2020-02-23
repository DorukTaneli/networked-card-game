package gameModel;

public class Player {
	
	public static Deck deck;
	private String name;
	private int score;
	public String serverAddress;
	public int serverPort;
	
	/**
	 * Creates a player with the given name, server port and server address. Server port
	 * and the server address will be used for TCP connection. Initially the player score
	 * is set to 0
	 * @param serverAddress for the socket connection
	 * @param serverPort for the socket connection
	 * @param name the ID of the player object
	 */
	public Player(String serverAddress, int serverPort, String name){
		this.serverAddress = serverAddress;
		this.serverPort = serverPort;
		this.name = name;
		this.score = 0;
	}
	
	/**
	 * Assigns the deck field of the player object as the given Deck object
	 * @param d a Deck instance 
	 */
	public static void giveDeck(Deck d){
		deck = d;
	}
	
	/**
	 * Increments the player score by one
	 */
	public void giveScore(){
		score += score;
	}
	
	/**
	 * 
	 * @return returns the current player score
	 */
	public int getScore(){
		return this.score;
	}
	
	/**
	 * 
	 * @return returns the player name
	 */
	public String getName(){
		return this.name;
	}

}

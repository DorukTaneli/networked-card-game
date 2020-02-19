package gameModel;

public class Player {
	
	public static Deck deck;
	private String name;
	private int score;
	public String serverAddress;
	public int serverPort;
	
	public Player(String serverAddress, int serverPort, String name){
		this.serverAddress = serverAddress;
		this.serverPort = serverPort;
		this.name = name;
		this.score = 0;
	}
	
	public static void giveDeck(Deck d){
		deck = d;
	}
	
	public void giveScore(){
		score += score;
	}
	
	public int getScore(){
		return this.score;
	}
	
	public String getName(){
		return this.name;
	}

}

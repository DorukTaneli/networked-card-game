package gameModel;

import java.util.*;

import com.sun.org.apache.bcel.internal.generic.ReturnaddressType;

public class Deck {

	private static ArrayList<Integer> deck = new ArrayList<Integer>();
	
	public Deck() {
		for(int i = 1; i < 53; i++){
			deck.add(i);
		}
	}
	
	public static void shuffle(){
		Collections.shuffle(deck);	
	}
	
	public static int drawCard()
	{
		if(!deck.isEmpty()){
			int cardDrawn = deck.get(deckSize()-1);
			deck.remove(deckSize()-1);
			return cardDrawn;
		}
		else
			//TODO fix the case for deck being empty
			return 0;
		
		
	}
	
	public static int deckSize()
	{
		return deck.size();
	}



}

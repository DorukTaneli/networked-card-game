package gameModel;

import java.util.*;

public class Deck {

	public static ArrayList<Integer> deck = new ArrayList<Integer>();
	
	/**
	 * Creates the Deck object by filling out the ArrayList deck field of the instance with numbers from
	 * 1 to 52, indicating a whole card deck.
	 */
	public Deck() {
		for(int i = 1; i < 53; i++){
			deck.add(i);
		}
	}
	
	/**
	 * Shuffles the contents in the ArrayList deck 
	 */
	public static void shuffle(){
		Collections.shuffle(deck);	
	}
	
	
	/**
	 * Divides the deck into two equal sized sub decks
	 * @param main the deck to be divided
	 * @param d1 first sub deck
	 * @param d2 second sub deck
	 */
	/*
	public static void divideDeck(Deck main, Deck d1, Deck d2){
		ArrayList<Integer> temp = new ArrayList<Integer>();
		temp.addAll(26, main.deck);
		d2.deck = temp;
		d2.deck = (ArrayList<Integer>) main.deck.subList(0, 26);
				
	}
	*/
	
	/**
	 *
	 * @return  Returns the top card from the deck
	 */
	
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
	
	/**
	 * 
	 * @return returns the size of the deck indicated by the elements in the ArrayList deck
	 */
	
	public static int deckSize()
	{
		return deck.size();
	}



}

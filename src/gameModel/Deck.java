package gameModel;

import java.util.*;

public class Deck {

	public ArrayList<Integer> deck = new ArrayList<Integer>();
	
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
	 * Creates an empty deck capable of holding the specified number of cards
	 * @param i is the size of the deck
	 */
	public Deck(int i){
		deck = new ArrayList<Integer>(i);
	}
	
	/**
	 * Shuffles the contents in the ArrayList deck 
	 */
	public void shuffle(){
		Collections.shuffle(this.deck);
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
	
	public int drawCard()
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
	 * Copies the contents of deck b into deck a
	 * @param a the destination deck	
	 * @param b the source deck
	 */
	public static void copyDeck(Deck a, List<Integer> b)
	{
		if(a.deckSize() == 0)
		{
			for(int i = 0; i < b.size(); i++)
			{
				a.deck.add(b.get(i));
			}
		}
		else if(a.deckSize() != b.size())
		{
			System.out.println("Decks are not the same size");
		}
		else{
			for(int i = 0; i < a.deckSize(); i++)
			{
				a.deck.set(i, b.get(i));
			}
		}
		
	}
	
	/**
	 * 
	 * @return returns the size of the deck indicated by the elements in the ArrayList deck
	 */
	
	public int deckSize()
	{
		return deck.size();
	}



}

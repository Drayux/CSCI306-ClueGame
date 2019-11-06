package clueGame;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

public abstract class Player {
	
	private String playerName;
	protected int row;
	protected int column;
	private Color color;
	
	protected Set<Card> hand = new HashSet<Card>();
	
	// All players will "know" the same cards to be disproven
	// As this impacts their decision making, this is located here and not in Board.java
	protected static Set<Card> disprovenCards = new HashSet<Card>();
	
	public Player(String playerName, int row, int column, Color color) {
		this.playerName = playerName;
		this.row = row;
		this.column = column;
		this.color = color;
	}
	
	public Card disproveSuggestion(Solution suggestion) {
		Set<Card> matches = new HashSet<Card>();
		
		// Same pointer is used, so no equals function is necessary
		if (hand.contains(suggestion.getPerson())) matches.add(suggestion.getPerson());
		if (hand.contains(suggestion.getRoom())) matches.add(suggestion.getRoom());
		if (hand.contains(suggestion.getWeapon())) matches.add(suggestion.getWeapon());

		// Note to self: This AI is intended to choose a match at random
		// The best strategy would be to disprove the suggestion with a card that has already been known
		// This situation however, should not occur with the create suggestion AI
		Card evidence = (Card) pickRandomFromSet(matches); 
		//System.out.println("Disproved: " + evidence.getName() + "\n");
		
		if (evidence != null) disprovenCards.add(evidence);
		return evidence;
	}
	
	public String getPlayerName() {
		return playerName;
	}

	public Color getPlayerColor() {
		return color;
	}
	
	public BoardCell getPlayerLocation() {
		return Board.getInstance().getCellAt(row, column);
	}
	
	public void takeCard(Card card) {
		hand.add(card);
		
	}
	
	public Set<Card> getHand() {
		return hand;
		
	}
	
	protected Object pickRandomFromSet(Set<?> set) {
		int i = 0;
		int rand = (int) Math.floor(Math.random() * set.size());

		// TODO Look into sets and determine if there is a better way to remove an item at random
		// I speculate that relying on the hashing properties of the set will likely result
		//     in similar results across the course of multiple games, per computer (and game config)
		for (Object c : set) {
			if (i == rand) return c;
			else i++;
			
		}
		return null;
	}
	
	abstract public BoardCell pickLocation(Set<BoardCell> targets);
	abstract public Solution makeAccusation();
	abstract public Solution createSuggestion();

}
package clueGame;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

public abstract class Player {
	
	private String playerName;
	private int row;
	private int column;
	private Color color;
	
	private Set<Card> hand = new HashSet<Card>();
	
	// All players will "know" the same cards to be disproven
	// As this impacts their decision making, this is located here and not in Board.java
	private static Set<Card> disprovenCards = new HashSet<Card>();
	
	public Player(String playerName, int row, int column, Color color) {
		this.playerName = playerName;
		this.row = row;
		this.column = column;
		this.color = color;
	}
	
	public Card disproveSuggestion(Solution suggestion) {
		return null;
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
	
	abstract public BoardCell pickLocation(Set<BoardCell> targets);
	abstract public void makeAccusation();
	abstract public void createSuggestion();

}
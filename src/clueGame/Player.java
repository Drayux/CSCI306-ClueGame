package clueGame;

import java.awt.Color;

public abstract class Player {
	
	private String playerName;
	private int row;
	private int column;
	private Color color;
	
	
	
	public Player(String playerName, int row, int column, Color color) {
		this.playerName = playerName;
		this.row = row;
		this.column = column;
		this.color = color;
	}
	
	public Card disproveSuggestion( Solution suggestion) {
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

}

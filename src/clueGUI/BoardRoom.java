package clueGUI;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Player;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *	This class is used to quantify rooms as sets of board cells
 *	From this, we can easily place players in the room, and color
 *		the entire room a certain color if it is a valid location.
 */

public class BoardRoom {

	private char initial;
	private String label = null;

	private double centerX;
	private double centerY;

	private Map<BoardCell, Player> roomCells = null;

	public BoardRoom(char c, String l) {
		initial = c;
		label = l;

		roomCells = new HashMap<BoardCell, Player>();
		defineRoom(Board.getInstance());

	}

	//	public void defineRoom(Set<BoardCell> cells) {
	//		for (BoardCell cell : cells) roomCells.put(cell, null);
	//		
	//	}

	// Helper function
	public void defineRoom(Board board) {
		for (int j = 0; j < board.getNumColumns(); j++) {
			for (int i = 0; i < board.getNumRows(); i++) {
				if (board.getCellAt(i, j).getInitial() == initial) roomCells.put(board.getCellAt(i, j), null);

			}
		}
	}

	public void putPlayer(Player player) {
		// Create a set of valid locations
		Set<BoardCell> locations = new HashSet<BoardCell>();

		for (BoardCell cell : roomCells.keySet()) {
			if (roomCells.get(cell) == null && !cell.isDoorway()) locations.add(cell);

		}

		int rand = (int) Math.random() * locations.size(); 

		for (BoardCell cell : locations) {
			if (rand == 0) {
				roomCells.put(cell, player);
				
				return;
			}
			else rand--;

		}
	}

	//	public void removePlayer(Player player) {
	//		for (BoardCell cell : roomCells.keySet()) {
	//			if (roomCells.get(cell) == player) {
	//				roomCells.put(cell, null);
	//				
	//				return;
	//			}
	//		}
	//	}

	public void clearPlayers() {
		for (BoardCell cell : roomCells.keySet()) {
			if (roomCells.get(cell) != null) roomCells.put(cell, null);

		}
	}

	public void drawSelected(Graphics g, int cellSize, Color color) {
		g.setColor(color);
		
		for (BoardCell cell : roomCells.keySet()) g.fillRect(cell.getColumn() * cellSize, cell.getRow() * cellSize, cellSize, cellSize);
	}
	
	public void drawPlayers(Graphics g, int cellSize, Color borderColor) {
		for (BoardCell cell : roomCells.keySet()) {
			Player player = roomCells.get(cell);

			if (player != null) {
				g.setColor(player.getColor());
				g.fillOval(cell.getColumn() * cellSize + 1, cell.getRow() * cellSize + 1, cellSize - 2, cellSize - 2);
	
				g.setColor(borderColor);
				g.drawOval(cell.getColumn() * cellSize + 1, cell.getRow() * cellSize + 1, cellSize - 2, cellSize - 2);

			}
		}
	}

	public void defineRoomCenter(Dimension center) {
		this.centerX = center.getWidth();
		this.centerY = center.getHeight();

	}

	public double getCenterX() { return centerX; }
	public double getCenterY() { return centerY; }
	public String getLabel() { return label; }
	public char getInitial() { return initial; }

}

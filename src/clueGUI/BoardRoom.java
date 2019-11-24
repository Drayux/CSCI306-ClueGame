package clueGUI;

import clueGame.BoardCell;
import clueGame.Player;

import java.awt.Dimension;
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
	
	private float centerX;
	private float centerY;
	
	private Map<BoardCell, Player> roomCells = null;
	
	public BoardRoom(char c, String l) {
		initial = c;
		label = l;
		
		roomCells = new HashMap<BoardCell, Player>();
		
	}
	
	public void defineRoom(Set<BoardCell> cells) {
		for (BoardCell cell : cells) roomCells.put(cell, null);
		
	}
	
	public void putPlayer(Player player) {
		// Create a set of valid locations
		Set<BoardCell> locations = new HashSet<BoardCell>();
		
		for (BoardCell cell : roomCells.keySet()) {
			if (roomCells.get(cell) == null && !cell.isDoorway()) locations.add(cell);
			
		}
		
		int rand = (int) Math.random() * locations.size(); 
		
		for (BoardCell cell : locations) {
			if (rand == 0) roomCells.put(cell, player);
			else rand--;
			
		}
	}
	
	public void removePlayer(Player player) {
		for (BoardCell cell : roomCells.keySet()) {
			if (roomCells.get(cell) == player) {
				roomCells.put(cell, null);
				
				return;
			}
		}
	}
}

package clueGUI;

import clueGame.BoardCell;
import java.util.HashSet;
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
	
	private Set<BoardCell> roomCells = null;
	
	public BoardRoom(char c, String l) {
		
		
	}
	
	public void defineRoom(Set<BoardCell> cells) {
		this.roomCells = cells;
		
	}
	
}

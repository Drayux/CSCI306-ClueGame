package clueGame;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

public class ComputerPlayer extends Player {

	private Set<Character> visitedRooms;
	
	public ComputerPlayer(String playerName, int row, int column, Color color) {
		super(playerName, row, column, color);
		visitedRooms = new HashSet<Character>();
		
	}

	public BoardCell pickLocation(Set<BoardCell> targets) {
		if (targets.size() == 0) return null;   // If targets is empty, no need to process everything
		
		// Split up the targets by type
		Set<BoardCell> newRooms = new HashSet<BoardCell>();
		Set<BoardCell> oldRooms = new HashSet<BoardCell>();
		Set<BoardCell> cells = new HashSet<BoardCell>();
		
		double rand = Math.random();
		
		for (BoardCell location : targets) {
			if (location.isWalkway()) cells.add(location);
			else if (visitedRooms.contains(location.getInitial())) oldRooms.add(location);
			else newRooms.add(location);
			
		}
		
		// No rooms are nearby
		if (newRooms.size() + oldRooms.size() == 0) {
			
			
		}
		
		return null;
	}
	
	public void makeAccusation() {
		
	}
	
	public void createSuggestions() {
		
	}
}

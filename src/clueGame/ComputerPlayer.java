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

	@Override
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
			int i = 0;
			int randInt = (int) Math.floor(rand * cells.size());

			// TODO Look into sets and determine if there is a better way to remove an item at random
			// I speculate that relying on the hashing properties of the set will likely result
			//     in similar results across the course of multiple games, per computer (and game config)
			for (BoardCell c : cells) {
				if (i == randInt) return c;
				else i++;
				
			}
		}
		
		// Unvisted room
		else if (newRooms.size() > 0) {
			int i = 0;
			int randInt = (int) Math.floor(rand * newRooms.size());

			// TODO Look into sets and determine if there is a better way to remove an item at random
			// I speculate that relying on the hashing properties of the set will likely result
			//     in similar results across the course of multiple games, per computer (and game config)
			for (BoardCell c : newRooms) {
				if (i == randInt) return c;
				else i++;
				
			}
		}
		
		else {
			// Create a combined set (this is the last option, so no need to allocate new memory
			cells.addAll(oldRooms);
			cells.addAll(newRooms);
			
			int i = 0;
			int randInt = (int) Math.floor(rand * cells.size());

			// TODO Look into sets and determine if there is a better way to remove an item at random
			// I speculate that relying on the hashing properties of the set will likely result
			//     in similar results across the course of multiple games, per computer (and game config)
			for (BoardCell c : cells) {
				if (i == randInt) return c;
				else i++;
				
			}
		}
		
		// If all else fails, return a null pointer
		return null;
	}

	@Override
	public void makeAccusation() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createSuggestion() {
		
		
	}
}

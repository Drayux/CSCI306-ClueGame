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
		
		for (BoardCell location : targets) {
			if (location.isWalkway()) cells.add(location);
			else if (visitedRooms.contains(location.getInitial())) oldRooms.add(location);
			else newRooms.add(location);
			
		}
		
		// No rooms are nearby
		if (newRooms.size() + oldRooms.size() == 0) return (BoardCell) pickRandomFromSet(cells);
		
		// Unvisted room
		else if (newRooms.size() > 0) {
			BoardCell room = (BoardCell) pickRandomFromSet(newRooms);
			visitedRooms.add(room.getInitial());
			
			return room;
		}
		
		else {
			// Create a combined set (this is the last option, so no need to allocate new memory
			cells.addAll(oldRooms);
			cells.addAll(newRooms);
			
			BoardCell room = (BoardCell) pickRandomFromSet(cells);
			if (room.isDoorway()) visitedRooms.add(room.getInitial());
			
			return room;
		}
	}

	@Override
	public Solution makeAccusation() {
		// TODO Auto-generated method stub
		return null;
		
	}

	@Override
	public Solution createSuggestion() {
		Solution suggestion = new Solution();
		Set<Card> personCards = new HashSet<Card>();
		Set<Card> weaponCards = new HashSet<Card>();
		
		// Prepare the sets
		for (Card c : Board.getInstance().getGameCards()) {
			if (hand.contains(c) || disprovenCards.contains(c)) continue;
			
			if (c.getType() == CardType.PERSON) personCards.add(c);
			else if (c.getType() == CardType.WEAPON) weaponCards.add(c);
			
		}
		
		//System.out.println("Person cards:");
		//for (Card c : personCards) System.out.println(c.getName());
		//System.out.println("\nWeapon cards:");
		//for (Card c : weaponCards) System.out.println(c.getName());
		//System.out.println();
		
		suggestion.setRoom(Board.getInstance().getAssociatedRoomCard(row, column));
		suggestion.setPerson((Card) pickRandomFromSet(personCards));
		suggestion.setWeapon((Card) pickRandomFromSet(weaponCards));
		
		return suggestion;
	}
}

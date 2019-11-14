package clueGame;

import java.awt.Color;
import java.util.Set;

public class HumanPlayer extends Player {

	public HumanPlayer(String playerName, int row, int column, Color color) {
		super(playerName, row, column, color);
		// TODO Auto-generated constructor stub
	}

	@Override
	public BoardCell pickLocation(Set<BoardCell> targets) {
		for (BoardCell cell : targets) cell.isSelected = true;
		Board.getInstance().getGUI().repaint();
		
		// Wait for user to select a cell
		
		for (BoardCell cell : targets) cell.isSelected = false;
		return Board.getInstance().getCellAt(row, column);
	}

	@Override
	public Solution makeAccusation() {
		// TODO Auto-generated method stub
		return null;
		
	}

	@Override
	public Solution createSuggestion() {
		System.out.println("TODO Finish human player suggestion");

		return null;		
	}

}

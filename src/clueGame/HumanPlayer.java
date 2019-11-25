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
		//for (BoardCell cell : targets) cell.isSelected = true;
		//Board.getInstance().getGUI().repaint();
		
		//WaitThread test = new WaitThread();
		//test.start();
		
		//synchronized(WaitThread.monitor) { WaitThread.monitor.notify(); }
		
		/*try {
			test.join();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
			
		}*/
		
		//for (BoardCell cell : targets) cell.isSelected = false;  // This is repainted again as soon as the function returns
		//return Board.getInstance().getCellAt(row, column);
		
		/*for (BoardCell cell : targets) {
			if (cell == Board.getInstance().getGUI().getBoardDisplay().getCellAtClick()) return cell;
			
		}*/
		
		return null;
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

package clueGame;

import java.util.Set;

public class Main {

	private static Board board;
	
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		
		// set the file names to use my config files
		board.setConfigFiles("CTest_ClueLayout.csv", "CTest_ClueLegend.txt");	
		
		// Initialize will load BOTH config files 
		board.initialize();
		
	}
	
	public static void main(String[] args) {
		System.out.println("Hello");
		
		setUp();
		
		board.calcTargets(21, 7, 4);
		Set<BoardCell> targets= board.getTargets();
		System.out.println();
		for (BoardCell cell : targets) System.out.println("Cell row: " + cell.getRow() + ", Cell column: " + cell.getColumn());
		
	}
	
}

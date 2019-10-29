package tests;

import clueGame.Board;
import clueGame.ConfigType;

import org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

public class GameSetupTests {
	private static Board board;
	
	@BeforeClass
	public static void setUp() {
		board = Board.getInstance();
		
		board.setConfig(ConfigType.BOARD, "board_layout.csv");
		board.setConfig(ConfigType.BOARD, "board_layout.csv");
		board.setConfig(ConfigType.BOARD, "board_layout.csv");
		board.setConfig(ConfigType.BOARD, "board_layout.csv");
		
		board.initialize();
		
	}

	//This test tests ...
	@Test
	public void testPlayers() {
		
		
	}
	
}

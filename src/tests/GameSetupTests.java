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
		
		board.setConfig(ConfigType.BOARD, "config/board.csv");
		board.setConfig(ConfigType.LEGEND, "config/legend.txt");
		board.setConfig(ConfigType.PLAYER, "config/players.txt");
		board.setConfig(ConfigType.WEAPON, "config/weapons.txt");
		
		board.initialize();
		
	}

	// This test tests that the player config file has been successfully loaded (and that the values are correct)
	// Checks for name, color, human vs computer, starting location
	@Test
	public void testPlayerConfig() {
		// Name tests
		
		// Color tests
		
		// Human vs computer tests
		
		// Starting location tests
		
	}
	
}

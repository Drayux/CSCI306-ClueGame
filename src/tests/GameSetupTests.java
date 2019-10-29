package tests;

import clueGame.Board;
import clueGame.ComputerPlayer;
import clueGame.ConfigType;
import clueGame.HumanPlayer;
import clueGame.Player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.instanceOf;
import java.awt.Color;

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
		
		Player firstPlayer = board.getPlayer(0);
		Player secondPlayer = board.getPlayer(1);
		Player thirdPlayer = board.getPlayer(2);
		Player fourthPlayer = board.getPlayer(3);
		Player fifthPlayer = board.getPlayer(4); // This is the human player
		Player sixthPlayer = board.getPlayer(5);
		
		// Name tests ensuring the names of our players meet the text file
		assertEquals(firstPlayer.getPlayerName(), "Colonel Mustard");
		assertEquals(secondPlayer.getPlayerName(), "Miss Scarlet");
		assertEquals(thirdPlayer.getPlayerName(), "Mr. Green");
		assertEquals(fourthPlayer.getPlayerName(), "Mrs. Peacock");
		assertEquals(fifthPlayer.getPlayerName(), "Mrs. White");
		assertEquals(sixthPlayer.getPlayerName(), "Professor Plum");
		
		
		// Color tests ensuring the color of our players meet the text file
		assertEquals(firstPlayer.getPlayerColor(), new Color(244, 230, 40));
		assertEquals(secondPlayer.getPlayerColor(), new Color(146, 65, 84));
		assertEquals(thirdPlayer.getPlayerColor(), new Color (156, 244, 40));
		assertEquals(fourthPlayer.getPlayerColor(), new Color (0, 255, 255));
		assertEquals(fifthPlayer.getPlayerColor(), new Color (255, 255, 255));
		assertEquals(sixthPlayer.getPlayerColor(), new Color (221, 160, 221));
		
		// Human vs computer tests 
		assertThat(firstPlayer, instanceOf(ComputerPlayer.class));
		assertThat(secondPlayer, instanceOf(ComputerPlayer.class));
		assertThat(thirdPlayer, instanceOf(ComputerPlayer.class));
		assertThat(fourthPlayer, instanceOf(ComputerPlayer.class));
		assertThat(fifthPlayer, instanceOf(HumanPlayer.class)); 
		assertThat(sixthPlayer, instanceOf(ComputerPlayer.class));
		
		
		// Starting location tests
		assertEquals(firstPlayer.getPlayerLocation(), board.getCellAt(0, 7));
		assertEquals(secondPlayer.getPlayerLocation(), board.getCellAt(0, 15));
		assertEquals(thirdPlayer.getPlayerLocation(), board.getCellAt(6, 22));
		assertEquals(fourthPlayer.getPlayerLocation(), board.getCellAt(19, 16));
		assertEquals(fifthPlayer.getPlayerLocation(), board.getCellAt(19, 10));
		assertEquals(sixthPlayer.getPlayerLocation(), board.getCellAt(19, 1));
		
	}
	
}

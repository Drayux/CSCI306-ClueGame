package tests;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.ConfigType;
import clueGame.HumanPlayer;
import clueGame.Player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.instanceOf;
import java.awt.Color;
import java.util.Set;

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
	
	// This test tests that the deck of cards has been successfully created with all the cards defined in our config file
	@Test
	public void testDeckOfCards() {
		Set<Card> deck = board.getGameDeck();
		
		assertEquals(deck.size(), 18);
		
		// Because the cards *only* exist in the deck, we need to be sure that the contents of every card it the deck
		// matches that of those we know are in our text files, nothing more, nothing less.
		
		// This checks every card in the deck across every card (initialed with its type and name) and compares
		// their member variables for equivalence.
		for (Card c : deck) {
			assertTrue(
					c.equals(new Card(CardType.ROOM, "Asylum")) ||
					c.equals(new Card(CardType.ROOM, "Kitchen")) ||
					c.equals(new Card(CardType.ROOM, "Butcher's Bedroom")) ||
					c.equals(new Card(CardType.ROOM, "Hotbox")) ||
					c.equals(new Card(CardType.ROOM, "Entry")) ||
					c.equals(new Card(CardType.ROOM, "Studio")) ||
					c.equals(new Card(CardType.ROOM, "Dining Room")) ||
					c.equals(new Card(CardType.ROOM, "Arena")) ||
					c.equals(new Card(CardType.ROOM, "Underwear Repair")) ||
					
					c.equals(new Card(CardType.PERSON, "Colonel Mustard")) ||
					c.equals(new Card(CardType.PERSON, "Miss Scarlet")) ||
					c.equals(new Card(CardType.PERSON, "Mr. Green")) ||
					c.equals(new Card(CardType.PERSON, "Mrs. Peacock")) ||
					c.equals(new Card(CardType.PERSON, "Mrs. White")) ||
					c.equals(new Card(CardType.PERSON, "Professor Plum")) ||
					
					c.equals(new Card(CardType.WEAPON, "Light Saber")) ||
					c.equals(new Card(CardType.WEAPON, "Plasma Rifle")) ||
					c.equals(new Card(CardType.WEAPON, "Throwing Hatchets")) ||
					c.equals(new Card(CardType.WEAPON, "Rusty Knife")) ||
					c.equals(new Card(CardType.WEAPON, "SCAR 17-H")) ||
					c.equals(new Card(CardType.WEAPON, "Chain Saw")));
			
		}
		
	}
	
	@Test
	public void testDealingOfCards() {
		Player firstPlayer = board.getPlayer(0);
		Player secondPlayer = board.getPlayer(1);
		Player thirdPlayer = board.getPlayer(2);
		Player fourthPlayer = board.getPlayer(3);
		Player fifthPlayer = board.getPlayer(4); // This is the human player
		Player sixthPlayer = board.getPlayer(5);
		
		assertTrue(firstPlayer.getHand().size() == 3);
		assertTrue(secondPlayer.getHand().size() == 3);
		assertTrue(thirdPlayer.getHand().size() == 3);
		assertTrue(fourthPlayer.getHand().size() == 3);
		assertTrue(fifthPlayer.getHand().size() == 3);
		assertTrue(sixthPlayer.getHand().size() == 3);
		
	}
	
	
	
}

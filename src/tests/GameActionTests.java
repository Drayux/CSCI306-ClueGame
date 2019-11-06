package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.ConfigType;
import clueGame.Solution;

public class GameActionTests {	

	private static Board board;
	
	private static Card room1 = new Card(CardType.ROOM, "Asylum");
	private static Card room2 = new Card(CardType.ROOM, "Kitchen");
	private static Card room3 = new Card(CardType.ROOM, "Bedroom");
	private static Card room4 = new Card(CardType.ROOM, "Hotbox");
	private static Card room5 = new Card(CardType.ROOM, "Entry");
	private static Card room6 = new Card(CardType.ROOM, "Studio");
	private static Card room7 = new Card(CardType.ROOM, "Dining");
	private static Card room8 = new Card(CardType.ROOM, "Arena");
	private static Card room9 = new Card(CardType.ROOM, "Repair");
	
	private static Card person1 = new Card(CardType.PERSON, "Mustard");
	private static Card person2 = new Card(CardType.PERSON, "Scarlet");
	private static Card person3 = new Card(CardType.PERSON, "Green");
	private static Card person4 = new Card(CardType.PERSON, "Peacock");
	private static Card person5 = new Card(CardType.PERSON, "White");
	private static Card person6 = new Card(CardType.PERSON, "Plum");
	
	private static Card weapon1 = new Card(CardType.WEAPON, "Saber");
	private static Card weapon2 = new Card(CardType.WEAPON, "Rifle");
	private static Card weapon3 = new Card(CardType.WEAPON, "Hatchet");
	private static Card weapon4 = new Card(CardType.WEAPON, "Knife");
	private static Card weapon5 = new Card(CardType.WEAPON, "SCAR");
	private static Card weapon6 = new Card(CardType.WEAPON, "Saw");

	@BeforeClass
	public static void setUp() {
		board = Board.getInstance();


		board.setConfig(ConfigType.BOARD, "config/board.csv");
		board.setConfig(ConfigType.LEGEND, "config/legend.txt");
		board.setConfig(ConfigType.PLAYER, "config/players.txt");
		board.setConfig(ConfigType.WEAPON, "config/weapons.txt");

		board.initialize();

	}

	// THESE TESTS TEST THAT THE COMPUTER AI CORRECTLY SELECTS ROOMS ACCORDING TO THE PROVIDED ALGORITHM
	@Test
	public void targetLocationTests() {

		ComputerPlayer comp1 = new ComputerPlayer("TEST", 0, 0, null); //new ComputerPlayer();
		BoardCell testCell = null;
		Set<BoardCell> targets = null;
		double random = Math.random();

		// TEST WHEN ONLY WALKWAYS IN TARGETS
		//row 3 col 15 2 step
		Map <BoardCell, Integer> countMap = new HashMap<BoardCell, Integer>();

		Board.getInstance().calcTargets(board.getCellAt(3, 15), 2);
		targets = Board.getInstance().getTargets();

		for(int i = 0; i < 500; i ++ ) {
			//comp1 = new ComputerPlayer("TEST", 0, 0, null);

			testCell = comp1.pickLocation(targets);
			countMap.putIfAbsent(testCell, 0);

			int count = countMap.get(testCell);
			count++;

			countMap.put(testCell, count);

		}


		for(int count : countMap.values() ) {

			assertTrue( count > 40 && count < 85);

		}

		// TEST WHEN TWO ROOMS ARE IN TARGETS (both initially unvisited)
		// row 5 , col 3 4 step
		//run2 select other room 

		Board.getInstance().calcTargets(board.getCellAt(5, 3), 4);
		targets = Board.getInstance().getTargets();
		countMap = new HashMap<BoardCell, Integer>();

		for(int i = 0; i < 500; i++ ) {
			comp1 = new ComputerPlayer("TEST", 0, 0, null);
			
			// First room should be selected at random
			testCell = comp1.pickLocation(targets);
			countMap.putIfAbsent(testCell, 0);

			int count = countMap.get(testCell);
			count++;
			
			countMap.put(testCell, count);

			// Second room is guaranteed to be the other room
			testCell = comp1.pickLocation(targets);
			countMap.putIfAbsent(testCell, 0);

			count = countMap.get(testCell);
			count++;
			
			countMap.put(testCell, count);

		}

		assertEquals(countMap.size(), 2);
		for(int count : countMap.values() ) {
			assertTrue(count == 500);

		}

		// TEST WHEN ALL ROOMS IN TARGETS ARE VISITED
		// row 5 , col 3 4 step
		//run 1 select room

		countMap = new HashMap<BoardCell, Integer>();
		// Intentionally don't call new on the computer player
		
		for(int i = 0; i < 500; i ++ ) {
			//comp1 = new ComputerPlayer("TEST", 0, 0, null);

			testCell = comp1.pickLocation(targets);
			countMap.putIfAbsent(testCell, 0);

			int count = countMap.get(testCell);
			count++;

			countMap.put(testCell, count);

		}


		for(int count : countMap.values()) {

			assertTrue( count > 58 && count < 109);

		}
	}

	// THESE TESTS TEST THAT THE EQUALS FUNCTION WORKS FOR SOLUTIONS SUCH THAT THE GAME MAY
	// ACCURATELY CHECK AN ACCUSATION
	@Test
	public void checkingAccusationTest() {
		// "Official" solution
		Solution testSolution = new Solution();   // Culprit was Miss Scarlet in the Hotbox, with a throwing hatchet
		testSolution.setPerson(person2);
		testSolution.setRoom(room4);
		testSolution.setWeapon(weapon3);

		// Test solutions
		Solution testAccusationCorrect = new Solution();
		testAccusationCorrect.setPerson(person2);
		testAccusationCorrect.setRoom(room4);
		testAccusationCorrect.setWeapon(weapon3);
		
		Solution testAccusationWrongPerson = new Solution();  // Colonel Mustard
		testAccusationWrongPerson.setPerson(person1);
		testAccusationWrongPerson.setRoom(room4);
		testAccusationWrongPerson.setWeapon(weapon3);
		
		Solution testAccusationWrongWeapon = new Solution();  // Chain Saw
		testAccusationWrongWeapon.setPerson(person2);
		testAccusationWrongWeapon.setRoom(room4);
		testAccusationWrongWeapon.setWeapon(weapon6);
		
		Solution testAccusationWrongRoom = new Solution();    // Underwear repair room
		testAccusationWrongRoom.setPerson(person2);
		testAccusationWrongRoom.setRoom(room9);
		testAccusationWrongRoom.setWeapon(weapon3);

		// Comparison step
		assertTrue(testSolution.equals(testAccusationCorrect));
		
		assertFalse(testSolution.equals(testAccusationWrongPerson));
		assertFalse(testSolution.equals(testAccusationWrongWeapon));
		assertFalse(testSolution.equals(testAccusationWrongRoom));
		
	}

	@Test 
	public void creatingSuggestionTest() {
		ComputerPlayer testPlayer1 = new ComputerPlayer("TEST_1", 5, 1, null);
		ComputerPlayer testPlayer2 = new ComputerPlayer("TEST_2", 5, 1, null);

		Solution testSuggestion1 = new Solution();
		Solution testSuggestion2 = new Solution();
		
		// Put a few cards in the player's hand (we'll use quite a few for this simulation)
		testPlayer1.takeCard(person1);
		testPlayer1.takeCard(person2);
		testPlayer1.takeCard(person3);
		
		testPlayer1.takeCard(weapon1);
		testPlayer1.takeCard(weapon2);
		testPlayer1.takeCard(weapon3);
		
		// Now we give the other player all the other cards (this simulation does not need a solution)
		testPlayer2.takeCard(person4);
		testPlayer2.takeCard(person5);
		testPlayer2.takeCard(person6);
		
		testPlayer2.takeCard(weapon4);
		testPlayer2.takeCard(weapon5);
		testPlayer2.takeCard(weapon6);
		
		// We will be testing the decision making of testPlayer1, so let testPlayer2 disprove a few cards
		testSuggestion1.setPerson(person4);
		testSuggestion1.setRoom(room2);
		testSuggestion1.setWeapon(weapon4);
		
		testPlayer2.disproveSuggestion(testSuggestion1);
		
		// Now testPlayer1 should create a suggestion of person 5 or 6, room 2 (kitchen), and weapon 5 or 6
		testSuggestion1 = testPlayer1.createSuggestion();
		
		System.out.println(testSuggestion1.getPerson().getName() + " " + testSuggestion1.getWeapon().getName());
		
		assertTrue(testSuggestion1.getPerson() == person5 || testSuggestion1.getPerson() == person6);
		assertTrue(testSuggestion1.getWeapon() == weapon5 || testSuggestion1.getWeapon() == weapon6);
		assertTrue(testSuggestion1.getRoom() == room2);
		
		// Now have testPlayer2 disprove that suggestion
		testPlayer2.disproveSuggestion(testSuggestion1);
		
		// Now testPlayer1 is guaranteed to create a suggestion of the remaining cards
		testSuggestion2 = testPlayer1.createSuggestion();
		
		assertTrue(testSuggestion2.getPerson() == person5 || testSuggestion2.getPerson() == person6);
		assertTrue(testSuggestion2.getWeapon() == weapon5 || testSuggestion2.getWeapon() == weapon6);
		assertTrue(testSuggestion2.getRoom() == room2);
		
		// Ensures that the suggestions are different
		assertFalse(testSuggestion1.equals(testSuggestion2));
		
		// Now let's test testPlayer2
		
		
	}

	@Test 
	public void disprovingSuggestionTest () {

	}

	@Test
	public void handlingSuggestionTest() {

	}
}





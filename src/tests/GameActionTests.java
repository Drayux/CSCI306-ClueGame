package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.ComputerPlayer;
import clueGame.ConfigType;

public class GameActionTests {	

	private static Board board;
	private static Card PeacockCard;
	private static Card MustardCard;
	private static Card LightSaberCard;
	private static Card RustyKnifeCard;
	private static Card HotboxRoomCard;
	private static Card DiningRoomCard;



	@BeforeClass
	public static void setUp() {
		board = Board.getInstance();


		board.setConfig(ConfigType.BOARD, "config/board.csv");
		board.setConfig(ConfigType.LEGEND, "config/legend.txt");
		board.setConfig(ConfigType.PLAYER, "config/players.txt");
		board.setConfig(ConfigType.WEAPON, "config/weapons.txt");

		board.initialize();

	}


	@Test
	public void targetLocationTests() {

		ComputerPlayer comp1 = new ComputerPlayer("TEST", 0, 0, null);;//new ComputerPlayer();
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




	@Test
	public void checkingAccusationTest() {





	}



	@Test 
	public void disprovingSuggestionTest () {

	}

	@Test
	public void handlingSuggestionTest() {

	}

	@Test 
	public void creatingSuggestionTest() {

	}


}





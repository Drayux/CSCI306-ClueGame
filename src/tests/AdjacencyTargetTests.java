package tests;

/*
 * This program tests that adjacencies and targets are calculated correctly.
 */

import java.util.Set;

//Doing a static import allows me to write assertEquals rather than
//assertEquals
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.ConfigType;

public class AdjacencyTargetTests {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfig(ConfigType.BOARD, "config/board.csv");
		board.setConfig(ConfigType.LEGEND, "config/legend.txt");
		board.setConfig(ConfigType.PLAYER, "config/players.txt");
		board.setConfig(ConfigType.WEAPON, "config/weapons.txt");
		// Initialize will load BOTH config files 
		board.initialize();
	}

	// Ensure that player does not move around within room
	// These cells are PURPLE on the planning spreadsheet
	@Test
	public void testAdjacenciesInsideRooms()
	{
		// Test a corner
		Set<BoardCell> testList = board.getAdjList(0, 0);
		assertEquals(0, testList.size());
		
		// Test one that has walkway underneath cell 3,10
		testList = board.getAdjList(3, 10);
		assertEquals(0, testList.size());
		
		// Test one that has walkway above cell 15,5
		testList = board.getAdjList(15, 5);
		assertEquals(0, testList.size());
		
		// Test one that is in middle of room cell 16,5
		testList = board.getAdjList(16, 5);
		assertEquals(0, testList.size());
		
		// Test one beside a door cell 6,10
		testList = board.getAdjList(6, 10);
		assertEquals(0, testList.size());
		
		// Test one in a corner of room cell 19,22
		testList = board.getAdjList(19, 22);
		assertEquals(0, testList.size());
	}
	
	

	// Ensure that the adjacency list from a doorway is only the
	// walkway. NOTE: This test could be merged with door 
	// direction test. 
	// These tests are WHITE on the planning spreadsheet
	@Test
	public void testAdjacencyRoomExit()
	{
		// TEST DOORWAY RIGHT 15,8
		Set<BoardCell> testList = board.getAdjList(15, 8);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(15, 9)));
		
		// TEST DOORWAY LEFT cell 16,2
		testList = board.getAdjList(16, 2);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(16, 1)));
		
		//TEST DOORWAY DOWN cell 14,21
		testList = board.getAdjList(14, 21);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(15, 21)));
		
		//TEST DOORWAY UP cell 16,13
		testList = board.getAdjList(16, 13);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(15, 13)));
		
		//TEST DOORWAY LEFT, WHERE THERE'S A WALKWAY BELOW 3,18 / 3,17
		testList = board.getAdjList(3, 18);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(3, 17)));
		
	}
	
	// Test adjacency at entrance to rooms
	// These tests are GREEN in planning spreadsheet
	@Test
	public void testAdjacencyDoorways()
	{
		// Test beside a door direction RIGHT cell 2,6
		Set<BoardCell> testList = board.getAdjList(2, 6);
		assertTrue(testList.contains(board.getCellAt(2, 5)));
		assertTrue(testList.contains(board.getCellAt(3, 6)));
		assertTrue(testList.contains(board.getCellAt(1, 6)));
		assertTrue(testList.contains(board.getCellAt(2, 7)));
		assertEquals(4, testList.size());
		
		// Test beside a door direction DOWN 15,21
		testList = board.getAdjList(15, 21);
		assertTrue(testList.contains(board.getCellAt(14, 21)));
		assertTrue(testList.contains(board.getCellAt(15, 20)));
		assertTrue(testList.contains(board.getCellAt(15, 22)));
		assertEquals(3, testList.size());
		
		// Test beside a door direction LEFT 3,17
		testList = board.getAdjList(3, 17);
		assertTrue(testList.contains(board.getCellAt(3, 16)));
		assertTrue(testList.contains(board.getCellAt(3, 18)));
		assertTrue(testList.contains(board.getCellAt(2, 17)));
		assertTrue(testList.contains(board.getCellAt(4, 17)));
		assertEquals(4, testList.size());
		
		// Test beside a door direction UP 15,13
		testList = board.getAdjList(15, 13);
		assertTrue(testList.contains(board.getCellAt(15, 12)));
		assertTrue(testList.contains(board.getCellAt(15, 14)));
		assertTrue(testList.contains(board.getCellAt(14, 13)));
		assertTrue(testList.contains(board.getCellAt(16, 13)));
		assertEquals(4, testList.size());
	}

	// Test a variety of walkway scenarios
	// These tests are RED on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Test on top edge of board, just one walkway piece
		Set<BoardCell> testList = board.getAdjList(0, 5);
		assertTrue(testList.contains(board.getCellAt(0, 6)));
		assertEquals(1, testList.size());
		
		// Test on left edge of board, three walkway pieces
		testList = board.getAdjList(13, 0);
		assertTrue(testList.contains(board.getCellAt(12, 0)));
		assertTrue(testList.contains(board.getCellAt(13, 1)));
		assertTrue(testList.contains(board.getCellAt(14, 0)));
		assertEquals(3, testList.size());

		// Test between two rooms, walkways right and left
		testList = board.getAdjList(12, 5);
		assertTrue(testList.contains(board.getCellAt(12, 6)));
		assertTrue(testList.contains(board.getCellAt(12, 4)));
		assertEquals(2, testList.size());

		// Test surrounded by 4 walkways
		testList = board.getAdjList(11,9);
		assertTrue(testList.contains(board.getCellAt(11, 10)));
		assertTrue(testList.contains(board.getCellAt(11, 8)));
		assertTrue(testList.contains(board.getCellAt(10, 9)));
		assertTrue(testList.contains(board.getCellAt(12, 9)));
		assertEquals(4, testList.size());
		
		// Test on bottom edge of board, next to 1 room piece
		testList = board.getAdjList(19, 10); 
		assertTrue(testList.contains(board.getCellAt(19, 9))); 
		assertTrue(testList.contains(board.getCellAt(18, 10))); 
		assertEquals(2, testList.size());
		
		// Test on right edge of board, next to 1 room piece
		testList = board.getAdjList(7, 22);
		assertTrue(testList.contains(board.getCellAt(7, 21))); 
		assertTrue(testList.contains(board.getCellAt(6, 22)));
		assertEquals(2, testList.size());

		// Test on walkway next to  door that is not in the needed
		// direction to enter
		testList = board.getAdjList(4, 18); 
		assertTrue(testList.contains(board.getCellAt(4, 17)));
		assertTrue(testList.contains(board.getCellAt(5, 18)));
		assertEquals(2, testList.size());
	}
	
	
	// Tests of just walkways, 1 step, includes on edge of board
	// and beside room
	// Have already tested adjacency lists on all four edges, will
	// only test two edges here
	// These are Orange on the planning spreadsheet
	@Test
	public void testTargetsOneStep() {
		board.calcTargets(19, 10, 1); 
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(18, 10)));
		assertTrue(targets.contains(board.getCellAt(19, 9)));	
		
		board.calcTargets(14, 0, 1);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(14, 1)));
		assertTrue(targets.contains(board.getCellAt(13, 0)));	
		assertTrue(targets.contains(board.getCellAt(15, 0)));			
	}
	
	// Tests of just walkways, 2 steps
	// These are Orange on the planning spreadsheet
	@Test
	public void testTargetsTwoSteps() {
		board.calcTargets(19, 10, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(19, 8)));
		assertTrue(targets.contains(board.getCellAt(18, 9)));
		assertTrue(targets.contains(board.getCellAt(17, 10)));
		
		board.calcTargets(14, 0, 2);
		targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(12, 0)));
		assertTrue(targets.contains(board.getCellAt(13, 1)));	
		assertTrue(targets.contains(board.getCellAt(15, 1)));
		assertTrue(targets.contains(board.getCellAt(16, 0)));
	}
	
	// Tests of just walkways, 4 steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsFourSteps() {
		board.calcTargets(19, 10, 4); 
		Set<BoardCell> targets= board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCellAt(15, 10)));
		assertTrue(targets.contains(board.getCellAt(17, 10)));
		assertTrue(targets.contains(board.getCellAt(16, 9)));
		assertTrue(targets.contains(board.getCellAt(18, 9)));
		assertTrue(targets.contains(board.getCellAt(19, 6)));
		assertTrue(targets.contains(board.getCellAt(19, 8)));
		
		// Includes a path that doesn't have enough length
		board.calcTargets(14, 0, 4);
		targets= board.getTargets();
		assertEquals(9, targets.size());
		assertTrue(targets.contains(board.getCellAt(17, 1)));
		assertTrue(targets.contains(board.getCellAt(12, 2)));	
		assertTrue(targets.contains(board.getCellAt(16, 2)));	
		assertTrue(targets.contains(board.getCellAt(18, 0)));
		assertTrue(targets.contains(board.getCellAt(15, 1)));
		assertTrue(targets.contains(board.getCellAt(13, 1)));
		assertTrue(targets.contains(board.getCellAt(16, 0)));
		assertTrue(targets.contains(board.getCellAt(11, 0)));
		assertTrue(targets.contains(board.getCellAt(12, 0)));
		
	}	


	// Tests of just walkways with dead end, 5 steps
	// These are ORANGE on the planning spreadsheet

	@Test
	public void testTargetsFiveSteps() {
		board.calcTargets(19, 0, 5);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCellAt(17, 1)));
		assertTrue(targets.contains(board.getCellAt(19, 1)));	
		assertTrue(targets.contains(board.getCellAt(16, 2)));	
		assertTrue(targets.contains(board.getCellAt(18, 0)));	
		assertTrue(targets.contains(board.getCellAt(15, 1)));	
		assertTrue(targets.contains(board.getCellAt(16, 0)));	
		assertTrue(targets.contains(board.getCellAt(14, 0)));	
	}	

	
	// Tests of just walkways plus one door, 6 steps
	// These are ORANGE on the planning spreadsheet

	@Test
	public void testTargetsSixSteps() {
		board.calcTargets(14, 0, 6);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(11, targets.size());
		assertTrue(targets.contains(board.getCellAt(17, 1)));
		assertTrue(targets.contains(board.getCellAt(19, 1)));	
		assertTrue(targets.contains(board.getCellAt(12, 2)));	
		assertTrue(targets.contains(board.getCellAt(16, 2)));	
		assertTrue(targets.contains(board.getCellAt(18, 0)));	
		assertTrue(targets.contains(board.getCellAt(15, 1)));	
		assertTrue(targets.contains(board.getCellAt(12, 4)));
		assertTrue(targets.contains(board.getCellAt(13, 1)));
		assertTrue(targets.contains(board.getCellAt(16, 0)));
		assertTrue(targets.contains(board.getCellAt(11, 0)));
		assertTrue(targets.contains(board.getCellAt(12, 0)));
	}	
	
	// Test getting into a room
	// These are ORANGE on the planning spreadsheet

	@Test 
	public void testTargetsIntoRoom()
	{
		// One room is exactly 2 away
		board.calcTargets(15, 10, 2); 
		Set<BoardCell> targets= board.getTargets();
		assertEquals(7, targets.size());
		// directly left (can't go right 2 steps)
		assertTrue(targets.contains(board.getCellAt(14, 9))); 
		// directly up and down
		assertTrue(targets.contains(board.getCellAt(13, 10))); 
		assertTrue(targets.contains(board.getCellAt(15, 8))); 
		// one up/down, one left/right
		assertTrue(targets.contains(board.getCellAt(16, 9))); 
		assertTrue(targets.contains(board.getCellAt(14, 11))); 
		assertTrue(targets.contains(board.getCellAt(17, 10))); 
		assertTrue(targets.contains(board.getCellAt(15, 12))); 
	}
	
	// Test getting into room, doesn't require all steps
	// These are ORANGE on the planning spreadsheet
	@Test
	public void testTargetsIntoRoomShortcut() 
	{
		board.calcTargets(17, 9, 3); 
		Set<BoardCell> targets= board.getTargets();
		assertEquals(9, targets.size());
		// directly up and down
		assertTrue(targets.contains(board.getCellAt(18, 9))); 
		assertTrue(targets.contains(board.getCellAt(14, 9))); 
		// directly right (can't go left)
		assertTrue(targets.contains(board.getCellAt(16, 8)));
		// right then down
		assertTrue(targets.contains(board.getCellAt(15, 8))); 
		assertTrue(targets.contains(board.getCellAt(19, 8))); 
		// down then left/right
		assertTrue(targets.contains(board.getCellAt(16, 9))); 
		assertTrue(targets.contains(board.getCellAt(15, 10))); 
		// right then up
		assertTrue(targets.contains(board.getCellAt(17, 10))); 
		// into the rooms
		assertTrue(targets.contains(board.getCellAt(19, 10))); 
				
		
	}

	// Test getting out of a room
	// These are ORange on the planning spreadsheet
	@Test
	public void testRoomExit()
	{
		// Take one step, essentially just the adj list
		board.calcTargets(1, 8, 1);
		Set<BoardCell> targets= board.getTargets();
		// Ensure doesn't exit through the wall
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(1, 7)));
		// Take two steps
		board.calcTargets(1, 8, 2);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(0, 7)));
		assertTrue(targets.contains(board.getCellAt(2, 7)));
		assertTrue(targets.contains(board.getCellAt(1, 6)));
	}

}
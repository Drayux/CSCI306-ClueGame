package tests;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

public class BoardSetupTests {
	
	// Constants that I will use to test whether the file was loaded correctly
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 20;
	public static final int NUM_COLUMNS = 23;

	
	private static Board board;
	
	@BeforeClass
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("config/board.csv", "config/legend.txt");
		// Initialize will load BOTH configuration files 
		board.initialize();
	}
	
	

	@Test
	public void testRooms() {
		// Get the map of initial room 
		Map<Character, String> legend = board.getLegend();
		// check correct number of rooms
		assertEquals(LEGEND_SIZE , legend.size());
		// To ensure data is correctly loaded, test retrieving a few rooms 
		assertEquals("Asylum", legend.get('C'));
		assertEquals("Butcher's Bedroom", legend.get('B'));
		assertEquals("Studio", legend.get('S'));
		assertEquals("Dining Room", legend.get('D'));
		assertEquals("Kitchen", legend.get('K'));
	}
	
	
	@Test
	public void testBoardDimensions() {
		// proper number of rows and columns
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());
	}
	
	
	// Test a doorway in each direction (RIGHT/LEFT/UP/DOWN)
		@Test
		public void FourDoorDirections() {
			
			// Asylum left doorway 
			BoardCell room = board.getCellAt(3, 18);
			assertTrue(room.isDoorway());
			assertEquals(DoorDirection.LEFT, room.getDoorDirection());
			
			//Butchers bedroom right doorway
			room = board.getCellAt(1, 13);
			assertTrue(room.isDoorway());
			assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
			//Studio upper doorway
			room = board.getCellAt(6,9);
			assertTrue(room.isDoorway());
			assertEquals(DoorDirection.UP, room.getDoorDirection());
			//Dining room down doorway
			room = board.getCellAt(11, 0);
			assertTrue(room.isDoorway());
			assertEquals(DoorDirection.DOWN, room.getDoorDirection());
			//Dining room up doorway
			room = board.getCellAt(7, 1);
			assertTrue(room.isDoorway());
			assertEquals(DoorDirection.UP, room.getDoorDirection());
			//Kitchen right doorway
			room = board.getCellAt(2, 5);
			assertTrue(room.isDoorway());
			assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
			
			
			// Test that room pieces that aren't doors know it
			room = board.getCellAt(19, 12);
			assertFalse(room.isDoorway());	
			// Test that walkways are not doors
			BoardCell cell = board.getCellAt(16, 10);
			assertFalse(cell.isDoorway());		

		}
		
		
		@Test
		public void testNumberOfDoorways() 
		{
			int numDoors = 0;
			for (int row=0; row<board.getNumRows(); row++)
				for (int col=0; col<board.getNumColumns(); col++) {
					BoardCell cell = board.getCellAt(row, col);
					if (cell.isDoorway())
						numDoors++;
				}
			Assert.assertEquals(17, numDoors);
		}
		
		
		
		@Test
		public void testRoomInitials() {
			// Test first cell in room
			assertEquals('C', board.getCellAt(0, 17).getInitial());
			assertEquals('B', board.getCellAt(0, 8).getInitial());
			assertEquals('S', board.getCellAt(6, 8).getInitial());
			// Test last cell in room
			assertEquals('C', board.getCellAt(5, 22).getInitial());
			assertEquals('D', board.getCellAt(11, 6).getInitial());
			// Test a walkway
			assertEquals('W', board.getCellAt(3, 15).getInitial());
			// Test the closet
			assertEquals('X', board.getCellAt(11,14).getInitial());
		}
		
		
		
}

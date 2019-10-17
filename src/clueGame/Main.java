package clueGame;

//import java.io.FileNotFoundException;

//import java.util.Map;

public class Main {
	//JUnit was failing due to internal errors on my system, so I did the tests this way instead
	//public static final int LEGEND_SIZE = 11;
	//public static final int NUM_ROWS = 22;
	//public static final int NUM_COLUMNS = 23;

	private static Board board;
	
	/*public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("CTest_ClueLayout.csv", "CTest_ClueLegend.txt");		
		// Initialize will load BOTH config files 
		board.initialize();
	}
	
	public static void testRooms() {
		// Get the map of initial => room 
		Map<Character, String> legend = board.getLegend();
		// Ensure we read the correct number of rooms
		System.out.println(LEGEND_SIZE == legend.size());
		// To ensure data is correctly loaded, test retrieving a few rooms 
		// from the hash, including the first and last in the file and a few others
		System.out.println("Conservatory".equals(legend.get('C')));
		//System.out.println("Ballroom" == legend.get('B'));
		//System.out.println("Billiard room" == legend.get('R'));
		//System.out.println("Dining room" == legend.get('D'));
		//System.out.println("Walkway" == legend.get('W'));
	}
	
	public static void testBoardDimensions() {
		// Ensure we have the proper number of rows and columns
		System.out.println(NUM_ROWS == board.getNumRows());
		System.out.println(NUM_COLUMNS == board.getNumColumns());		
	}
	
	public static void FourDoorDirections() {
		BoardCell room = board.getCellAt(4, 3);
		
		System.out.println(room.isDoorway());
		System.out.println(DoorDirection.RIGHT == room.getDoorDirection());
		
		room = board.getCellAt(4, 8);
		
		System.out.println(room.isDoorway());
		System.out.println(DoorDirection.DOWN == room.getDoorDirection());
		
		room = board.getCellAt(15, 18);
		
		System.out.println(room.isDoorway());
		System.out.println(DoorDirection.LEFT == room.getDoorDirection());
		
		room = board.getCellAt(14, 11);
		
		System.out.println(room.isDoorway());
		System.out.println(DoorDirection.UP == room.getDoorDirection());
		
		// Test that room pieces that aren't doors know it
		room = board.getCellAt(14, 14);
		System.out.println(!(room.isDoorway()));	
		
		// Test that walkways are not doors
		BoardCell cell = board.getCellAt(0, 6);
		System.out.println(!(cell.isDoorway()));		

	}
	
	public static void testNumberOfDoorways() 
	{
		int numDoors = 0;
		for (int row=0; row<board.getNumRows(); row++)
			for (int col=0; col<board.getNumColumns(); col++) {
				BoardCell cell = board.getCellAt(row, col);
				if (cell.isDoorway())
					numDoors++;
			}
		System.out.println(16 == numDoors);
	}
	
	public static void testRoomInitials() {
		// Test first cell in room
		System.out.println('C' == board.getCellAt(0, 0).getInitial());
		System.out.println('R' == board.getCellAt(4, 8).getInitial());
		System.out.println('B' == board.getCellAt(9, 0).getInitial());
		// Test last cell in room
		System.out.println('O' == board.getCellAt(21, 22).getInitial());
		System.out.println('K' == board.getCellAt(21, 0).getInitial());
		// Test a walkway
		System.out.println('W' == board.getCellAt(0, 5).getInitial());
		// Test the closet
		System.out.println('X' == board.getCellAt(9,13).getInitial());
	}*/
	
	/*public static void testBadColumns() throws BadConfigFormatException, FileNotFoundException {
		// Note that we are using a LOCAL Board variable, because each 
		// test will load different files
		Board board = Board.getInstance();
		board.setConfigFiles("CTest_ClueLayoutBadColumns.csv", "CTest_ClueLegend.txt");
		// Instead of initialize, we call the two load functions directly.
		// This is necessary because initialize contains a try-catch. 
		board.loadRoomConfig();
		// This one should throw an exception
		board.loadBoardConfig();
	}
	
	public static void testBadRoom() throws BadConfigFormatException, FileNotFoundException {
		Board board = Board.getInstance();
		board.setConfigFiles("CTest_ClueLayoutBadRoom.csv", "CTest_ClueLegend.txt");
		board.loadRoomConfig();
		board.loadBoardConfig();
	}
	
	public static void testBadRoomFormat() throws BadConfigFormatException, FileNotFoundException {
		Board board = Board.getInstance();
		board.setConfigFiles("CTest_ClueLayout.csv", "CTest_ClueLegendBadFormat.txt");
		board.loadRoomConfig();
	}*/
	
	public static void main(String[] args) {
		System.out.println("Hello");
		
		//setUp();
		//testRooms();
		//testBoardDimensions();
		//FourDoorDirections();
		//testNumberOfDoorways();
		//testRoomInitials();
		//testBadColumns();
		//testBadRoom();
		//testBadRoomFormat();
		
	}
	
}

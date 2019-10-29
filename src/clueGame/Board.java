package clueGame;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Board {

	private int numRows = 0;     // Initialize to zero for an uninitialized board
	private int numColumns = 0;  // Initialize to zero for an uninitialized board

	private int numRooms = 0;
	private int numPlayers = 0;
	private int numWeapons = 0;
	
	private int turnCount = 0; // Will be used later (this is why getPlayers has a modulus)
	
	public static final int MAX_BOARD_SIZE = 50;
	public static final int MAX_ROOMS_COUNT = 9;
	public static final int MAX_PLAYERS_COUNT = 6;
	public static final int MAX_WEAPONS_COUNT = 6;

	private BoardCell[] board;
	private Map<Integer, String> boardLayout;
	private Map<Character, String> boardLegend;
	private Map<BoardCell, Set<BoardCell>> adjacencies;
	private Set<BoardCell> targets;

	private Player[] players = new Player[MAX_PLAYERS_COUNT];
	private Card[] deck = new Card[MAX_ROOMS_COUNT + MAX_PLAYERS_COUNT + MAX_WEAPONS_COUNT];
	private Solution gameSolution;
	
	private String layoutConfigFile;
	private String legendConfigFile;
	private String playerConfigFile;
	private String weaponConfigFile;


	//Variable used for singleton pattern
	private static final Board GAME_INSTANCE = new Board();
	// constructor is private to ensure only one can be created

	private Board() {

		adjacencies = new HashMap<>();

		boardLayout = new HashMap<>();
		boardLegend = new HashMap<>();

		board = new BoardCell[MAX_BOARD_SIZE * MAX_BOARD_SIZE];

	}

	public void initialize() {
		try {
			loadRoomConfig();
			loadBoardConfig();
			loadPlayerConfig();
			loadWeaponConfig();
			
		} catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());

		} catch (IOException e) {
			System.out.println(e.getMessage());

		}

		//Populate the grid
		for (Integer i : boardLayout.keySet()) {
			//Supplementary testing code
			//System.out.println("Board cell index: " + i);
			board[i] = new BoardCell(i / MAX_BOARD_SIZE, i % MAX_BOARD_SIZE, boardLayout.get(i));

		}

		calcAdjacencies();

		//Generate a solution
		
	}

	//Load the board layout (should be private)
	public void loadBoardConfig() throws IOException, BadConfigFormatException {
		BufferedReader reader = null;
		String line = null;
		
		//Large try block to ensure file will be closed on any exception
		try {
			//Attempt to open the file reader
			reader = new BufferedReader(new FileReader(layoutConfigFile));
			line = reader.readLine();
			int index = 0;

			while(line != null) {
				String[] split = line.split(",");
				//Assign numColumns
				if (numColumns == 0) numColumns = split.length;
				else if (numColumns != split.length) throw new BadConfigFormatException("Inconsistent number of board columns: " + numColumns + " != " + split.length);

				for (String cell : split) {
					if (!boardLegend.containsKey(cell.charAt(0))) throw new BadConfigFormatException("Invalid cell value: " + cell.charAt(0));
					boardLayout.put(index, cell);  // This step separates the config loading, from actually using it
												  // Not required, but organizes the code for me
					index++;

				}

				numRows++;
				index = MAX_BOARD_SIZE * numRows;
				line = reader.readLine();

			}

			//Final error checking
			if (numColumns > MAX_BOARD_SIZE) throw new BadConfigFormatException("Number of columns exceeds " + MAX_BOARD_SIZE);
			if (numRows > MAX_BOARD_SIZE) throw new BadConfigFormatException("Number of rows exceeds " + MAX_BOARD_SIZE);

		} finally {
			if (reader != null) {
				try {
					//Attempt to close the reader
					reader.close();
					
				} catch (IOException e) {
					//This error is handled here so as to not suppress other (more important) errors
					System.out.println(e.getMessage());
					
				}
			}
		}
	}

	//Load the board legend (should be private)
	public void loadRoomConfig() throws IOException, BadConfigFormatException {
		BufferedReader reader = null;
		String line = null;
		
		//Large try block to ensure file will be closed on any exception
		try {
			//Attempt to open the reader
			reader = new BufferedReader(new FileReader(legendConfigFile));
			line = reader.readLine();
			
			while(line != null) {
				//Use , as the delimiter in line.split : returns { "C", "Conservatory", "Card" }
				String[] split = line.split(", ");

				//Error handling
				try {
					boardLegend.put(split[0].charAt(0), split[1]);
					
					//Supplementary testing output
					//System.out.println("Added " + split[0].charAt(0) + " : " + split[1]);
					
					if (!(split[2].equals("Card") || split[2].equals("Other"))) throw new BadConfigFormatException("Invalid room type specification: " + split[2]);
					else if (split[2].equals("Card")) {
						//Checks for appropriate number of rooms, adds to deck
						if (numRooms == MAX_ROOMS_COUNT) throw new BadConfigFormatException("Invalid number of rooms: " + (numRooms + 1));
						deck[numRooms + numPlayers + numWeapons] = new Card(CardType.ROOM, split[1]);
						
						numRooms++;
						
					}
					
					if (split[1].equalsIgnoreCase("walkway")) BoardCell.setWalkwayInitial(split[0].charAt(0));

				} catch (ArrayIndexOutOfBoundsException e) {
					
					throw new BadConfigFormatException("Invalid number of room legend parameters: " + split.length);
				}

				line = reader.readLine();

			}
			
			if (numRooms != MAX_ROOMS_COUNT) throw new BadConfigFormatException("Invalid number of rooms: " + numRooms);
			
		} finally {
			if (reader != null) {
				try {
					//Attempt to close the reader
					reader.close();
					
				} catch (IOException e) {
					//This error is handled here so as to not suppress other (more important) errors
					System.out.println(e.getMessage());
					
				}
			}
		}
	}
	
	//Load the player config (should be private)
	public void loadPlayerConfig() throws IOException, BadConfigFormatException {
		BufferedReader reader = null;
		String line = null;
		
		Player player;
		
		//Large try block to ensure file will be closed on any exception
		try {
			//Attempt to open the reader
			reader = new BufferedReader(new FileReader(legendConfigFile));
			line = reader.readLine();
			
			while(line != null) {
				//Use ", " as the delimiter in line.split
				String[] split = line.split(", ");

				//Error handling
				try {
					if (numPlayers == MAX_PLAYERS_COUNT) throw new BadConfigFormatException("Invalid number of players: " + (numPlayers + 1));
					
					if (split[6].equals("Computer")) player = new ComputerPlayer(split[0], split[4], split[5], new Color(Integer.parseInt(split[1]), Integer.parseInt(split[2]), Integer.parseInt(split[3])));
					else if (split[6].equals("Player")) player = new HumanPlayer(split[0], split[4], split[5], new Color(Integer.parseInt(split[1]), Integer.parseInt(split[2]), Integer.parseInt(split[3])));
					
					players[numPlayers] = player;
					deck[numRooms + numPlayers + numWeapons] = new Card(CardType.PERSON, split[0]);
					numPlayers++;

				} catch (ArrayIndexOutOfBoundsException e) {
					
					throw new BadConfigFormatException("Invalid number of player config parameters: " + split.length);
				}

				line = reader.readLine();

			}
			
			if (numPlayers != MAX_PLAYERS_COUNT) throw new BadConfigFormatException("Invalid number of players: " + numPlayers);
			
		} finally {
			if (reader != null) {
				try {
					//Attempt to close the reader
					reader.close();
					
				} catch (IOException e) {
					//This error is handled here so as to not suppress other (more important) errors
					System.out.println(e.getMessage());
					
				}
			}
		}
	}
	
	//Load the weapon config (should be private)
	public void loadWeaponConfig() throws IOException, BadConfigFormatException {
		
		
	}

	//This function should only ever be called once
	private void calcAdjacencies() {
		//For every cell on the board, calculate a set of all adjacent cells
		//Store as map in adjacencies
		for (Integer i : boardLayout.keySet()) {
			HashSet<BoardCell> adj = new HashSet<>();

			BoardCell cellLeft = board[i].getCellLeft(this);
			BoardCell cellUp = board[i].getCellUp(this);
			BoardCell cellRight = board[i].getCellRight(this);
			BoardCell cellDown = board[i].getCellDown(this);

			if (cellLeft != null) adj.add(cellLeft);  //Check square to left
			if (cellUp != null) adj.add(cellUp);  //Check square above
			if (cellRight != null) adj.add(cellRight);  //Check square to right
			if (cellDown != null) adj.add(cellDown);  //Check square below

			adjacencies.put(board[i], adj);

		}
	}
	

	public void calcTargets(int r, int c, int pathLength) {
		targets = new HashSet<BoardCell>();
		
		BoardCell cell = getCellAt(r, c);
		Set<BoardCell> checked = new HashSet<>();
		
		if (cell != null) calcTargets(cell, pathLength, cell.isDoorway(), checked);

	}
	
	
	public void calcTargets(BoardCell cell, int pathLength) {
		targets = new HashSet<BoardCell>();
		
		Set<BoardCell> checked = new HashSet<>();
		
		if (cell != null) calcTargets(cell, pathLength, cell.isDoorway(), checked);

	}
	
	
	private void calcTargets(BoardCell startCell, int pathLength, boolean checkDoorway, Set<BoardCell> checked) {
		// Depth-first search to calculate all possible locations from a given square
		// Store in targets
		
		// Depth-first search checks that no cell from the parent search path is added
		Set<BoardCell> newChecked = new HashSet<BoardCell>(checked);
		newChecked.add(startCell);
		
		// BASE case (triggers when a cell is a "final destination")
		if (pathLength == 0 || (startCell.isDoorway() && !checkDoorway)) {
			targets.add(startCell);
			return;
			
		}
		
		// Go as far left as possible
		BoardCell cellLeft = startCell.getCellLeft(this);
		if (cellLeft != null && !checked.contains(cellLeft)) calcTargets(cellLeft, pathLength - 1, false, newChecked);
		
		// Go as far up as possible
		BoardCell cellUp = startCell.getCellUp(this);
		if (cellUp != null && !checked.contains(cellUp)) calcTargets(cellUp, pathLength - 1, false, newChecked);
		
		// Go as far right as possible
		BoardCell cellRight = startCell.getCellRight(this);
		if (cellRight != null && !checked.contains(cellRight)) calcTargets(cellRight, pathLength - 1, false, newChecked);
		
		// Go as far down as possible
		BoardCell cellDown = startCell.getCellDown(this);
		if (cellDown != null && !checked.contains(cellDown)) calcTargets(cellDown, pathLength - 1, false, newChecked);
		
	}
	
	//DEPRICATED - USED IN OLD TESTS
	@Deprecated
	public void setConfigFiles(String layout, String legend) {
		layoutConfigFile = new String(layout);
		legendConfigFile = new String(legend);

	}

	public void setConfig(ConfigType type, String fileName) {
		switch (type) {
		case BOARD:
			layoutConfigFile = new String(fileName);
			break;
			
		case LEGEND:
			legendConfigFile = new String(fileName);
			break;
		
		case PLAYER:
			playerConfigFile = new String(fileName);
			break;
			
		case WEAPON:
			weaponConfigFile = new String(fileName);
			break;
			
		}
	}
	
	//Simple getters
	public int getNumRows() { return numRows; }
	public int getNumColumns() { return numColumns; }

	public BoardCell getCellAt(int r, int c) { 
		//Supplementary testing code
		//System.out.println(r * MAX_BOARD_SIZE + j);
		return board[r * MAX_BOARD_SIZE + c]; 

	}
	
	public static Board getInstance() { return GAME_INSTANCE; }

	public Map<Character, String> getLegend() { return (boardLegend == null) ? null : boardLegend; }
	public Set<BoardCell> getTargets() { return (targets == null) ? null : targets; }
	public Set<BoardCell> getAdjList(BoardCell c) { return (adjacencies == null) ? null : adjacencies.get(c); }
	public Set<BoardCell> getAdjList(int r, int c) { return (adjacencies == null) ? null : adjacencies.get(getCellAt(r, c)); }
	public Player getPlayer(int i) { return players[i % MAX_PLAYERS_COUNT]; }

}
package clueGame;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Board {

	private int numRows = 0;     // Initialize to zero for an uninitialized board
	private int numColumns = 0;  // Initialize to zero for an uninitialized board

	public static final int MAX_BOARD_SIZE = 50;

	private BoardCell[] board;
	private Map<Integer, String> boardLayout;
	private Map<Character, String> boardLegend;
	private Map<BoardCell, Set<BoardCell>> adjacencies;
	private Set<BoardCell> targets;

	private String layoutConfigFile;
	private String legendConfigFile;


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

	}

	//Load the board layout
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

	//Load the board legend
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
					if (split[1].equalsIgnoreCase("walkway")) BoardCell.setWalkwayInitial(split[0].charAt(0));

				} catch (ArrayIndexOutOfBoundsException e) {
					
					throw new BadConfigFormatException("Invalid number of room legend parameters: " + split.length);
				}

				line = reader.readLine();

			}
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
	

	public void setConfigFiles(String layout, String legend) {
		layoutConfigFile = new String(layout);
		legendConfigFile = new String(legend);

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

}
package clueGame;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Board {

	private int numRows = 0;  // Initialize to zero for an uninitialized board
	private int numColumns = 0;  //Initialize to zero for an uninitialized board

	public static final int MAX_BOARD_SIZE = 50;

	private BoardCell[] board;
	private Map<Integer, String> layout;
	private Map<Character, String> legend;
	private Map<BoardCell, Set<BoardCell>> adjacencies;
	private Set<BoardCell> targets;
	private Set<BoardCell> checked;

	private String layoutConfigFile;
	private String legendConfigFile;


	//Variable used for singleton pattern
	private static final Board GAME_INSTANCE = new Board();
	// constructor is private to ensure only one can be created

	private Board() {

		/*/Got help from fellow students who also ran into Null pointer Exception.
		// They suggested 'Their fix"  for the problem which was this method below
		legend = new HashMap<Character,String>();
		board = new BoardCell[MAX_BOARDSIZE][MAX_BOARDSIZE];
		for(int i = 0; i < MAX_BOARDSIZE; i ++) {
			for(int j = 0; j < MAX_BOARDSIZE; j ++) {
				board[i][j] = new BoardCell(i,j);

			}
		}*/
		adjacencies = new HashMap<>();
		checked = new HashSet<>();
		
		layout = new HashMap<>();
		legend = new HashMap<>();

		board = new BoardCell[MAX_BOARD_SIZE * MAX_BOARD_SIZE];

	}

	public void initialize() {
		loadRoomConfig();
		loadBoardConfig();
		
		//Populate the grid
		for (Integer i : layout.keySet()) {
			//Supplimentary testing code
			//System.out.println("Board cell index: " + i);
			board[i] = new BoardCell(i / MAX_BOARD_SIZE, i % MAX_BOARD_SIZE, layout.get(i));
			
		}

		calcAdjacencies();

	}

	//Load the board layout
	public void loadBoardConfig() {
		try {
			//Open the file reader
			BufferedReader reader = new BufferedReader(new FileReader(layoutConfigFile));
			String line = reader.readLine();
			int index = 0;
			
			while(line != null) {
				String[] split = line.split(",");
				//Assign numColumns
				if (numColumns == 0) numColumns = split.length;
				else if (numColumns != split.length) throw new BadConfigFormatException("Inconsistent number of board columns: " + numColumns + " != " + split.length);
				
				for (String cell : split) {
			    		if (!legend.containsKey(cell.charAt(0))) throw new BadConfigFormatException("Invalid cell value: " + cell.charAt(0));
					layout.put(index, cell);
					index++;
					
				}
				
				numRows++;
				index = MAX_BOARD_SIZE * numRows;
				line = reader.readLine();
				
			}
			
			//Final error checking
			if (numColumns > MAX_BOARD_SIZE) throw new BadConfigFormatException("Number of columns exceeds " + MAX_BOARD_SIZE);
			if (numRows > MAX_BOARD_SIZE) throw new BadConfigFormatException("Number of rows exceeds " + MAX_BOARD_SIZE);
			
			//Attempt to close the file reader
			reader.close();
			
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
			
		} catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());
			
		} 
	}

	//Load the board legend
	public void loadRoomConfig() {
		try {
			//Open the file reader
			BufferedReader reader = new BufferedReader(new FileReader(legendConfigFile));
			String line = reader.readLine();
			
			while(line != null) {
				//Use , as the delimiter in line.split : returns { "C", "Conservatory", "Card" }
				String[] split = line.split(", ");
				//Error handling
				try {
					legend.put(split[0].charAt(0), split[1]);
					//Supplimentary testing output
					//System.out.println("Added " + split[0].charAt(0) + " : " + split[1]);
					if (!(split[2].equals("Card") || split[2].equals("Other"))) throw new BadConfigFormatException("Invalid room type specification: " + split[2]);
					
				} catch (ArrayIndexOutOfBoundsException e) {
					throw new BadConfigFormatException("Invalid number of room legend parameters: " + split.length);
					
				}
				
				line = reader.readLine();
				
			}
			
			//Attempt to close the file reader
			reader.close();
			
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
			
		} catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());
			
		}
	}
	
	//This function should only ever be called once
	private void calcAdjacencies() {
		//For every cell on the board, calculate a set of all adjacent cells
		//Store as map in adjacencies
		for (Integer i : layout.keySet()) {
			HashSet<BoardCell> adj = new HashSet<>();

			int cellLeft = board[i].getCellLeft(MAX_BOARD_SIZE, numColumns);
			int cellUp = board[i].getCellUp(MAX_BOARD_SIZE);
			int cellRight = board[i].getCellRight(MAX_BOARD_SIZE, numColumns);
			int cellDown = board[i].getCellDown(MAX_BOARD_SIZE, numRows);

			if (cellLeft >= 0) adj.add(board[cellLeft]);  //Check square to left
			if (cellUp >= 0) adj.add(board[cellUp]);  //Check square above
			if (cellRight >= 0) adj.add(board[cellRight]);  //Check square to right
			if (cellDown >= 0) adj.add(board[cellDown]);  //Check square below

			adjacencies.put(board[i], adj);

		}

	}

	public void calcTargets(BoardCell startCell, int pathLength) {
		//Calculate all possible locations from a given square
		//Store as set in targets

		//Supplimentary test output
		//System.out.println("calcTargets | startCell address: " + startCell + ", pathLength: " + pathLength);

		if (pathLength <= 0) return;  //Less than equal is a safety catch on the chance pathLength starts less than 0

		if (startCell != null) {
			targets = new HashSet<>();
			targets.add(startCell);

			checked = new HashSet<>();

		}

		//Moves targets to an intermediate list for each iteration
		HashSet<BoardCell> intTargets = new HashSet<>();
		for (BoardCell cell : targets) intTargets.add(cell);

		//for (BoardCell cell : intTargets) System.out.println("intTargets contains: " + cell.getRow() + " " + cell.getColumn());

		//Adds every cell to targets that stemmed from the previous
		for (BoardCell cell : intTargets) {
			targets.remove(cell);
			if (checked.contains(cell)) continue;

			int cellLeft = cell.getCellLeft(MAX_BOARD_SIZE, numColumns);
			int cellUp = cell.getCellUp(MAX_BOARD_SIZE);
			int cellRight = cell.getCellRight(MAX_BOARD_SIZE, numColumns);
			int cellDown = cell.getCellDown(MAX_BOARD_SIZE, numRows);

			//Supplimentary test output
			//System.out.println("intTargets cell: " + (cell.getRow() * 4 + cell.getColumn()));
			//System.out.println("left " + cellLeft + ", up " + cellUp + ", right " + cellRight + ", down " + cellDown);

			//Note the order of the conditions 
			if (cellLeft >= 0 && !targets.contains(board[cellLeft])) targets.add(board[cellLeft]);  //Check square to left
			if (cellUp >= 0 && !targets.contains(board[cellUp])) targets.add(board[cellUp]);  //Check square above
			if (cellRight >= 0 && !targets.contains(board[cellRight])) targets.add(board[cellRight]);  //Check square to right
			if (cellDown >= 0 && !targets.contains(board[cellDown])) targets.add(board[cellDown]);  //Check square below

			checked.add(cell);

		}

		//Supplimentary test output
		//for (BoardCell cell : targets) System.out.println("targets contains: " + (cell.getRow() * 4 + cell.getColumn()));
		//for (BoardCell cell : checked) System.out.println("checked contains: " + (cell.getRow() * 4 + cell.getColumn()));

		calcTargets(null, pathLength - 1);

	}

	public void setConfigFiles(String layout, String legend) {
		layoutConfigFile = new String(layout);
		legendConfigFile = new String(legend);

	}

	//Simple getters
	public int getNumRows() { return numRows; }
	public int getNumColumns() { return numColumns; }
	
	public BoardCell getCellAt(int r, int j) { 
		//Supplimentary testing code
		//System.out.println(r * MAX_BOARD_SIZE + j);
		return board[r * MAX_BOARD_SIZE + j]; 
		
	}
	public static Board getInstance() { return GAME_INSTANCE; }
	
	public Set<BoardCell> getTargets() { return (targets == null) ? null : targets; }
	public Map<Character, String> getLegend() { return (legend == null) ? null : legend; }
	public Set<BoardCell> getAdjList(BoardCell c) { return (adjacencies == null) ? null : adjacencies.get(c); }
	
}
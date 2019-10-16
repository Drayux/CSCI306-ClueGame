package clueGame;


import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

import experiment.BoardCell;


public class Board {

	private int numRows;
	private  int numColumns;

	
	public static final int MAX_BOARDSIZE = 100;

	private clueGame.BoardCell[][] board;

	private Map<Character,String> legend;

	private Map<BoardCell, Set<BoardCell>> adjMatrix;

	private Set<BoardCell> targets;

	private String boardConfigFile;

	private String roomConfigFile;


//Variable used for singleton pattern
	private static Board theInstance = new Board();
// constructor is private to ensure only one can be created

	private Board() {
		
		//Got help from fellow students who also ran into Null pointer Exception.
		// They suggested 'Their fix"  for the problem which was this method below
		legend = new HashMap<Character,String>();
		board = new clueGame.BoardCell[MAX_BOARDSIZE][MAX_BOARDSIZE];
		for(int i = 0; i < MAX_BOARDSIZE; i ++) {
			for(int j = 0; j < MAX_BOARDSIZE; j ++) {
				board[i][j] = new clueGame.BoardCell(i,j);
			
		}
	}
	
	}
	

// this method returns the only Board

	public static Board getInstance() {
		return theInstance;
	}


	public void initialize() {

	}

	
	public void loadBoardConfig() {

	}

	public void calcAdjacencies() {

	}
	
	public void loadRoomConfig() {

	}
	
	public void calcTargets(BoardCell cell, int pathLength) {

	}



	public clueGame.BoardCell getCellAt(int i, int j) {
		// TODO Auto-generated method stub
	
		return board[i][j];
	}

	public Map<Character, String> getLegend() {
	// TODO Auto-generated method stub
	return null;
	}

	public int getNumColumns() {
	
	return 0;
	}
	


	public void setConfigFiles(String string, String string2) {
		
		
	}

	public int getNumRows() {
		
		return 0;
	}
	
}
package experiment;

import java.util.HashMap;
import java.util.HashSet;

//Class for a 4x4 test board
public class IntBoard {
    
	public static final int BOARD_SIZE = 4;
	
    private BoardCell[] grid; 	//Row first, column second order
    private HashMap<BoardCell, HashSet<BoardCell>> adjacencies;
    private HashSet<BoardCell> targets;
    
    public IntBoard() {
        grid = new BoardCell[BOARD_SIZE * BOARD_SIZE];
        
        adjacencies = new HashMap<>();
        calcAdjacencies();
        
    }
    
    public BoardCell getBoardCell(int r, int c) {
        return grid[r * BOARD_SIZE + c];
        
    }
    
    public HashSet<BoardCell> getAdjList(BoardCell c) {
        return adjacencies.get(c);
        
    }
    
    public void calcTargets(BoardCell startCell, int pathLength) {
        //Calculate all possible locations from a given square
        //Store as set in targets
        
    }
    
    public HashSet<BoardCell> getTargets() {
        return targets;
        
    }
    
    //////////////////////////////////////////
    
    private void calcAdjacencies() {
        //For every cell on the board, calculate a set of all adjacent cells
        //Store as map in adjacencies
    	for (int i = 0; i < BOARD_SIZE * BOARD_SIZE; i++) {
    		HashSet<BoardCell> adj = new HashSet<>();
    		
    		if (i > 0) adj.add(grid[i - 1]);  //Check square to left
    		if (i >= BOARD_SIZE) adj.add(grid[i - BOARD_SIZE]);  //Check square above
    		if (i < BOARD_SIZE * BOARD_SIZE - 1) adj.add(grid[i + 1]);  //Check square to right
    		if (i < BOARD_SIZE * (BOARD_SIZE - 1)) adj.add(grid[i + BOARD_SIZE]);  //Check square below
    		
    		adjacencies.put(grid[i], adj);
    		
    	}
        
    }
    
}

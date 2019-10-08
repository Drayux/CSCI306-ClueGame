package experiment;

import java.util.HashMap;
import java.util.HashSet;

//Class for a 4x4 test board
public class IntBoard {
    
    public static final int BOARD_SIZE = 4;
	
    private BoardCell[] grid; 	//Row first, column second order
    private HashMap<BoardCell, HashSet<BoardCell>> adjacencies;
    private HashSet<BoardCell> targets;
    private HashSet<BoardCell> checked;
    
    public IntBoard() {
        grid = new BoardCell[BOARD_SIZE * BOARD_SIZE];
        
        //Populate the grid
        for (int i = 0; i < BOARD_SIZE * BOARD_SIZE; i++)
            grid[i] = new BoardCell(i / BOARD_SIZE, i % BOARD_SIZE);
        
        adjacencies = new HashMap<>();
        checked = new HashSet<>();
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
            
            int cellLeft = cell.getCellLeft(BOARD_SIZE);
            int cellUp = cell.getCellUp(BOARD_SIZE);
            int cellRight = cell.getCellRight(BOARD_SIZE);
            int cellDown = cell.getCellDown(BOARD_SIZE);
            
            //Supplimentary test output
            //System.out.println("intTargets cell: " + (cell.getRow() * 4 + cell.getColumn()));
            //System.out.println("left " + cellLeft + ", up " + cellUp + ", right " + cellRight + ", down " + cellDown);
            
            //Note the order of the conditions 
            if (cellLeft >= 0 && !targets.contains(grid[cellLeft])) targets.add(grid[cellLeft]);  //Check square to left
            if (cellUp >= 0 && !targets.contains(grid[cellUp])) targets.add(grid[cellUp]);  //Check square above
            if (cellRight >= 0 && !targets.contains(grid[cellRight])) targets.add(grid[cellRight]);  //Check square to right
            if (cellDown >= 0 && !targets.contains(grid[cellDown])) targets.add(grid[cellDown]);  //Check square below
            
            checked.add(cell);
            
        }
        
        //Supplimentary test output
        //for (BoardCell cell : targets) System.out.println("targets contains: " + (cell.getRow() * 4 + cell.getColumn()));
        //for (BoardCell cell : checked) System.out.println("checked contains: " + (cell.getRow() * 4 + cell.getColumn()));
        
        calcTargets(null, pathLength - 1);
        
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

            int cellLeft = grid[i].getCellLeft(BOARD_SIZE);
            int cellUp = grid[i].getCellUp(BOARD_SIZE);
            int cellRight = grid[i].getCellRight(BOARD_SIZE);
            int cellDown = grid[i].getCellDown(BOARD_SIZE);
            
            if (cellLeft >= 0) adj.add(grid[cellLeft]);  //Check square to left
            if (cellUp >= 0) adj.add(grid[cellUp]);  //Check square above
            if (cellRight >= 0) adj.add(grid[cellRight]);  //Check square to right
            if (cellDown >= 0) adj.add(grid[cellDown]);  //Check square below

            adjacencies.put(grid[i], adj);
    		
    	}
        
    }
    
}

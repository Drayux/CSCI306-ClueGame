package experiment;

import java.util.Map;
import java.util.Set;

public class IntBoard {
    
    private BoardCell[] grid;
    private Map<BoardCell, Set<BoardCell>> adjacencies;
    private Set<BoardCell> targets;
    
    public IntBoard() {
        
        
    }
    
    public BoardCell getBoardCell(int r, int c) {
        return null;
        
    }
    
    public Set<BoardCell> getAdjList() {
        return null;
        
    }
    
    public Set<BoardCell> getTargets() {
        return null;
        
    }
    
    //////////////////////////////////////////
    
    private void calcAdjacencies() {
        //For every cell on the board, calculate a set of all adjacent cells
        //Store as map in adjacencies
        
    }
    
    private void calcTargets(BoardCell startCell, int pathLength) {
        //Calculate all possible locations from a given square
        //Store as set in targets
        
    }
    
}

package clueGame;

public class BoardCell {
    
    private int row;
    private int column;
    
    private char cellInitial;
    private DoorDirection doorDirection;
    private boolean isDoorway = false;
    
    public BoardCell() {
        row = 0;
        column = 0;
        cellInitial = '!';
        
    }
    
    public BoardCell(int r, int c) {
        row = r;
        column = c;
        cellInitial = '!';
        
    }
    
    public BoardCell(int r, int c, String cell) {
    		row = r;
    		column = c;
    		
    		if (cell.length() > 1) {
    			switch (cell.charAt(1)) {
    			case 'L':
    				isDoorway = true;
    				doorDirection = DoorDirection.LEFT;
    				break;
    			case 'U':
    				isDoorway = true;
    				doorDirection = DoorDirection.UP;
    				break;
    			case 'R':
    				isDoorway = true;
    				doorDirection = DoorDirection.RIGHT;
    				break;
    			case 'D':
    				isDoorway = true;
    				doorDirection = DoorDirection.DOWN;
    				break;
			default:
				doorDirection = DoorDirection.NONE;
				break;
    			
    			}
    		} else {
			doorDirection = DoorDirection.NONE;
			
    		}
    }
    
    public int getRow() {
        return row;
        
    }
    
    public int getColumn() {
        return column;
        
    
    }
    
    //Returns grid array index of cell to the left
    public int getCellLeft(final int MAX_BOARD_SIZE, final int NUM_COLUMNS) {
        int cellIndex = row * MAX_BOARD_SIZE + column;
        
        if (cellIndex % NUM_COLUMNS > 0) return cellIndex - 1;
        return -1;
    }
    
    //Returns grid array index of cell above
    public int getCellUp(final int MAX_BOARD_SIZE) {
        int cellIndex = row * MAX_BOARD_SIZE + column;
        
        if (cellIndex >= MAX_BOARD_SIZE) return cellIndex - MAX_BOARD_SIZE;
        return -1;
    }
    
    //Returns grid array index of cell to the right
    public int getCellRight(final int MAX_BOARD_SIZE, final int NUM_COLUMNS) {
        int cellIndex = row * MAX_BOARD_SIZE + column;
        
        if (cellIndex % NUM_COLUMNS < NUM_COLUMNS - 1) return cellIndex + 1;
        return -1;
    }

    //Returns grid array index of cell below
    public int getCellDown(final int MAX_BOARD_SIZE, final int NUM_ROWS) {
        int cellIndex = row * MAX_BOARD_SIZE + column;
        
        if (cellIndex < MAX_BOARD_SIZE * (NUM_ROWS - 1)) return cellIndex + MAX_BOARD_SIZE;
        return -1;
    }
    
    //Getters
	public char getInitial() { return cellInitial; }
	public DoorDirection getDoorDirection() { return doorDirection; }
	public boolean isDoorway() { return isDoorway; }

}

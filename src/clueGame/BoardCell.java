package clueGame;

public class BoardCell {
    
    private int row;
    private int column;
    
    public BoardCell() {
        row = 0;
        column = 0;
        
    }
    
    public BoardCell(int r, int c) {
        row = r;
        column = c;
        
    }
    
    public int getRow() {
        return row;
        
    }
    
    public int getColumn() {
        return column;
        
    
    }
    
    //Returns grid array index of cell to the left
    public int getCellLeft(final int BOARD_SIZE) {
        int cellIndex = row * BOARD_SIZE + column;
        
        if (cellIndex % BOARD_SIZE > 0) return cellIndex - 1;
        return -1;
    }
    
    //Returns grid array index of cell above
    public int getCellUp(final int BOARD_SIZE) {
        int cellIndex = row * BOARD_SIZE + column;
        
        if (cellIndex >= BOARD_SIZE) return cellIndex - BOARD_SIZE;
        return -1;
    }
    
    //Returns grid array index of cell to the right
    public int getCellRight(int BOARD_SIZE) {
        int cellIndex = row * BOARD_SIZE + column;
        
        if (cellIndex % BOARD_SIZE < BOARD_SIZE - 1) return cellIndex + 1;
        return -1;
    }

    //Returns grid array index of cell below
    public int getCellDown(int BOARD_SIZE) {
        int cellIndex = row * BOARD_SIZE + column;
        
        if (cellIndex < BOARD_SIZE * (BOARD_SIZE - 1)) return cellIndex + BOARD_SIZE;
        return -1;
    }

	public Object getInitial() {
		// TODO Auto-generated method stub
		return null;
	}


	public Object getDoorDirection() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isDoorway() {
		// TODO Auto-generated method stub
		return false;
	
	}
}

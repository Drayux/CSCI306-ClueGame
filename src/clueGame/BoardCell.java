package clueGame;

public class BoardCell {

	private int row;
	private int column;

	private static char walkwayInitial = 0;

	private char cellInitial;
	private DoorDirection doorDirection;
	private boolean isDoorway = false;
	private boolean isWalkway = false;

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
		cellInitial = cell.charAt(0);

		if (cellInitial == walkwayInitial) isWalkway = true;

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

	//Cell functions designed for use with adj/targets functions
	//Returns cell to the left
	public BoardCell getCellLeft(final Board board) {
		if (column == 0) return null;

		//Cell exists, so create a variable for the one we are looking to return
		BoardCell cell = board.getCellAt(row, column - 1);

		//Original cell is a walkway
		if (isWalkway) {
			if (cell.isWalkway) return cell;
			else if (cell.doorDirection == DoorDirection.RIGHT) return cell; //Player entered room
			else return null;

		}

		//Original cell is a doorway
		if (doorDirection == DoorDirection.LEFT && cell.isWalkway) return cell; 

		return null;
		//return cell;
	}

	//Returns cell above
	public BoardCell getCellUp(final Board board) {
		if (row == 0) return null;

		//Cell exists, so create a variable for the one we are looking to return
		BoardCell cell = board.getCellAt(row - 1, column);

		//Original cell is a walkway
		if (isWalkway) {
			if (cell.isWalkway) return cell;
			else if (cell.doorDirection == DoorDirection.DOWN) return cell; //Player entered room
			else return null;

		}

		//Original cell is a doorway
		if (doorDirection == DoorDirection.UP && cell.isWalkway) return cell;

		return null;
	}

	//Returns cell to the right
	public BoardCell getCellRight(final Board board) {
		if (column >= board.getNumColumns() - 1) return null;
		
		//Cell exists, so create a variable for the one we are looking to return
		BoardCell cell = board.getCellAt(row, column + 1);

		//Original cell is a walkway
		if (isWalkway) {
			if (cell.isWalkway) return cell;
			else if (cell.doorDirection == DoorDirection.LEFT) return cell; //Player entered room
			else return null;

		}

		//Original cell is a doorway
		if (doorDirection == DoorDirection.RIGHT && cell.isWalkway) return cell;

		return null;
	}

	//Returns cell below
	public BoardCell getCellDown(final Board board) {
		if (row >= board.getNumRows() - 1) return null;

		//Cell exists, so create a variable for the one we are looking to return
		BoardCell cell = board.getCellAt(row + 1, column);

		//Original cell is a walkway
		if (isWalkway) {
			if (cell.isWalkway) return cell;
			else if (cell.doorDirection == DoorDirection.UP) return cell; //Player entered room
			else return null;

		}

		//Original cell is a doorway
		if (doorDirection == DoorDirection.DOWN && cell.isWalkway) return cell;

		return null;
	}

	//walkwayInitial will only be set once
	public static void setWalkwayInitial(char c) {
		if (walkwayInitial == 0) walkwayInitial = c;

	}

	//Getters
	public char getInitial() { return cellInitial; }
	public DoorDirection getDoorDirection() { return doorDirection; }
	public boolean isDoorway() { return isDoorway; }
	public boolean isWalkway() { return isWalkway; }
}

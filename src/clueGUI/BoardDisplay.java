package clueGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

import clueGame.Board;
import clueGame.DoorDirection;

public class BoardDisplay extends JPanel {

	private static final int SIZE = 32;
	private static final int DOOR_WIDTH = 8;
	
	private static final Color walkwayColor = new Color(237, 211, 137, 255);
	private static final Color borderColor = new Color(140, 140, 120, 255);
	private static final Color doorColor = new Color(136, 86, 15, 255);
	private static final Color backgroundColor = new Color(220, 215, 210, 255);

	public BoardDisplay() {}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(backgroundColor);
		g.fillRect(0, 0, (int) getPreferredSize().getHeight() + 1, (int) getPreferredSize().getWidth() + 1);
		
		for (int i = 0; i < Board.getInstance().getNumRows(); i++) {
			for (int j = 0; j < Board.getInstance().getNumColumns(); j++) {

				g.setColor(doorColor);
				
				switch (Board.getInstance().getCellAt(i, j).getDoorDirection()) {
				case NONE:
					if (!Board.getInstance().getCellAt(i, j).isWalkway()) break;
					
					// Draw the board cell
					g.setColor(walkwayColor);
					g.fillRect(j * SIZE, i * SIZE, SIZE, SIZE);

					// Draw the border
					g.setColor(borderColor);
					g.drawRect(j * SIZE, i * SIZE, SIZE, SIZE);
					
					break;
					
				case UP:
					g.fillRect(j * SIZE, i * SIZE, SIZE, DOOR_WIDTH);
					break;
					
				case RIGHT:
					g.fillRect((j * SIZE) + (SIZE - DOOR_WIDTH), i * SIZE, DOOR_WIDTH, SIZE);
					break;
				
				case DOWN:
					g.fillRect(j * SIZE, (i * SIZE) + (SIZE - DOOR_WIDTH), SIZE, DOOR_WIDTH);
					break;
					
				case LEFT:
					g.fillRect(j * SIZE, i * SIZE, DOOR_WIDTH, SIZE);
					break;
					
				default:
					break;
					
				}
			}
		}
	}

	@Override
	public Dimension getPreferredSize() {

		return new Dimension(Board.getInstance().getNumRows() * SIZE, Board.getInstance().getNumColumns() * SIZE);
	}

	private static Dimension calculateRoomCenter(Board board, int rows, int columns, char roomInitial) {
		int maxColTop = -1;
		int maxColBottom = -1;
		
		int curColTop = -1;
		int curColBottom = -1;
		
		// In every column, find the longest section of room
		for (int j = 0; j < board.getNumColumns(); j++) {
			for (int i = 0; i < board.getNumRows(); i++) {
				if (board.getCellAt(i, j).getInitial() == roomInitial) {
					if (curColTop == -1) {
						curColTop = i;
						curColBottom = i;
						
					}
					
				} else if (curColTop != -1) {
					maxColTop = curColTop;
					maxColBottom = curColBottom;
					
				}
				
			}
		}
		
		
		
		return null;
	}
	
}

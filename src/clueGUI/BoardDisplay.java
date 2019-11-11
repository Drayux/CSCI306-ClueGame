package clueGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JPanel;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.DoorDirection;

public class BoardDisplay extends JPanel {

	private static final int SIZE = 32;
	private static final int DOOR_WIDTH = 8;
	
	private static final Color walkwayColor = new Color(237, 211, 137, 255);
	private static final Color borderColor = new Color(140, 140, 120, 255);
	private static final Color playerBorderColor = new Color(90, 90, 60, 255);
	private static final Color textColor = new Color(75, 75, 40, 255);
	private static final Color doorColor = new Color(136, 86, 15, 255);
	private static final Color backgroundColor = new Color(220, 215, 210, 255);

	private Map<Character, Dimension> roomCenters = new HashMap<Character, Dimension>();
	
	public BoardDisplay() {
		for (Card gameCard : Board.getInstance().getGameCards()) {
			char initial = 0;
			
			if (gameCard.getType() == CardType.ROOM) {
				for (char c : Board.getInstance().getLegend().keySet()) {
					if (Board.getInstance().getLegend().get(c).equals(gameCard.getName())) {
						initial = c;
						
						break;
					}
				}
				
				if (initial != 0) roomCenters.put(initial, calculateRoomCenter(Board.getInstance(), initial));
				
			}
			
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Draw the background
		g.setColor(backgroundColor);
		g.fillRect(0, 0, (int) getPreferredSize().getHeight() + 1, (int) getPreferredSize().getWidth() + 1);
		
		// Draw the board
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
		
		// Draw the players
		for (int i = 0; i < Board.getInstance().MAX_PLAYERS_COUNT; i++) {
			g.setColor(Board.getInstance().getPlayer(i).getColor());
			g.fillOval(Board.getInstance().getPlayer(i).getColumn() * SIZE + 1, Board.getInstance().getPlayer(i).getRow() * SIZE + 1, SIZE - 2, SIZE - 2);
			
			g.setColor(playerBorderColor);
			g.drawOval(Board.getInstance().getPlayer(i).getColumn() * SIZE + 1, Board.getInstance().getPlayer(i).getRow() * SIZE + 1, SIZE - 2, SIZE - 2);
			
		}
		
		// Draw the room labels
		g.setColor(textColor);
		
		for (char initial : roomCenters.keySet()) {
			int strWidth = g.getFontMetrics().stringWidth(Board.getInstance().getLegend().get(initial));
			int strHeight = g.getFontMetrics().getHeight();
			
			g.drawString(Board.getInstance().getLegend().get(initial), (int) (roomCenters.get(initial).getWidth() * SIZE - (strWidth / 2)), (int) (roomCenters.get(initial).getHeight() * SIZE) + (strHeight / 2));
			
		}
	}

	@Override
	public Dimension getPreferredSize() {

		return new Dimension(Board.getInstance().getNumRows() * SIZE, Board.getInstance().getNumColumns() * SIZE);
	}

	private static Dimension calculateRoomCenter(Board board, char roomInitial) {
		int maxColTop = -1;
		int maxColBottom = -1;
		
		int curColTop = -1;
		int curColBottom = -1;
		
		int shiftCountRight = 0;
		int shiftCountLeft = 0;
		
		int maxArea = 0;
		int curArea = 0;
		
		int maxCoordX = 0;
		int maxCoordY = 0;
		
		int maxDimX = 0;
		int maxDimY = 0;
		
		// In every column, find the longest section of room
		for (int j = 0; j < board.getNumColumns(); j++) {
			for (int i = 0; i < board.getNumRows(); i++) {
				if (board.getCellAt(i, j).getInitial() == roomInitial) {
					if (curColTop == -1) curColTop = i;
					curColBottom = i;
					
				} else if (curColTop != -1) {
					if ((curColBottom - curColTop) > (maxColBottom - maxColTop)) {
						maxColTop = curColTop;
						maxColBottom = curColBottom;
						
					}
					
					curColTop = -1;
					curColBottom = -1;
					
				}
				
			}
			
			// Finally once the board ends, the room also has if not already
			if ((curColBottom - curColTop) > (maxColBottom - maxColTop)) {
				maxColTop = curColTop;
				maxColBottom = curColBottom;
				
			}
			
			curColTop = -1;
			curColBottom = -1;
			
			// Test output
			//System.out.println("Top: " + maxColTop);
			//System.out.println("Bottom: " + maxColBottom);
			//System.out.println();
			
			///////////////////////////////////////////
			
			shiftCountRight = 0;
			shiftCountLeft = 0;
			
			boolean edge = false;
			
			if ((maxColBottom - maxColTop) > 0) {
				// If a room is present, check for the same row as far right as possible
				for (int k = j + 1; k < board.getNumColumns() && !edge; k++) {
					for (int l = maxColTop; l <= maxColBottom; l++) {
						if (board.getCellAt(l, k).getInitial() != roomInitial) {
							edge = true;
							
							break;
						}
					}
					
					shiftCountRight = (edge) ? shiftCountRight : (shiftCountRight + 1);
					
				}
				
				edge = false;
				
				// If a room is present, check for the same row as far left as possible
				for (int k = j - 1; k >= 0 && !edge; k--) {
					for (int l = maxColTop; l <= maxColBottom; l++) {
						if (board.getCellAt(l, k).getInitial() != roomInitial) {
							edge = true;
							
							break;
						}
					}
					
					shiftCountLeft = (edge) ? shiftCountLeft : (shiftCountLeft + 1);
					
				}
			}
			
			curArea = (shiftCountRight + shiftCountLeft + 1) * (maxColBottom - maxColTop + 1);
			
			if (curArea >= maxArea) {	// >= Will prioritize longer rooms over taller rooms given the same size (Look at room S on our layout for reference)
				maxArea = curArea;
				
				maxCoordX = j - shiftCountLeft;
				maxCoordY = maxColTop;
				
				maxDimX = shiftCountRight + shiftCountLeft + 1;
				maxDimY = maxColBottom - maxColTop + 1;
				
			}
			
			maxColTop = -1;
			maxColBottom = -1;
			
		}
		
		//Test output
		//System.out.println("Coord X: " + maxCoordX);
		//System.out.println("Coord Y: " + maxCoordY);
		
		//System.out.println("Dim X: " + maxDimX);
		//System.out.println("Dim Y: " + maxDimY);
		
		return new Dimension(maxCoordX + (maxDimX / 2), maxCoordY + (maxDimY / 2));
	}
	
}

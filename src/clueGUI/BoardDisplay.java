package clueGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.DoorDirection;
import clueGame.Solution;
import clueGame.WaitThread;

public class BoardDisplay extends JPanel {

	private int cellSize = 32;
	private int doorSize = 8;

	private static final Color walkwayColor = new Color(237, 211, 137, 255);
	private static final Color selectedWalkwayColor = new Color(250, 234, 190, 255);
	private static final Color selectedRoomColor = new Color(245, 245, 235, 255);
	private static final Color borderColor = new Color(140, 140, 120, 255);
	private static final Color playerBorderColor = new Color(90, 90, 60, 255);
	private static final Color textColor = new Color(75, 75, 40, 255);
	private static final Color doorColor = new Color(136, 86, 15, 255);
	private static final Color backgroundColor = new Color(220, 215, 210, 255);

	//private Map<Character, Dimension> roomCenters = new HashMap<Character, Dimension>();
	private Set<BoardRoom> rooms = new HashSet<BoardRoom>();
	private Dimension lastClick = null;

	public final ClickListener listener = new ClickListener();

	public BoardDisplay() {
		this.addComponentListener(new ResizeListener());
		//this.addMouseListener(new ClickListener());

		for (Card gameCard : Board.getInstance().getGameCards()) {
			char initial = 0;
			String name = null;

			if (gameCard.getType() == CardType.ROOM) {
				for (char c : Board.getInstance().getLegend().keySet()) {
					if (Board.getInstance().getLegend().get(c).equals(gameCard.getName())) {
						initial = c;
						name = gameCard.getName();

						break;
					}
				}

				if (initial != 0 && name != null) {
					BoardRoom room = new BoardRoom(initial, name);
					room.defineRoomCenter(calculateRoomCenter(Board.getInstance(), initial));

					rooms.add(room);
				}
			}

			this.repaint();

		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		BoardCell curCell = null;

		// Draw the background
		g.setColor(backgroundColor);
		g.fillRect(0, 0, (int) getPreferredSize().getHeight() + 1, (int) getPreferredSize().getWidth() + 1);

		// Compile a list of selected room chars
		Set<Character> selectedRooms = new HashSet<Character>();
		for (int i = 0; i < Board.getInstance().getNumRows(); i++) {
			for (int j = 0; j < Board.getInstance().getNumColumns(); j++) {
				if (Board.getInstance().getCellAt(i, j).isSelected) selectedRooms.add(Board.getInstance().getCellAt(i, j).getInitial());
				
			}
		}
		
		// Draw the selected rooms
		for (Character c : selectedRooms) {
			for (BoardRoom room : rooms) {
				if (room.getInitial() == c) {
					room.drawSelected(g, cellSize, selectedRoomColor);
					break;
					
				}
			}
		}
		
		// Draw the board
		for (int i = 0; i < Board.getInstance().getNumRows(); i++) {
			for (int j = 0; j < Board.getInstance().getNumColumns(); j++) {

				curCell = Board.getInstance().getCellAt(i, j);

				// Draw the walkways
				g.setColor(doorColor);
				switch (curCell.getDoorDirection()) {
				case NONE:
					if (!curCell.isWalkway()) break;

					// Draw the board cell
					g.setColor((curCell.isSelected) ? selectedWalkwayColor : walkwayColor);
					g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);

					// Draw the border
					g.setColor(borderColor);
					g.drawRect(j * cellSize, i * cellSize, cellSize, cellSize);

					break;

				case UP:
					g.fillRect(j * cellSize, i * cellSize, cellSize, doorSize);
					break;

				case RIGHT:
					g.fillRect((j * cellSize) + (cellSize - doorSize), i * cellSize, doorSize, cellSize);
					break;

				case DOWN:
					g.fillRect(j * cellSize, (i * cellSize) + (cellSize - doorSize), cellSize, doorSize);
					break;

				case LEFT:
					g.fillRect(j * cellSize, i * cellSize, doorSize, cellSize);
					break;

				default:
					break;

				}
			}
		}

		// Draw the players (on walkways)
		for (int i = 0; i < Board.MAX_PLAYERS_COUNT; i++) {
			if (Board.getInstance().getPlayer(i).getLocation().isWalkway()) {
				g.setColor(Board.getInstance().getPlayer(i).getColor());
				g.fillOval(Board.getInstance().getPlayer(i).getColumn() * cellSize + 1, Board.getInstance().getPlayer(i).getRow() * cellSize + 1, cellSize - 2, cellSize - 2);

				g.setColor(playerBorderColor);
				g.drawOval(Board.getInstance().getPlayer(i).getColumn() * cellSize + 1, Board.getInstance().getPlayer(i).getRow() * cellSize + 1, cellSize - 2, cellSize - 2);

			} else {
				for (BoardRoom room : rooms) {
					if (room.getInitial() == Board.getInstance().getPlayer(i).getLocation().getInitial()) room.putPlayer(Board.getInstance().getPlayer(i));
					
				}
			}
		}

		// Draw the players (in rooms)
		for (BoardRoom room : rooms) {
			room.drawPlayers(g, cellSize, playerBorderColor);
			room.clearPlayers();
			
		}

		// Draw the room labels
		g.setColor(textColor);

		for (BoardRoom room : rooms) {
			int strWidth = g.getFontMetrics().stringWidth(room.getLabel());
			int strHeight = g.getFontMetrics().getHeight();

			g.drawString(room.getLabel(), (int) (room.getCenterX() * cellSize - (strWidth / 2)), (int) (room.getCenterY() * cellSize) + (strHeight / 2));

		}
	}

	@Override
	public Dimension getPreferredSize() {

		return new Dimension(Board.getInstance().getNumRows() * cellSize, Board.getInstance().getNumColumns() * cellSize);
	}

	public BoardCell getCellAtClick() {
		if (lastClick == null) return null;

		BoardCell location = Board.getInstance().getCellAt((int) lastClick.getHeight() / cellSize, (int) lastClick.getWidth() / cellSize);
		return location; 
	}

	public void calculateCellSize(int numRows, int numColumns) {
		double maxHeight = this.getHeight() / numRows;
		double maxWidth = this.getWidth() / numColumns;

		cellSize = (maxHeight > maxWidth) ? (int) maxWidth : (int) maxHeight;
		doorSize = cellSize / 4;

	}

	private class ResizeListener extends ComponentAdapter {

		@Override
		public void componentResized(ComponentEvent e) {
			calculateCellSize(Board.getInstance().getNumRows(), Board.getInstance().getNumColumns());
			BoardDisplay.this.repaint();

		}
	}

	public class ClickListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			lastClick = new Dimension(e.getX(), e.getY());
			//System.out.println("Clicked: " + e.getX() + ", " + e.getY());

			//Board.getInstance().calcTargets(Board.getInstance().getPlayer(Board.getInstance().getHumanPlayer()).getLocation(), Board.getInstance().getRoll());
			BoardCell selection = Board.getInstance().getPlayer(Board.getInstance().getHumanPlayer()).pickLocation(Board.getInstance().getTargets());

			if (selection == null) {
				JOptionPane.showMessageDialog(null, "Invalid location selected!", "Error", JOptionPane.INFORMATION_MESSAGE );
				return;

			}

			for (BoardCell cell : Board.getInstance().getTargets()) cell.isSelected = false;
			Board.getInstance().getPlayer(Board.getInstance().getHumanPlayer()).move(selection);
			Board.getInstance().getGUI().repaint();

			BoardDisplay.this.removeMouseListener(listener);

			// Handle human player suggestions
			System.out.println("TODO handle human suggestions");

			Board.getInstance().getGUI().getControlPanel().updateFields();
			Board.getInstance().getGUI().getControlPanel().enableNextPlayerButton();

			/*synchronized(WaitThread.monitor) {
				WaitThread.monitor.notify(); 

			}*/
		}

		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}

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
		//		System.out.println("Coord X: " + maxCoordX);
		//		System.out.println("Coord Y: " + maxCoordY);
		//		
		//		System.out.println("Dim X: " + maxDimX);
		//		System.out.println("Dim Y: " + maxDimY);

		return new Dimension(maxCoordX + (maxDimX / 2), maxCoordY + (maxDimY / 2));
	}

	public Dimension getLastClick() { return lastClick; }

}

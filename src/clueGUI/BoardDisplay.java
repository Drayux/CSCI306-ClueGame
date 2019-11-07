package clueGUI;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import clueGame.Board;

public class BoardDisplay extends JPanel {
	
	private static final int SIZE = 50;
	
	public BoardDisplay() {}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawRect(20, 20, SIZE, SIZE);
		
	}

	@Override
	public Dimension getPreferredSize() {
		
		return new Dimension(Board.getInstance().getNumRows() * SIZE, Board.getInstance().getNumColumns() * SIZE);
	}
	
}

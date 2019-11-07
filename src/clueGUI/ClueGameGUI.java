package clueGUI;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;


//Put main in a separate class due to confusion of how to have both JFRAME / JPANEL in one class
public class ClueGameGUI extends JFrame {


	//Constructor for main window
	public ClueGameGUI() {

		// 800 by 300 for a longer skinny look 
		setSize(800,250); // could switch to 400 for more room
		setBackground(Color.GREEN); // added a background color for "Fun"
		setTitle("Clue Game"); // set title to generic "clue game"
		CPLayout();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}


	//make the  control panel (CP) layout
	private void CPLayout() {
		JPanel control = new ControlPanel();
		add(control, BorderLayout.SOUTH);

	}


	public static void main(String[] args) {

		JFrame gui = new ClueGameGUI();
		gui.setVisible(true);

	}
}

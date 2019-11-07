package clueGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;


//Put main in a separate class due to confusion of how to have both JFRAME / JPANEL in one class
public class ClueGameGUI extends JFrame {


	//Constructor for main window
	public ClueGameGUI() {

		// 800 by 300 for a longer skinny look 
		setSize(800, 800); // could switch to 400 for more room
		setBackground(Color.GREEN); // added a background color for "Fun"
		setTitle("Clue Game"); // set title to generic "clue game"
		ControlPanelLayout();
		BoardDisplayLayout();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());

	}


	// Make the control panel (CP) layout
	private void ControlPanelLayout() {
		JPanel control = new ControlPanel();
		add(control, BorderLayout.SOUTH);

	}
	
	// Make the board display layout
	private void BoardDisplayLayout() {
		JPanel display = new BoardDisplay();
		add(display, BorderLayout.CENTER);
		
	}
	
	private JMenu createFileMenu() {
		JMenu menu = new JMenu("File"); 
		menu.add(createFileExitItem());
		return menu;
	}

	private JMenuItem createFileExitItem() {
		JMenuItem item = new JMenuItem("Exit");
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				
			}
		}
		item.addActionListener(new MenuItemListener());

		return item;
	}


	public static void main(String[] args) {

		JFrame gui = new ClueGameGUI();
		gui.setVisible(true);

	}
}

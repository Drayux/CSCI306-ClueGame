package clueGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import clueGame.Board;
import clueGame.ConfigType;


//Put main in a separate class due to confusion of how to have both JFRAME / JPANEL in one class
public class ClueGameGUI extends JFrame {

JDialog detectiveNotes = null;

	//Constructor for main window
	public ClueGameGUI() {

		setSize(940, 940);
		setBackground(Color.GREEN); // added a background color for "Fun"
		setTitle("Clue"); // set title to generic "clue"
		setMinimumSize(new Dimension(674, 700));
		
		ControlPanelLayout();
		BoardDisplayLayout();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
		
		detectiveNotes = new DetectiveNotesGUI();
		detectiveNotes.setDefaultCloseOperation(HIDE_ON_CLOSE);

		CardPanel panel = new CardPanel();
		panel.updateCards();
		add(panel, BorderLayout.EAST);
	}

	// Make the control panel (CP) layout
	private void ControlPanelLayout() {
		JPanel control = new ControlPanel();
		this.add(control, BorderLayout.SOUTH);

	}
	
	// Make the board display layout
	private void BoardDisplayLayout() {
		JPanel display = new BoardDisplay();
		//display.setPreferredSize(display.getPreferredSize());
		this.add(display, BorderLayout.CENTER);
		
	}
	
	private JMenu createFileMenu() {
		JMenu menu = new JMenu("File"); 
		menu.add(openDetectiveNotes());
		menu.addSeparator();
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
	
	private JMenuItem openDetectiveNotes() {
		JMenuItem item = new JMenuItem("Detective Notes");
		
		class MenuItemListener implements ActionListener {
			
			public void actionPerformed(ActionEvent e) {
				
				detectiveNotes.setLocationRelativeTo(null);
				detectiveNotes.setVisible(true);
				
			}
		}
		
		
		
		item.addActionListener(new MenuItemListener());
		return item;
	}


	public static void main(String[] args) {
		// Prepare the board
		Board board = Board.getInstance();

		board.setConfig(ConfigType.BOARD, "config/board.csv");
		board.setConfig(ConfigType.LEGEND, "config/legend.txt");
		board.setConfig(ConfigType.PLAYER, "config/players.txt");
		board.setConfig(ConfigType.WEAPON, "config/weapons.txt");

		board.initialize();  // @Kevin, everything we need to add should go AFTER this, else we'll get null pointers galore.
		
		// Create the GUI
		JFrame gui = new ClueGameGUI();
		gui.setLocationRelativeTo(null);
		gui.setVisible(true);
		
		// Show the splash screen
		JOptionPane.showMessageDialog(null, "You are " + Board.getInstance().getPlayer(Board.getInstance().getHumanPlayer()).getName() + ", press Next Player to begin play", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE );

	}
}

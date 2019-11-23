package clueGUI;

import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.Solution;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;


public class ControlPanel extends JPanel {

	private JTextField response;
	
	private JLabel whoseTurnLabel;
	private JTextField whoseTurn;
	
	private JLabel diceRollLabel;
	private JTextField diceRoll;
	
	private JLabel guessLabel;
	private JTextField guess;
	
	private JLabel responseLabel;
	private JButton nextPlayer;
	
	private JButton makeAccusation;

	public ControlPanel() {

		//  Initialize J-Labels
		guessLabel = new JLabel("Guess");
		diceRollLabel = new JLabel("Roll");
		responseLabel = new JLabel("Response");
		whoseTurnLabel = new JLabel("Whose turn?");

		// Initialize J-Text fields
		whoseTurn = new JTextField(16);
		diceRoll = new JTextField(1);
		guess = new JTextField(31);
		response = new JTextField(16);

		// Configure J-Text fields
		whoseTurn.setEditable(false);
		diceRoll.setEditable(false);
		guess.setEditable(false);
		response.setEditable(false);

		// Initialize J-Button
		nextPlayer = new JButton("Next Player");
		//nextPlayer.setBackground(Color.BLUE);  // BackGround on buttons wont change?
		makeAccusation = new JButton("Make an accusation");

		nextPlayer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//System.out.println("TODO next player game flow!");
				nextPlayer.setEnabled(false);  // Disables the button until nextTurn() is finished (relevant for human player's turn)
				
				if (!Board.getInstance().nextTurn()) {
					updateFields();
					nextPlayer.setEnabled(true);
					
				} else {
					updateFields();
					Board.getInstance().calcTargets(Board.getInstance().getPlayer(Board.getInstance().getHumanPlayer()).getLocation(), Board.getInstance().getRoll());
					
					for (BoardCell cell : Board.getInstance().getTargets()) cell.isSelected = true;
					Board.getInstance().getGUI().repaint();
					
					Board.getInstance().getGUI().getBoardDisplay().addMouseListener(Board.getInstance().getGUI().getBoardDisplay().listener);
					
				}
			}
		});
		
		makeAccusation.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("TODO make accusation functionality!");
				
			}
		});

		this.setLayout(new BorderLayout());

		// Top Buttons for next player and make accusations /  whose turn
		JPanel topButtons = new JPanel();
		// J panel object for the lower text boxes in the bottom along with DIE ROLL
		JPanel textBoxes = new JPanel();

		topButtons.setLayout(new GridLayout(1, 0));
		topButtons = createButtonPanel(topButtons);

		textBoxes.setLayout(new FlowLayout());
		textBoxes = fillInPanel(textBoxes);

		//set up the dimensions 
		topButtons.setPreferredSize(new Dimension(200, 100));
		textBoxes.setPreferredSize(new Dimension(200, 100));
		//where to put buttons and text boxes on the screen 
		this.add(textBoxes, BorderLayout.SOUTH);
		this.add(topButtons, BorderLayout.NORTH);
		
		//updateFields();

	}
	
	public void updateFields() {
		int currentTurn = Board.getInstance().getTurn();
		if (currentTurn < 0) return;
		
		whoseTurn.setText(Board.getInstance().getPlayer(currentTurn).getName());
		diceRoll.setText(Integer.toString(Board.getInstance().getRoll()));
		
		Solution currentSuggestion = Board.getInstance().getPlayerSuggestion();
		Card currentEvidence = Board.getInstance().getPlayerEvidence();
		
		guess.setText((currentSuggestion == null) ? "No guess!" : currentSuggestion.getPerson().getName() + ", " + currentSuggestion.getRoom().getName() + ", " + currentSuggestion.getWeapon().getName());
		
		if (currentSuggestion == null) response.setText("Nothing to disprove!");
		else response.setText((currentEvidence == null) ? "Suggestion cannot be disproved!" : currentEvidence.getName());
		
	}

	private JPanel fillInPanel(JPanel fillPanel) {

		// Create JPanel Object for the DIEROLL box called diceBox
		JPanel diceBox = new JPanel();
		diceBox.setLayout(new GridLayout(1,2)); // first set the layout of the box
		diceBox.setBorder(new TitledBorder(new EtchedBorder(), "Die")); // next we want to set the boarder and TXT
		diceBox.add(diceRoll); // add the JTEXTFIELD 
		diceBox.add(diceRollLabel); //add the JLabel 
		// add all of the "pieces" for our diceBox object to be filled onto the board with the correct layout
		fillPanel.add(diceBox, FlowLayout.LEFT);

		// Create JPanel Object for the Guess field box called guessBox
		JPanel guessBox = new JPanel(); 
		guessBox.setLayout(new GridLayout(2,2)); // first set the layout of the box
		guessBox.setBorder(new TitledBorder(new EtchedBorder(), "Guess")); //next we want to set the board and TXT
		guessBox.add(guess); // added the JTEXTfield 
		guessBox.add(guessLabel); // added the JLabel field
		// add all of the "pieces" from guess box to be filled onto the panel 
		fillPanel.add(guessBox, FlowLayout.CENTER);


		JPanel responseBox = new JPanel();
		responseBox.setLayout(new GridLayout(2,1));
		responseBox.setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));
		responseBox.add(response); // add the JTEXT field 
		responseBox.add(responseLabel); // add the Jlabel 
		// a
		fillPanel.add(responseBox, FlowLayout.RIGHT);
		return fillPanel;

	}

	//setting up the format of whose turn and adding buttons
	private JPanel createButtonPanel(JPanel fillButton) {

		JPanel whoTurn = new JPanel();
		whoTurn.setLayout(new GridLayout(5, 1)); // setting the size for the "whose turn" box
		whoTurn.add(whoseTurnLabel);
		whoTurn.add(whoseTurn);

		fillButton.add(whoTurn);
		fillButton.add(nextPlayer);
		fillButton.add(makeAccusation);
		return fillButton;
		
	}

	public void enableNextPlayerButton() { nextPlayer.setEnabled(true); }
	
}
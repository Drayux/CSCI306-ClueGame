package clueGUI;

import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;


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
		
		//  Declare J-Labels
		guessLabel = new JLabel("Guess");
		diceRollLabel = new JLabel("Roll");
		responseLabel = new JLabel("Response");
		whoseTurnLabel = new JLabel("Whose turn?");
		
		// Declare J-Text fields
		whoseTurn = new JTextField(16);
		diceRoll = new JTextField(1);
		guess = new JTextField(31);
		response = new JTextField(16);
		
		// Configure J-Text fields
		whoseTurn.setEditable(false);
		diceRoll.setEditable(false);
		guess.setEditable(false);
		response.setEditable(false);
		
		// Declare J-Button
		nextPlayer = new JButton("Next Player");
		//nextPlayer.setBackground(Color.BLUE);  // BackGround on buttons wont change?
		makeAccusation = new JButton("Make an accusation");
		
		
		this.setLayout(new BorderLayout());
		
		//Top Buttons for next player and make accusations /  whose turn
		JPanel topButtons = new JPanel();
		// J panel object for the lower text boxes in the bottom along with DIE ROLL
		JPanel textBoxes = new JPanel();
		
		topButtons.setLayout(new GridLayout(1,0));
		topButtons = createButtonPanel(topButtons);
		
		textBoxes.setLayout(new FlowLayout());
		textBoxes = fillInPanel(textBoxes);
		
		//set up the dimensions 
		topButtons.setPreferredSize(new Dimension(200, 100));
		textBoxes.setPreferredSize(new Dimension(200, 100));
		//where to put buttons and text boxes on the screen 
		this.add(textBoxes, BorderLayout.SOUTH);
		this.add(topButtons, BorderLayout.NORTH);
			
		
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
		whoTurn.setLayout(new GridLayout(5,1)); // setting the size for the "whose turn" box
		whoTurn.add(whoseTurnLabel);
		whoTurn.add(whoseTurn);
		
		fillButton.add(whoTurn);
		fillButton.add(nextPlayer);
		fillButton.add(makeAccusation);
		return fillButton;
		}
	
	
	

	

}
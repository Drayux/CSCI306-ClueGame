package clueGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class DetectiveNotesGUI extends JFrame {
	
	public DetectiveNotesGUI() {
		setSize(new Dimension(600,600));
		setTitle("Detective Notes");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//create DisplayPanel 
		PeoplePanel pp = new  PeoplePanel();
		add(pp, BorderLayout.NORTH);
		
		RoomsPanel rp = new RoomsPanel();
		add(rp, BorderLayout.CENTER);
		

				
	}
	
	
	private class PeoplePanel extends JPanel {
	
		private JRadioButton scarletButton, greenButton, peacockButton, whiteButton, mustardButton, plumButton;
		
		public PeoplePanel() {
			
			scarletButton = new JRadioButton("Miss Scarlet"); 
			greenButton = new JRadioButton("Mr. Green"); 
			peacockButton = new JRadioButton("Mrs. Peacock"); 
			whiteButton = new JRadioButton("Mrs. White"); 
			mustardButton = new JRadioButton("Colonel Mustard"); 
			plumButton = new JRadioButton("Professor Plum"); 
			
			add(scarletButton);
			add(greenButton);
			add(peacockButton);
			add(whiteButton); 
			add(mustardButton); 
			add(plumButton);
			
			ButtonGroup group = new ButtonGroup();
			group.add(scarletButton);
			group.add(greenButton);
			group.add(peacockButton);
			group.add(whiteButton);
			group.add(mustardButton);
			group.add(plumButton);
			//set border
			setBorder(new TitledBorder ( new EtchedBorder(), "People"));
			setLayout( new GridLayout(3,2));
			
		}
		
	}
	
	
	
	private class RoomsPanel extends JPanel {
		
		private JRadioButton kitchenButton, asylumButton, butchersbedroomButton, hotboxButton, entryButton, studioButton, diningroomButton, arenaButton, underwearrepairButton;
		
		public RoomsPanel() {
			
			kitchenButton = new JRadioButton("Kitchen"); 
			asylumButton = new JRadioButton("Asylum"); 
			butchersbedroomButton = new JRadioButton("Butcher's Bedroom"); 
			hotboxButton = new JRadioButton("Hotbox"); 
			entryButton = new JRadioButton("Entry"); 
			studioButton = new JRadioButton("Studio");
			diningroomButton = new JRadioButton("Dining Room"); 
			arenaButton = new JRadioButton("Arena"); 
			underwearrepairButton = new JRadioButton("Underwear Repair"); 
			
			add(kitchenButton);
			add(asylumButton);
			add(butchersbedroomButton);
			add(hotboxButton); 
			add(entryButton); 
			add(studioButton);
			add(diningroomButton);
			add(arenaButton);
			add(underwearrepairButton);
			
			ButtonGroup group = new ButtonGroup();
			group.add(kitchenButton);
			group.add(asylumButton);
			group.add(butchersbedroomButton);
			group.add(hotboxButton);
			group.add(entryButton);
			group.add(studioButton);
			group.add(diningroomButton);
			group.add(arenaButton);
			group.add(underwearrepairButton);
			//set border
			setBorder(new TitledBorder ( new EtchedBorder(), "Rooms"));
			setLayout( new GridLayout(5,2));
			
		}
		
	}

}

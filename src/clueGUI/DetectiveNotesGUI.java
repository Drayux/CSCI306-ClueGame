package clueGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
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
		
		RoomPanel rp = new RoomPanel();
		add(rp, BorderLayout.CENTER);
		
		WeaponPanel wp = new WeaponPanel();
		add(wp, BorderLayout.SOUTH);
		

				
	}
	
	
	private class PeoplePanel extends JPanel {
	
		private JCheckBox scarletButton, greenButton, peacockButton, whiteButton, mustardButton, plumButton;
		
		public PeoplePanel() {
			
			scarletButton = new JCheckBox("Miss Scarlet"); 
			greenButton = new JCheckBox("Mr. Green"); 
			peacockButton = new JCheckBox("Mrs. Peacock"); 
			whiteButton = new JCheckBox("Mrs. White"); 
			mustardButton = new JCheckBox("Colonel Mustard"); 
			plumButton = new JCheckBox("Professor Plum"); 
			
			add(scarletButton);
			add(greenButton);
			add(peacockButton);
			add(whiteButton); 
			add(mustardButton); 
			add(plumButton);

			//set border
			setBorder(new TitledBorder ( new EtchedBorder(), "People"));
			setLayout( new GridLayout(3,2));
			
		}
		
	}
	
	
	
	private class RoomPanel extends JPanel {
		
		private JCheckBox kitchenButton, asylumButton, butchersbedroomButton, hotboxButton, entryButton, studioButton, diningroomButton, arenaButton, underwearrepairButton;
		
		public RoomPanel() {
			
			kitchenButton = new JCheckBox("Kitchen"); 
			asylumButton = new JCheckBox("Asylum"); 
			butchersbedroomButton = new JCheckBox("Butcher's Bedroom"); 
			hotboxButton = new JCheckBox("Hotbox"); 
			entryButton = new JCheckBox("Entry"); 
			studioButton = new JCheckBox("Studio");
			diningroomButton = new JCheckBox("Dining Room"); 
			arenaButton = new JCheckBox("Arena"); 
			underwearrepairButton = new JCheckBox("Underwear Repair"); 
			
			add(kitchenButton);
			add(asylumButton);
			add(butchersbedroomButton);
			add(hotboxButton); 
			add(entryButton); 
			add(studioButton);
			add(diningroomButton);
			add(arenaButton);
			add(underwearrepairButton);
			

			//set border
			setBorder(new TitledBorder ( new EtchedBorder(), "Rooms"));
			setLayout( new GridLayout(5,2));
			
		}
		
	}
	
	
	
	
	private class WeaponPanel extends JPanel {
		
		private JCheckBox lightSaberButton, plasmaRifleButton, throwingHatchetButton, throwingKnifeButton, scar17Button, chainSawButton;
		
		public WeaponPanel() {
			
			lightSaberButton = new JCheckBox("Light Saber"); 
			plasmaRifleButton = new JCheckBox("Plasma Rifle"); 
			throwingHatchetButton = new JCheckBox("Throwing Hatchet"); 
			throwingKnifeButton = new JCheckBox("Throwing Knife");
			scar17Button = new JCheckBox("SCAR 17-H"); 
			chainSawButton = new JCheckBox("Chain Saw");
 
			
			add(lightSaberButton);
			add(plasmaRifleButton);
			add(throwingHatchetButton);
			add(throwingKnifeButton); 
			add(scar17Button); 
			add(chainSawButton);
		

			//set border
			setBorder(new TitledBorder ( new EtchedBorder(), "Weapons"));
			setLayout( new GridLayout(3,2));
			
		}
		
	}
	
	
	private class PersonDropbox extends JPanel {
		
		private JComboBox <String> scarletButton, greenButton, peacockButton, whiteButton, mustardButton, plumButton;
		
		PersonDropbox() {
			
		}
	}
	
	

}

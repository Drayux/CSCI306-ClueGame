package clueGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Board;
import clueGame.Card;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class DetectiveNotesGUI extends JDialog {
	
	private String[] people;
	private String[] rooms;
	private String[] weapons;
	
	public DetectiveNotesGUI() {
		setSize(new Dimension(600, 600));
		setTitle("Detective Notes");
		setLayout(new GridLayout(3, 2));
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		people = new String[Board.MAX_PLAYERS_COUNT];
		rooms = new String[Board.MAX_ROOMS_COUNT];
		weapons = new String[Board.MAX_WEAPONS_COUNT];
		
		int[] counts = { 0, 0, 0 };
		
		for (Card card : Board.getInstance().getGameCards()) {
			switch (card.getType()) {
			case PERSON:
				people[counts[0]] = card.getName();
				counts[0]++;
				break;
				
			case ROOM:
				rooms[counts[1]] = card.getName();
				counts[1]++;
				break;
			
			case WEAPON:
				weapons[counts[2]] = card.getName();
				counts[2]++;
				break;
				
			}
		}
		
		PeoplePanel peoplePanel = new PeoplePanel();
		add(peoplePanel);

		dropDownMenu peopleDD = new dropDownMenu(people, "Person Guess");
		add(peopleDD);

		RoomPanel roomPanel = new RoomPanel();
		add(roomPanel);

		dropDownMenu roomDD = new dropDownMenu(rooms, "Room Guess");
		add(roomDD);

		WeaponPanel weaponPanel = new WeaponPanel();
		add(weaponPanel);

		dropDownMenu weaponDD = new dropDownMenu(weapons, "Weapon Guess");
		add(weaponDD);
	
	}
	
	private class dropDownMenu extends JPanel {
		private JComboBox<String> combo = new JComboBox<String>();
		
		public dropDownMenu(String[] nameList, String label) {
			combo.addItem("Guess an option from the list");
			for (String s:nameList) {
				combo.addItem(s);
				
			}
			
			setLayout(new GridLayout(1,1));
			setBorder(new TitledBorder(new EtchedBorder(), label));
			add(combo);
			
		}
	}
	
	private class PeoplePanel extends JPanel {
		
		public PeoplePanel() {

			for (String string : people) add(new JCheckBox(string));
			
			//set border
			setBorder(new TitledBorder (new EtchedBorder(), "People"));
			setLayout(new GridLayout(3, 2));
			
		}
		
	}
	
	
	
	private class RoomPanel extends JPanel {
				
		public RoomPanel() {
		
			for (String string : rooms) add(new JCheckBox(string));

			//set border
			setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
			setLayout(new GridLayout(5, 2));
			
		}
		
	}
	
	private class WeaponPanel extends JPanel {
		
		public WeaponPanel() {
			
			for (String string : weapons) add(new JCheckBox(string));

			//set border
			setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
			setLayout(new GridLayout(3, 2));
			
		}
	}
}

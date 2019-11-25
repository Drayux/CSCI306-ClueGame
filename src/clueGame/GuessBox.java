package clueGame;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

//Dialog box for making suggestions and accusations
public class GuessBox extends JDialog{

	private DropDown pickPerson;
	private DropDown pickWeapon;
	private DropDown pickRoom;
	private JButton cancel;
	private JButton submit;
	private JTextField roomBox;
	private Solution guess = new Solution();


	//constructor for accusation
	public GuessBox() {
		setModal(true);
		setTitle("Make an Accusation");
		setSize(new Dimension(400, 200));
		setLayout(new GridLayout(4,2));
		String[] people = { "Colonel Mustard", "Miss Scarlet", "Mr Green" , "Mrs Peacock", "Mrs. White" , "Professor Plum"};
		String[] weapons = {"Light Saber", "Plasma Rifle", "Throwing Hatchet", "Rusty Knife", "SCAR 17-H", "Chain saw"};
		String[] rooms = {"Asylum", "Kitchen", "Living Room", "Butcher's Bedroom", "Hotbox", "Studio", "Dining Room", "Arena", "Underwear Repair"};


		JLabel yourRoom = new JLabel("Your room");
		pickRoom = new DropDown(rooms);

		this.add(yourRoom);
		this.add(pickRoom);

		JLabel personLabel = new JLabel("Person");
		pickPerson = new DropDown(people);

		this.add(personLabel);
		this.add(pickPerson);

		JLabel weaponLabel = new JLabel("Weapon");
		pickWeapon = new DropDown(weapons);

		this.add(weaponLabel);
		this.add(pickWeapon);

		submit = new JButton("Submit");
		cancel = new JButton("Cancel");
		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				
			}

		});
		cancel.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			} 
			
			
		});
			
		
		this.add(submit);
		this.add(cancel);



	

	}



	//constructor for suggestion

	public GuessBox(BoardCell room) {
		setModal(true);
		setTitle("Make a Guess");
		setSize(new Dimension(400, 200));
		setLayout(new GridLayout(4,2));
		String[] people = { "Colonel Mustard", "Miss Scarlet", "Mr Green" , "Mrs Peacock", "Mrs. White" , "Professor Plum"};
		String[] weapons = {"Light Saber", "Plasma Rifle", "Throwing Hatchet", "Rusty Knife", "SCAR 17-H", "Chain saw"};

		JLabel yourRoom = new JLabel("Your room");
		roomBox = new JTextField(20);
		roomBox.setEditable(false);
		roomBox.setText(Board.getInstance().getAssociatedRoomCard(room).getName());

		this.add(yourRoom);
		this.add(roomBox);

		JLabel personLabel = new JLabel("Person");
		pickPerson = new DropDown(people);

		this.add(personLabel);
		this.add(pickPerson);

		JLabel weaponLabel = new JLabel("Weapon");
		pickWeapon = new DropDown(weapons);

		this.add(weaponLabel);
		this.add(pickWeapon);

		submit = new JButton("Submit");
		
		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			guess.setRoom(Board.getInstance().getAssociatedRoomCard(room));
			guess.setPerson(Board.getInstance().getAssociatedCard(pickPerson.getName()));
			guess.setWeapon(Board.getInstance().getAssociatedCard(pickWeapon.getName()));
			
			setVisible(false);
			
			System.out.println(guess.getPerson().getName());
			
			}

		});
		
			

		this.add(submit);
	



	}

	//constructs a combobox based on an array of strings
	private class DropDown extends JComboBox {
		public DropDown(String[] nameList) {
			for (String s:nameList) {
				this.addItem(s);
			}
		}
	}




}



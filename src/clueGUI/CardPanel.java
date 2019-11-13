package clueGUI;


import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Card;


	public class CardPanel extends JPanel {
	
		CardPanel() {
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		setBorder(new TitledBorder(new EtchedBorder(), "My Cards"));
		
		DisplayCards weapons = new DisplayCards("Weapons");
		DisplayCards rooms = new DisplayCards("Rooms");
		DisplayCards people = new DisplayCards("People");
		
		//I believe we need some type of for loop to populate cards 
		
		
		
		
		add(weapons);
		add(rooms);
		add(people);
		
		
			
		}
		
		
		private class CardOnebyOne extends JPanel {
			
			public CardOnebyOne(Card x) {
				
				JLabel cardName = new JLabel(x.getName());
				setBackground(Color.GREEN);
				setLayout(new GridLayout());
				setBorder(BorderFactory.createLineBorder(Color.GREEN));
				add(cardName);
				
				
			}
		}
		
		
		private class DisplayCards extends JPanel{
			
			public DisplayCards( String typeOfCard ) {
				setBorder(new TitledBorder(new EtchedBorder(), typeOfCard));
				setLayout(new GridLayout(0,5, 30, 30));
			}
			
		}

	
		}



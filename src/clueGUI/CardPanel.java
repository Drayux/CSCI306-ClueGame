package clueGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Board;
import clueGame.Card;

public class CardPanel extends JPanel {

	public static final int cardHeight = 64;
	public static final int cardWidth = 128;

	DisplayCards people;
	DisplayCards rooms;
	DisplayCards weapons;

	public CardPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(new TitledBorder(new EtchedBorder(), "My Cards"));

		people = new DisplayCards("People");
		rooms = new DisplayCards("Rooms");
		weapons = new DisplayCards("Weapons");

		add(people);
		add(rooms);
		add(weapons);

	}

	public void updateCards() {
		people.cards = new ArrayList<Card>();
		rooms.cards = new ArrayList<Card>();
		weapons.cards = new ArrayList<Card>();

		for (Card card : Board.getInstance().getPlayer(Board.getInstance().getHumanPlayer()).getHand()) {
			switch (card.getType()) {
			case PERSON:
				people.cards.add(card);
				break;

			case ROOM:
				rooms.cards.add(card);
				break;

			case WEAPON:
				weapons.cards.add(card);
				break;

			default:
				break;
				
			}
		}

		people.setMaximumSize(new Dimension(cardWidth + 16, (people.cards.size() == 0) ? cardHeight + 28 : (cardHeight + 4) * people.cards.size() + 28));
		rooms.setMaximumSize(new Dimension(cardWidth + 16, (rooms.cards.size() == 0) ? cardHeight + 28 : (cardHeight + 4) * rooms.cards.size() + 28));
		weapons.setMaximumSize(new Dimension(cardWidth + 16, (weapons.cards.size() == 0) ? cardHeight + 28 : (cardHeight + 4) * weapons.cards.size() + 28));

		people.repaint();
		rooms.repaint();
		weapons.repaint();

	}

	private class DisplayCards extends JPanel {

		public ArrayList<Card> cards = new ArrayList<Card>();

		public DisplayCards(String title) {
			setBorder(new TitledBorder(new EtchedBorder(), title));

		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			int count = 0;
			
			for (Card card : cards) {
				g.setColor(new Color(250, 250, 250, 255));
				g.fillRoundRect(8, (cardHeight + 4) * count + 20, cardWidth, cardHeight, 8, 8);
				
				g.setColor(new Color(0, 0, 0, 255));
				g.drawRoundRect(8, (cardHeight + 4) * count + 20, cardWidth, cardHeight, 8, 8);
				
				g.drawString(card.getName(), 16, (int) ((cardHeight + 4) * count + (cardHeight / 2) + (g.getFontMetrics().getHeight() / 4) + 20));
				
				count++;
				
			}

		}

		@Override
		public Dimension getPreferredSize() {

			return new Dimension(cardWidth + 16, cardHeight + 28);
		}

	}
}



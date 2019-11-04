package clueGame;

public class Card {

	CardType type;
	String name;
	
	public Card(CardType cardType, String cardName) {
		type = cardType;
		name = cardName;
		
	}
	
	public boolean equals(Card c) {
		if (!(c.getType() == type)) return false;
		if (!(c.getName().equals(name))) return false;
		
		return true;
	}

	public CardType getType() { return type; }
	public String getName() { return name; }
	
}

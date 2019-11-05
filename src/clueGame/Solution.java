package clueGame;

public class Solution {
		
	private Card person;
	private Card room;
	private Card weapon;
	
	public Card getPerson() {
	
		return person;
	}

	public Card getRoom() {
		
		return room;
	}

	public Card getWeapon() {
		
		return weapon;
	}
	
	public void setPerson(Card c) {
		person = c;
		
	}

	public void setRoom(Card c) {
		room = c;
		
	}

	public void setWeapon(Card c) {
		weapon = c;
		
	}
	
	public boolean equals(Solution s) {
		if (!(person.equals(s.getPerson()))) return false; 
		if (!(room.equals(s.getRoom()))) return false; 
		if (!(weapon.equals(s.getWeapon()))) return false; 
		
		return true;
	}
	
}

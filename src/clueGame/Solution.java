package clueGame;

public class Solution {
	// Attributes
	private Card person;
	private Card weapon;
	private Card room;

	// Constructor
	public Solution(Card person, Card weapon, Card room) {
		this.person = person;
		this.weapon = weapon;
		this.room = room;
	}

	//
	public int size() {
		return 3;
	}

	// Getters and setters
	public Card getPerp() {
		return this.person;
	}

	public Card getWeapon() {
		// TODO Auto-generated method stub
		return this.weapon;
	}

	public Card getPlace() {
		// TODO Auto-generated method stub
		return this.room;
	}

}

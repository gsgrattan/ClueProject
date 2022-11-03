package clueGame;

public class Solution {
	private Card person;
	private Card weapon;
	private Card room;

	public Solution(Card person, Card weapon, Card room) {
		this.person = person;
		this.weapon = weapon;
		this.room = room;
	}

	public int size() {
		return 3;
	}

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

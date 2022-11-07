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
	public Card getPerson() {
		return this.person;
	}

	public Card getWeapon() {
		// TODO Auto-generated method stub
		return this.weapon;
	}

	public Card getRoom() {
		// TODO Auto-generated method stub
		return this.room;
	}

	public void setPerson(Card person) {
		this.person = person;
	}

	public void setWeapon(Card weapon) {
		this.weapon = weapon;
	}

	public void setRoom(Card room) {
		this.room = room;
	}

	// Return true if two solutions have the same value
	public boolean equals(Solution solution) {
		boolean rightPerson = this.person.equals(solution.person);
		boolean rightWeapon = this.weapon.equals(solution.weapon);
		boolean rightRoom = this.room.equals(solution.room);
		return (rightPerson && rightWeapon && rightRoom);
	}

}

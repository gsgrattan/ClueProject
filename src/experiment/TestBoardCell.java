package experiment;
import java.util.*;
import types.*;


public class TestBoardCell {
	private final static int NUM_ADJACENT = 8;
	private int row;
	private int column;
	
	private boolean isOccupied = false;
	private boolean isRoom = false;
	
	private Set<TestBoardCell> adjacencyList;
	
	//Constructor
	public TestBoardCell(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	
	//Getters and Setters
	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
	
	public boolean getOccupied() {
		return this.isOccupied;
	}
	
	public void setRoom(boolean isRoom) {
		this.isRoom = isRoom;
	}
	
	public boolean getRoom() {
		return this.isRoom;
		
	}
	public Set<TestBoardCell> getAdjList(){
		
		return adjacencyList;	
	}
	
	
	//Other methods
	public void addAdjacency(TestBoardCell cell) {
		
	}
	
	
}

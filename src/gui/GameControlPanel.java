package gui;
import clueGame.*;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameControlPanel extends JPanel {
	
	private String name, guess, guessResult;
	private int roll;
	
	/**
	 * Constructor for the panel, it does 90% of the work
	 */
	
	public GameControlPanel()  {
		name = "huh";
		guess = "who";
		guessResult = "ME";
		roll = 69;
		
		this.setLayout(new GridLayout(2,0));
		
		PlayerControlPanel yeet = new PlayerControlPanel();
		this.add(yeet, BorderLayout.EAST);
		
		
		
	
	}
	
	
	/**
	 * Main to test the panel
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		PlayerControlPanel yeet = new PlayerControlPanel();
		
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		
		// test filling in the data
		//panel.setTurn(new ComputerPlayer( "Col. Mustard", 0, 0, "orange"), 5);
		panel.setGuess( "I have no guess!");
		panel.setGuessResult( "So you have nothing?");
	}


	private void setGuessResult(String string) {
		// TODO Auto-generated method stub
		
	}


	private void setGuess(String string) {
		// TODO Auto-generated method stub
		
	}


	private void setTurn(ComputerPlayer computerPlayer, int i) {
		// TODO Auto-generated method stub
		
	}
}
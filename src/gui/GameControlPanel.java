package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import clueGame.Board;
import clueGame.ComputerPlayer;

public class GameControlPanel extends JPanel {

	private String name, guess, guessResult;
	private int roll, turn;

	private PlayerControlPanel playerControl;
	private GuessPanel guessPanel;

	/**
	 * Constructor for the panel, it does 90% of the work
	 */

	public GameControlPanel() {
		name = "huh";
		guess = "who";
		guessResult = "ME";
		roll = 69;

		this.setLayout(new GridLayout(2, 0));

		playerControl = new PlayerControlPanel();
		this.add(playerControl, BorderLayout.NORTH);

		guessPanel = new GuessPanel();

		this.add(guessPanel, BorderLayout.SOUTH);

	}

	/**
	 * Main to test the panel
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel(); // create the panel
		JFrame frame = new JFrame(); // create the frame

		Board board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();

		frame.setContentPane(panel); // put the panel in the frame

		frame.setSize(750, 180); // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible

		// test filling in the data
		panel.setTurn(new ComputerPlayer("Col. Mustard", board, board.getCell(0, 0), Color.blue), 5);
		panel.setGuess("I have no guess!");
		panel.setGuessResult("So you have nothing?");
	}

	private void setGuess(String string) {
		this.guess = string;
		guessPanel.setGuess(string);
	}

	private void setGuessResult(String string) {
		this.guessResult = string;
		guessPanel.setGuessResult(string);

	}

	private void setTurn(ComputerPlayer computerPlayer, int i) {
		this.playerControl.setTurn(computerPlayer, i);

		// TODO Auto-generated method stub

	}
}
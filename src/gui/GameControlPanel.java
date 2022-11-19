package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

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
	 * 
	 * @param board TODO
	 */

	public GameControlPanel(Board board) {
		name = "huh";
		guess = "who";
		guessResult = "ME";
		roll = 69;

		this.setLayout(new GridLayout(2, 0));

		playerControl = new PlayerControlPanel(board);
		this.add(playerControl, BorderLayout.NORTH);

		guessPanel = new GuessPanel();

		this.add(guessPanel, BorderLayout.SOUTH);

	}

	/**
	 * Main to test the panel
	 * 
	 * @param args
	 */

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
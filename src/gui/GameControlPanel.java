package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;

import clueGame.Board;

public class GameControlPanel extends JPanel {

	private PlayerControlPanel playerControl;
	private GuessPanel guessPanel;

	/**
	 * Constructor for the panel, it does 90% of the work
	 * 
	 * @param board TODO
	 */

	public GameControlPanel(Board board) {

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

	public void setGuess(String string) {
		guessPanel.setGuess(string);
	}

	public void setGuessResult(String string) {
		guessPanel.setGuessResult(string);

	}

}
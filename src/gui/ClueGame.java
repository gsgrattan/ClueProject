package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import clueGame.Board;

public class ClueGame extends JFrame {

	private static ClueGame INSTANCE = new ClueGame();
	private static Board board;
	private ClueGamePanel gamePanel;

	private ClueGame() {
		this.setSize(750, 650);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Create the cluegame Panel within the Jframe
		gamePanel = new ClueGamePanel();

		this.add(gamePanel, BorderLayout.CENTER);

		this.setTitle("Clue Game - CSCI306");

	}

	public static void main(String[] args) {

		INSTANCE.setVisible(true);

	}

	public static ClueGame getInstance() {
		return INSTANCE;
	}

	public ClueGamePanel getClueGamePanel() {
		return this.gamePanel;
	}

	public static void endGame() {
		INSTANCE.setVisible(false);
		INSTANCE.dispose();
		System.exit(0);

	}

	public void newGame() {
		// Set it to false
		INSTANCE.setVisible(false);
		// Create a new game
		INSTANCE = new ClueGame();

		// Make it visible
		INSTANCE.setVisible(true);

	}

}

package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import clueGame.Board;

public class ClueGame extends JFrame {

	private static ClueGame INSTANCE = new ClueGame();
	private static Board board;

	private ClueGame() {
		this.setSize(750, 650);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Create the cluegame Panel within the Jframe
		ClueGamePanel test = new ClueGamePanel();

		this.add(test, BorderLayout.CENTER);

		this.setTitle("Clue Game - CSCI306");

	}

	public static void main(String[] args) {
		ClueGame test = INSTANCE;

		test.setVisible(true);

	}

	public static ClueGame getInstance() {
		return INSTANCE;
	}
}

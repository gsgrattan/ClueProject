package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import clueGame.Board;

public class ClueGame extends JFrame {
	private static Board board;

	public ClueGame() {
		this.setSize(750, 650);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ClueGamePanel test = new ClueGamePanel();

		this.add(test, BorderLayout.CENTER);

	}

	public static void main(String[] args) {
		ClueGame testes = new ClueGame();
		testes.setVisible(true);

	}
}

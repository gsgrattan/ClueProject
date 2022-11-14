package gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import clueGame.Board;

public class ClueGamePanel extends JPanel {
	private static Board board;
	private static GameControlPanel controlPanel;
	private static CardPanel cardPanel;

	public ClueGamePanel() {
		board = Board.getInstance();

		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		board.deal();

		this.setLayout(new BorderLayout());

		this.controlPanel = new GameControlPanel();
		this.cardPanel = new CardPanel(board.getHumanPlayer());

		this.add(board, BorderLayout.CENTER);
		this.add(controlPanel, BorderLayout.PAGE_END);
		this.add(cardPanel, BorderLayout.LINE_END);

	}
}

package gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import clueGame.Board;

public class ClueGamePanel extends JPanel {
	private static Board board;
	private static GameControlPanel controlPanel;
	private static CardPanel cardPanel;

	public ClueGamePanel() {
		// create the board, initialize, and get the instance
		board = Board.getInstance();

		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		board.deal();

		// sett the layout so that the panels behave well together

		this.setLayout(new BorderLayout());

		// Create the controlpanel and card panel
		this.controlPanel = new GameControlPanel();
		this.cardPanel = new CardPanel(board.getHumanPlayer());

		// Add the three different panels to the gamepanel
		this.add(board, BorderLayout.CENTER);
		this.add(controlPanel, BorderLayout.PAGE_END);
		this.add(cardPanel, BorderLayout.LINE_END);
	}
}

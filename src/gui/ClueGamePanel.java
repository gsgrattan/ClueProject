package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import clueGame.Board;

public class ClueGamePanel extends JPanel {
	private Board board;
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
		this.controlPanel = new GameControlPanel(board);
		this.cardPanel = new CardPanel(board.getHumanPlayer());

		// Add the three different panels to the gamepanel
		this.add(board, BorderLayout.CENTER);
		this.add(controlPanel, BorderLayout.PAGE_END);
		this.add(cardPanel, BorderLayout.LINE_END);

		// Do the splash panel

		JOptionPane splash = new JOptionPane();
		JFrame splashFrame = new JFrame();

		splash.showMessageDialog(splashFrame,
				"You are " + board.getHumanPlayer().getName()
						+ "!\nCan you find the solution before the Computer Players" + "?",
				"Welcome to ClueCar", JOptionPane.INFORMATION_MESSAGE);
	}
}

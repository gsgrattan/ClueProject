package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import clueGame.Board;

public class PlayerControlPanel extends JPanel {
	private JButton makeAccusation, next;
	private JPanel currentPlayer, rollPanel;
	private JLabel turnLabel, rollLabel;
	private JTextField playerName, roll;
	private Board board;

	public PlayerControlPanel(Board board) {

		super();
		this.board = board;

		this.setLayout(new GridLayout(1, 4));

		// First panel
		currentPlayer = new JPanel();

		this.add(currentPlayer, BorderLayout.PAGE_START);

		turnLabel = new JLabel("Whose turn?");
		currentPlayer.add(turnLabel, BorderLayout.NORTH);

		playerName = new JTextField(15);

		// Set the Background color
		playerName.setText(board.getPlayers().get(board.getCurrentPlayerTurn()).getName());
		Color temp = board.getPlayers().get(board.getCurrentPlayerTurn()).getColor();

		playerName.setBackground(new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), temp.getAlpha()));
		playerName.setEditable(false);

		// playerName.setEditable(false);

		currentPlayer.add(playerName, BorderLayout.SOUTH);

		// Second panel
		rollPanel = new JPanel();
		this.add(rollPanel);

		rollLabel = new JLabel("Roll: ");
		rollPanel.add(rollLabel);

		roll = new JTextField(5);
		roll.setText(String.valueOf(board.getCurrRoll()));
		roll.setEditable(false);
		roll.setSize(1, 1);
		rollPanel.add(roll);

		// Third panel
		makeAccusation = new JButton("Make Accusation");
		makeAccusation.addActionListener(new ButtonListenerAccustaion(this.board));
		this.add(makeAccusation);

		// Fourth panel
		next = new JButton("NEXT!");
		next.addActionListener(new ButtonListenerNext(this.board, this.roll));
		this.add(next);

	}

	// ButtonListener for the Next button
	class ButtonListenerNext implements ActionListener {
		private Board board;
		private JTextField roll;

		public ButtonListenerNext(Board board, JTextField roll) {
			this.board = board;
			this.roll = roll;
		}

		public void actionPerformed(ActionEvent e) {

			// If the current player is the human player
			if (board.getHumanPlayer().equals(board.getPlayers().get(board.getCurrentPlayerTurn()))) {
				// if the human player has moved
				// TODO: add logic to determine if the player's turn is over, beyond if they
				// have moved
				if (board.getHumanPlayer().getHasMoved()) {

					int roll = this.board.getRoll();

					// update the roll and the playername for the current turn
					this.roll.setText(String.valueOf(roll));

					this.board.nextTurn(roll);

					// Update the Board
					playerName.setText(board.getPlayers().get(board.getCurrentPlayerTurn()).getName());
					Color temp = board.getPlayers().get(board.getCurrentPlayerTurn()).getColor();

					playerName
							.setBackground(new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), temp.getAlpha()));

				} else {
					// else, the player hasn't moved and their turn isn over yet

					JOptionPane turnNotOver = new JOptionPane();
					turnNotOver.showMessageDialog(new JFrame(), "Your turn isn't over yet!", "Turn Not Over",
							JOptionPane.WARNING_MESSAGE);

				}

			} else {

				// Else its a computer player, and process their turn
				int roll = this.board.getRoll();

				// update the roll and the playername for the current turn
				this.roll.setText(String.valueOf(roll));

				this.board.nextTurn(roll);

				// Update the board
				playerName.setText(board.getPlayers().get(board.getCurrentPlayerTurn()).getName());
				Color temp = board.getPlayers().get(board.getCurrentPlayerTurn()).getColor();
				playerName.setBackground(new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), temp.getAlpha()));
			}

			board.revalidate();
			board.repaint();

		}

	}

	// Button Listener for the accusation button
	class ButtonListenerAccustaion implements ActionListener {
		private Board board;

		public ButtonListenerAccustaion(Board board) {
			this.board = board;
		}

		public void actionPerformed(ActionEvent e) {

			// If it is the Human Player's turn
			if (board.getHumanPlayer().equals(board.getPlayers().get(board.getCurrentPlayerTurn()))) {

				AccusationDialog accusationDialog = new AccusationDialog(board.getHumanPlayer(), board);
			} else {
				JOptionPane notYourTurn = new JOptionPane();
				notYourTurn.showMessageDialog(new JFrame(), "You Can't make an accusaiton when it isn't your turn!",
						"It's not your turn", JOptionPane.WARNING_MESSAGE);
			}

		}

	}
}

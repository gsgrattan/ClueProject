package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import clueGame.Board;
import clueGame.ComputerPlayer;

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
		playerName.setText(board.getPlayers().get(board.getCurrentPlayerTurn()).getName());
		playerName.setEditable(false);
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

	public void setTurn(ComputerPlayer player, int i) {
		playerName.setText(player.getName());

		Color temp = player.getColor();
		playerName.setBackground(new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), temp.getAlpha() / 2));

		roll.setText(String.valueOf(i));
	}

	class ButtonListenerNext implements ActionListener {
		private Board board;
		private JTextField roll;

		public ButtonListenerNext(Board board, JTextField roll) {
			this.board = board;
			this.roll = roll;
		}

		public void actionPerformed(ActionEvent e) {
			int roll = this.board.getRoll();

			// update the roll and the playername for the current turn
			this.roll.setText(String.valueOf(roll));
			playerName.setText(board.getPlayers().get(board.getCurrentPlayerTurn()).getName());

			this.board.nextTurn(roll);

			board.revalidate();
			board.repaint();
		}
	}

	class ButtonListenerAccustaion implements ActionListener {
		private Board board;

		public ButtonListenerAccustaion(Board board) {
			this.board = board;
		}

		public void actionPerformed(ActionEvent e) {
			System.out.println("Accusation");
		}

	}
}

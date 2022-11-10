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

import clueGame.ComputerPlayer;

public class PlayerControlPanel extends JPanel {
	private JButton makeAccusation, next;
	private JPanel currentPlayer, rollPanel;
	private JLabel turnLabel, rollLabel;
	private JTextField playerName, roll;

	public PlayerControlPanel() {

		super();

		this.setLayout(new GridLayout(1, 4));

		// First panel
		currentPlayer = new JPanel();

		this.add(currentPlayer, BorderLayout.PAGE_START);

		turnLabel = new JLabel("Whose turn?");
		currentPlayer.add(turnLabel, BorderLayout.NORTH);

		playerName = new JTextField(15);
		playerName.setText("Col. Mustard");
		playerName.setEditable(false);
		currentPlayer.add(playerName, BorderLayout.SOUTH);

		// Second panel
		rollPanel = new JPanel();
		this.add(rollPanel);

		rollLabel = new JLabel("Roll: ");
		rollPanel.add(rollLabel);

		roll = new JTextField(5);
		roll.setText("5");
		roll.setEditable(false);
		roll.setSize(1, 1);
		rollPanel.add(roll);

		// Third panel
		makeAccusation = new JButton("Make Accusation");
		makeAccusation.addActionListener(new ButtonListener());
		this.add(makeAccusation);

		// Fourth panel
		next = new JButton("NEXT!");
		next.addActionListener(new ButtonListener());
		this.add(next);

	}

	public void setTurn(ComputerPlayer player, int i) {
		playerName.setText(player.getName());

		Color cum = player.getColor();
		playerName.setBackground(new Color(cum.getRed(), cum.getGreen(), cum.getBlue(), cum.getAlpha() / 2));

		roll.setText(String.valueOf(i));
	}

	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (makeAccusation.isSelected()) {

			} else if (next.isSelected()) {

			}

		}
	}

}

package gui;

import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JButton;

public class PlayerControlPanel extends JPanel{

	public PlayerControlPanel() {
		super();
		
		this.setLayout(new GridLayout(1,4));
		
		//First panel
		JPanel currentPlayer = new JPanel();

		this.add(currentPlayer, BorderLayout.PAGE_START);

		JLabel turnLabel = new JLabel("Whose turn?");
		currentPlayer.add(turnLabel, BorderLayout.NORTH);
		
		JTextField playerName = new JTextField(15);
		playerName.setText("Col. Mustard");
		playerName.setEditable(false);
		currentPlayer.add(playerName, BorderLayout.SOUTH);
		
		
		//Second panel
		JPanel rollPanel = new JPanel();
		this.add(rollPanel);
		
		JLabel rollLabel = new JLabel("Roll: ");
		rollPanel.add(rollLabel);
		
		JTextField roll = new JTextField(5);
		roll.setText("5");
		roll.setEditable(false);
		roll.setSize(1, 1);
		rollPanel.add(roll);
		
		//Third panel
		JButton makeAccusation = new JButton("Make Accusation");
		this.add(makeAccusation);
		
		//Fourth panel
		JButton next = new JButton("NEXT!");
		this.add(next);
	}
	

}

package gui;

import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import clueGame.Board;
import clueGame.Card;
import clueGame.Player;
import clueGame.Solution;

public class AccusationDialog extends JDialog {
	private Board board;
	private Player accuser;

	private JTextField currRoom;
	private JComboBox<Card> roomCombo;
	private JComboBox<Card> personCombo;
	private JComboBox<Card> weaponCombo;

	private Card roomCard;
	private Card personCard;
	private Card weaponCard;

	private JButton submit, cancel;

	private Solution accusation;

	public AccusationDialog(Player player, Board board) {
		super(ClueGame.getInstance(), Dialog.ModalityType.DOCUMENT_MODAL);

		this.board = board;

		this.setSize(300, 300);
		this.setTitle("Make a Suggestion");
		this.setLayout(new GridLayout(4, 2));

		JLabel roomLabel = new JLabel("Room");
		JLabel personLabel = new JLabel("Person");
		JLabel weaponLabel = new JLabel("Weapon");

		roomCombo = createComboBox(board.getRoomCards());
		personCombo = createComboBox(board.getPlayerCards());
		weaponCombo = createComboBox(board.getWeaponCards());

		submit = new JButton("Submit");
		cancel = new JButton("Cancel");

		this.add(roomLabel);
		this.add(roomCombo);

		this.add(personLabel);
		this.add(personCombo);

		this.add(weaponLabel);
		this.add(weaponCombo);

		this.add(submit);
		this.add(cancel);

		ComboListener comboListener = new ComboListener();

		roomCombo.addActionListener(comboListener);
		personCombo.addActionListener(comboListener);
		weaponCombo.addActionListener(comboListener);

		ButtonListener buttonListener = new ButtonListener();

		submit.addActionListener(buttonListener);
		cancel.addActionListener(buttonListener);

		this.setVisible(true);

	}

	public JComboBox<Card> createComboBox(Set<Card> cards) {
		JComboBox<Card> combo = new JComboBox<Card>();
		for (Card card : cards) {
			combo.addItem(card);
		}

		return combo;
	}

	private class ComboListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == roomCombo) {
				roomCard = (Card) roomCombo.getSelectedItem();

			} else if (e.getSource() == personCombo) {
				personCard = (Card) personCombo.getSelectedItem();

			} else if (e.getSource() == weaponCombo) {
				weaponCard = (Card) weaponCombo.getSelectedItem();

			}
		}
	}

	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == cancel) {
				setVisible(false);

			} else if (e.getSource() == submit) {
				// Ensure that all three Cards have been selected (none of them are null)
				// If one of them hasn't been selected (i.e it is null)
				if (roomCard == null | personCard == null | weaponCard == null) {
					JOptionPane splash = new JOptionPane();
					JFrame splashFrame = new JFrame();

					splash.showMessageDialog(splashFrame, "Please complete your card selction for the accusation",
							"Invalid Accusation", JOptionPane.INFORMATION_MESSAGE);

				} else {
					// else all cards for the accusation have been chosen
					accusation = new Solution(personCard, weaponCard, roomCard);
					String accusationResult;
					String accusationResultTitle;
					Solution trueSolution = board.getSolution();

					// If the accusation is correct
					if (board.checkAccusation(accusation)) {
						accusationResultTitle = "Congratulations! You won the game!";
						accusationResult = "Congratulations, you won the game!\n You made the correct accusation that the murder was committed by:\n "
								+ trueSolution.getPerson().getName() + " with the " + trueSolution.getWeapon().getName()
								+ " in " + trueSolution.getRoom().getName() + "!";
						// Else it is incorrect
					} else {
						accusationResultTitle = "Oh no! You lost the game!";
						accusationResult = "You lost the game!\n Your wrong accusaiton led you to be killed by:\n "
								+ trueSolution.getPerson().getName() + " with the " + trueSolution.getWeapon().getName()
								+ " in " + trueSolution.getRoom().getName() + "!";
					}

					EndGameDialog endGameDialog = new EndGameDialog(accusationResultTitle, accusationResult);

				}

			}

		}

	}
}

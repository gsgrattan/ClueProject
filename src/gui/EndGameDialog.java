package gui;

import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextArea;

public class EndGameDialog extends JDialog {

	JButton exit, playAgain;

	public EndGameDialog(String title, String body) {
		// Initialize the parent class
		super(ClueGame.getInstance(), Dialog.ModalityType.DOCUMENT_MODAL);

		// Set the basic panel types
		this.setSize(300, 300);
		this.setTitle(title);

		this.setLayout(new GridLayout(3, 0));

		// Set the textbody
		JTextArea textBody = new JTextArea(5, 20);
		textBody.setEditable(false);
		textBody.setText(body);
		this.add(textBody);

		exit = new JButton("Exit");
		playAgain = new JButton("Play Again");

		// Add the buttons
		this.add(playAgain);
		this.add(exit);

		ButtonListener buttonListener = new ButtonListener();

		playAgain.addActionListener(buttonListener);
		exit.addActionListener(buttonListener);

		this.setVisible(true);
	}

	public class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			// Handle the cases for the buttons
			if (e.getSource() == exit) {
				ClueGame.getInstance().endGame();
			} else if (e.getSource() == playAgain) {
				ClueGame.getInstance().newGame();

			}

		}

	}

}

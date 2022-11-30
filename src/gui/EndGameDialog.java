package gui;

import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;

public class EndGameDialog extends JDialog {

	JButton exit, playAgain;

	public EndGameDialog(String title, String body) {
		super(ClueGame.getInstance(), Dialog.ModalityType.DOCUMENT_MODAL);

		this.setSize(300, 300);
		this.setTitle(title);
		this.setLayout(new GridLayout(3, 0));

		JTextField textBody = new JTextField();
		textBody.setEditable(false);
		textBody.setText(body);
		this.add(textBody);

		exit = new JButton("Exit");
		playAgain = new JButton("Play Again");

		this.add(playAgain);
		this.add(exit);

		ButtonListener buttonListener = new ButtonListener();

		playAgain.addActionListener(buttonListener);
		exit.addActionListener(buttonListener);

		this.setVisible(true);
	}

	public class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == exit) {
				ClueGame.getInstance().endGame();
			} else if (e.getSource() == playAgain) {
				ClueGame.getInstance().newGame();

			}

		}

	}

}

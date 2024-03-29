package gui;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GuessPanel extends JPanel {
	JPanel guess, guessResult;
	JTextField currGuess, currGuessResult;

	public GuessPanel() {

		this.setLayout(new GridLayout(0, 2));

		guess = new JPanel();
		guess.setLayout(new GridLayout(1, 0));
		guess.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));

		// The Guess JTextfiel
		currGuess = new JTextField();
		currGuess.setText("IDK< I'm stupid!");
		currGuess.setEditable(false);
		guess.add(currGuess);
		this.add(guess);

		// GuessResult TextField
		guessResult = new JPanel();
		guessResult.setLayout(new GridLayout(1, 0));
		guessResult.setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));

		currGuessResult = new JTextField();
		currGuessResult.setEditable(false);
		currGuessResult.setText("Yes, yes you are.");
		guessResult.add(currGuessResult);

		this.add(guessResult);

	}

	// Functions to help us set the guess and guess result

	public void setGuess(String string) {
		currGuess.setText(string);
		this.revalidate();
		this.repaint();

	}

	public void setGuessResult(String string) {
		currGuessResult.setText(string);
		this.revalidate();
		this.repaint();

	}

}

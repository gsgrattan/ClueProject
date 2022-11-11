package gui;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import clueGame.Card;

public class JPCardList extends JPanel {

	private ArrayList<JTextField> fields;
	private boolean notNone = false;

	public JPCardList(String name) {
		fields = new ArrayList<JTextField>();

		this.setBorder(new TitledBorder(new EmptyBorder(10, 10, 10, 10), name));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JTextField yeet = new JTextField("None");

		this.add(yeet);
		fields.add(yeet);

	}

	public void updatePanel(Card card, Color color) {
		this.removeAll();
		if (!notNone) {
			this.fields.get(0).setText(card.getName());
			this.fields.get(0).setBackground(color);
			notNone = true;
		} else {
			JTextField tempField = new JTextField(card.getName());
			tempField.setBackground(color);
			this.fields.add(tempField);
		}

		for (JTextField field : fields) {
			this.add(field);

		}

	}

}

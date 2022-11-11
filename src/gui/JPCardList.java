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
		// ArrayList to store the field
		fields = new ArrayList<JTextField>();

		// Set the border and layout
		this.setBorder(new TitledBorder(new EmptyBorder(10, 10, 10, 10), name));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// Initialize the empty field
		JTextField yeet = new JTextField("None");
		yeet.setEditable(false);
		this.add(yeet);
		fields.add(yeet);
	}

	public void updatePanel(Card card, Color color) {
		// clear the panel
		this.removeAll();
		// Change the color to have more opacity and let the text be visible
		Color temp = new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha() / 4);

		// If it is the empty cell
		if (!notNone) {
			// Change the none to the first card
			this.fields.get(0).setText(card.getName());
			this.fields.get(0).setBackground(temp);
			this.fields.get(0).setEditable(false);
			notNone = true;
		} else {
			// Otherwise add a new field and add it to the array
			JTextField tempField = new JTextField(card.getName());
			tempField.setBackground(temp);
			this.fields.add(tempField);
		}
		// ReAdd all the textfields to the panel
		for (JTextField field : fields) {
			this.add(field);
			field.setBackground(temp);
			field.setEditable(false);

		}
	}

}

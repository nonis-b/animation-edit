package animationedit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;


class SelectBrushSizeField extends JPanel {
	
	private JTextField inputField;
	private JLabel label;
	private float brushSize = 1;
	
	public SelectBrushSizeField() {
		label = new JLabel("Brush size:");
		inputField = new JTextField("1");
		inputField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					brushSize = Float.parseFloat(inputField.getText());;
				} catch (NumberFormatException exception) {
					inputField.setText(new String("" + (int)brushSize));
				}
			}
		});
		add(label);
		add(inputField);
	}
	
	public float getBrushSize() {
		return brushSize;
	}
}
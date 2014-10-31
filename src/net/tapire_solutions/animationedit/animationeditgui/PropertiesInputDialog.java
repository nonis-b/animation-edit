package net.tapire_solutions.animationedit.animationeditgui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class PropertiesInputDialog extends JDialog implements ActionListener {

	private JButton okButton = null;
	private JButton cancelButton = null;
	private ArrayList<JTextField> propertyValueFields = new ArrayList<JTextField>();
	private ArrayList<PropertyItem> propertyItemList;
	
	public static class PropertyItem {
		public String name;
		public String value;
		public PropertyItem(String name, String value) {
			this.name = name;
			this.value = value;
		}
	}
	
	public PropertiesInputDialog(JFrame frame, String title,
			ArrayList<PropertyItem> propertyItemList) {
		super(frame, true);
		this.propertyItemList = propertyItemList;
		setTitle(title);
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

		for (PropertyItem propertyItem : propertyItemList) {
			JPanel row = new JPanel();
			row.setLayout(new GridLayout(1,2));
			
			JLabel name = new JLabel(propertyItem.name);
			row.add(name);

			JTextField value = new JTextField(1);
			value.setText(propertyItem.value);
			row.add(value);
			propertyValueFields.add(value);
			
			panel.add(row);
		}

		JPanel buttons = new JPanel();
		buttons.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		cancelButton = new JButton("Cancel");
		buttons.add(cancelButton);
		cancelButton.addActionListener(this);

		okButton = new JButton("OK");
		buttons.add(okButton);
		okButton.addActionListener(this);
		
		panel.add(buttons);
		
		getContentPane().add(panel);
		pack();
		setLocationRelativeTo(frame);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (okButton == e.getSource()) {
			for (int i = 0; i < propertyItemList.size(); i++) {
				propertyItemList.get(i).value = propertyValueFields.get(i).getText();
			}
			setVisible(false);
		} else if (cancelButton == e.getSource()) {
			setVisible(false);
		}
	}
}

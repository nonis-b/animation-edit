package net.tapire_solutions.animationedit.animationeditgui;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JLabel;


class BrushPropertiesMenu extends JPanel {
	
	private JSlider brushSizeSlider;
	private JCheckBox smoothCheckBox;
	
	public BrushPropertiesMenu() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		smoothCheckBox = new JCheckBox("Smooth");
		brushSizeSlider = new JSlider(JSlider.HORIZONTAL, 1, 10, 1);
		brushSizeSlider.setMajorTickSpacing(1);
		brushSizeSlider.setMinorTickSpacing(1);
		brushSizeSlider.setPaintTicks(true);
		brushSizeSlider.setPaintLabels(true);
		brushSizeSlider.setSnapToTicks(true);
		add(new JLabel("Brush size:"));
		add(brushSizeSlider);
		add(smoothCheckBox);
	}
	
	public float getBrushSize() {
		return brushSizeSlider.getValue();
	}
	
	public boolean isSmooth() {
		return smoothCheckBox.isSelected();
	}
}
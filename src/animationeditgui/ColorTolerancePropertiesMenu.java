package animationeditgui;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JLabel;


class ColorTolerancePropertiesMenu extends JPanel {
	
	private JSlider toleranceSlider;
	
	public ColorTolerancePropertiesMenu() {
		toleranceSlider = new JSlider(JSlider.HORIZONTAL, 0, 10, 1);
		toleranceSlider.setMajorTickSpacing(1);
		toleranceSlider.setMinorTickSpacing(1);
		toleranceSlider.setPaintTicks(true);
		toleranceSlider.setPaintLabels(true);
		toleranceSlider.setSnapToTicks(true);
		add(new JLabel("Tolerance:"));
		add(toleranceSlider);
	}
	
	public float getColorTolerance() {
		return (float)toleranceSlider.getValue() / 10;
	}
}
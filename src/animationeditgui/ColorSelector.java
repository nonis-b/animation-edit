package animationeditgui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JColorChooser;
import javax.swing.JPanel;

public class ColorSelector extends JPanel implements MouseListener {

	private Color color;
	
	public ColorSelector(int width, int height) {
		super();
		setPreferredSize(new Dimension(width, height));
		setColor(Color.BLACK);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		Color color = JColorChooser.showDialog(
                this,
                "Choose Color",
                getBackground());
		setColor(color);
		e.consume();
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
		setBackground(color);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

}

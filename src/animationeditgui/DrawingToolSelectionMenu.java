package animationeditgui;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import drawingtools.DrawingTool;

/**
 * Panel to select drawing tool.
 * 
 */
public class DrawingToolSelectionMenu extends JPanel {

	private DrawingTool tool;
	private ButtonHandler buttonHandler = new ButtonHandler();
	
	private class ToolButton extends JButton {
		public DrawingTool tool;

		public ToolButton(ImageIcon image, DrawingTool tool) {
			super(image);
			this.tool = tool;
		}
	}

	private class ButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			tool = ((ToolButton) event.getSource()).tool;
		}
	}

	private void createButton(DrawingTool tool, String image) {
		ToolButton button = new ToolButton(new ImageIcon(image), tool);
		button.addActionListener(buttonHandler);
		add(button);
	}

	public DrawingToolSelectionMenu(DrawingTool penDrawingTool, DrawingTool eraseDrawingTool, 
			DrawingTool pickupColorDrawingTool) {
		super();
		this.tool = penDrawingTool;
		createButton(penDrawingTool, "res/toolPen.png");
		createButton(eraseDrawingTool, "res/toolErase.png");
		createButton(pickupColorDrawingTool, "res/toolPickup.png");
		setLayout(new GridLayout(1, 3));
	}

	public DrawingTool getTool() {
		return tool;
	}
}

package animationeditgui;


import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import drawingtools.BucketDrawingTool;
import drawingtools.DrawingTool;
import drawingtools.LineDrawingTool;
import drawingtools.SelectRectDrawingTool;

/**
 * Panel to select drawing tool.
 * 
 */
public class DrawingToolSelectionMenu extends JPanel {

	private DrawingTool tool;
	private ButtonHandler buttonHandler = new ButtonHandler();
	private JPanel toolPropertiesPanel = new JPanel(); 
	private ArrayList<ToolButton> buttonList = new ArrayList<ToolButton>();
	
	private class ToolButton extends JButton {
		public DrawingTool tool;
		public JPanel propertiesPanel;

		public ToolButton(ImageIcon image, DrawingTool tool, JPanel propertiesPanel) {
			super(image);
			this.tool = tool;
			this.propertiesPanel = propertiesPanel;
		}
	}

	private void setTool(ToolButton toolButtonToSet) {
		if (tool != null) {
			tool.onSelectAnotherTool();
		}
		tool = toolButtonToSet.tool;
		toolPropertiesPanel.removeAll();
		toolPropertiesPanel.revalidate();
		toolPropertiesPanel.repaint();
		toolPropertiesPanel.add(toolButtonToSet.propertiesPanel);
		toolPropertiesPanel.revalidate();
		toolPropertiesPanel.repaint();
		
		for (ToolButton button : buttonList) {
			button.setSelected(false);
		}
		toolButtonToSet.setSelected(true);
		toolPropertiesPanel.revalidate();
		toolPropertiesPanel.repaint();
	}
	
	private class ButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			setTool(((ToolButton) event.getSource()));
		}
	}

	private ToolButton createButton(DrawingTool tool, String image, JPanel propertiesPanel) {
		ToolButton button = new ToolButton(new ImageIcon(image), tool, propertiesPanel);
		button.addActionListener(buttonHandler);
		button.setPreferredSize(new Dimension(30, 30));
		buttonList.add(button);
		return button;
	}

	public DrawingToolSelectionMenu(DrawingTool penDrawingTool, DrawingTool eraseDrawingTool, 
			DrawingTool pickupColorDrawingTool, BucketDrawingTool bucketDrawingTool,
			LineDrawingTool lineDrawingTool, SelectRectDrawingTool selectRectDrawingTool,
			JPanel brushPropertiesPanel, JPanel colorTolerancePropertiesPanel) {
		super();
		ToolButton initialToolButton = createButton(penDrawingTool, "res/toolPen.png", brushPropertiesPanel);
		setTool(initialToolButton);
		add(initialToolButton);
		add(createButton(lineDrawingTool, "res/toolLine.png", brushPropertiesPanel));
		add(createButton(eraseDrawingTool, "res/toolErase.png", brushPropertiesPanel));
		add(createButton(pickupColorDrawingTool, "res/toolPickup.png", new JPanel()));
		add(createButton(selectRectDrawingTool, "res/toolSelectRect.png", new JPanel()));
		add(createButton(bucketDrawingTool, "res/toolBucket.png", colorTolerancePropertiesPanel));
		setLayout(new GridLayout(1, 4));
		toolPropertiesPanel.setLayout(new FlowLayout());
	}

	public DrawingTool getTool() {
		return tool;
	}
	
	public JPanel getToolPropertiesPanel() {
		return toolPropertiesPanel;
	}
}

package animationedit;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

/**
 * Panel to select editing tool.
 * 
 */
public class ToolSelector extends JToolBar {
    
    public enum Tool {
        SET_TILE,
        DELETE_TILE,
        FILL_TILE,
        PICKUP_TILE,
        LINE_TILE,
        NEW_DUMMY,
        SELECT_DUMMY,
        DELETE_DUMMY,
    }
    
    private Tool tool;

    private class ToolButton extends JButton {
        public Tool tool;
        public ToolButton(ImageIcon image, Tool tool) {
            super(image);
            this.tool = tool;
        }
    }
        
    private class ButtonHandler implements ActionListener {
        
        @Override
	public void actionPerformed (ActionEvent event) {
            
            tool = ((ToolButton)event.getSource()).tool;

        }
    }
    
    private ButtonHandler buttonHandler = new ButtonHandler();
    
    private void createButton(Tool tool, String image) {
        ToolButton button = new ToolButton(new ImageIcon(image), tool);
        button.addActionListener(buttonHandler);
        add(button);
    }
    
    public ToolSelector() {
        
        super();
        
        tool = Tool.SET_TILE;

        createButton(Tool.NEW_DUMMY,    "res/toolNewDummy.png");
        createButton(Tool.SELECT_DUMMY, "res/toolSelectDummy.png");
        createButton(Tool.DELETE_DUMMY, "res/toolDeleteDummy.png");
        createButton(Tool.SET_TILE,     "res/toolSetTile.png");
        createButton(Tool.DELETE_TILE,  "res/toolDeleteTile.png");
        createButton(Tool.FILL_TILE,    "res/toolFillTile.png");
        createButton(Tool.PICKUP_TILE,  "res/toolPickupTile.png");
        createButton(Tool.LINE_TILE,    "res/toolLineTile.png");
        
	setLayout(new GridLayout(3,3));
    }

    
    public Tool getTool() {
        
        
        return tool;
    }
}

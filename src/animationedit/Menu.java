package animationedit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 * The application menu bar.
 * 
 */
public class Menu extends JMenuBar {

	protected JMenuItem newItem;
	protected JMenuItem saveItem;
	protected JMenuItem saveAsItem;
	protected JMenuItem openItem;
	protected JMenuItem reloadImagesItem;
	protected JMenuItem quitItem;
	protected JMenuItem helpItem;
	protected JMenuItem undoItem;
	protected JMenuItem moveLeftItem;
	protected JMenuItem moveRightItem;
	protected JMenuItem moveUpItem;
	protected JMenuItem moveDownItem;
	protected JMenuItem nudgeLeftItem;
	protected JMenuItem nudgeRightItem;
	protected JMenuItem nudgeUpItem;
	protected JMenuItem nudgeDownItem;
	protected JMenuItem scrollLeftItem;
	protected JMenuItem scrollRightItem;
	protected JMenuItem scrollUpItem;
	protected JMenuItem scrollDownItem;
	protected JMenuItem zoomInItem;
	protected JMenuItem zoomOutItem;

	private JMenuItem addMenuItem(JMenu menu, ActionListener handler, String text) {
		JMenuItem item = new JMenuItem(text);
		item.addActionListener(handler);
		menu.add(item);
		return item;
	}

	private JMenuItem addMenuItem(JMenu menu, ActionListener handler, String text, int keyCode, int keyMask) {
		JMenuItem item = new JMenuItem(text);
		item.addActionListener(handler);
		item.setAccelerator(KeyStroke.getKeyStroke(keyCode, keyMask));
		menu.add(item);
		return item;
	}

	public Menu(ActionListener handler) {
		super();

		JMenu fileMenu = new JMenu("File");
		JMenu editMenu = new JMenu("Edit");
		JMenu levelMenu = new JMenu("Level");
		JMenu dummyMenu = new JMenu("Dummy");
		JMenu tileMenu = new JMenu("Tilemap");
		JMenu helpMenu = new JMenu("Help");

		newItem = addMenuItem(fileMenu, handler, "New", KeyEvent.VK_N, ActionEvent.CTRL_MASK);
		openItem = addMenuItem(fileMenu, handler, "Open", KeyEvent.VK_O, ActionEvent.CTRL_MASK);
		saveItem = addMenuItem(fileMenu, handler, "Save", KeyEvent.VK_S, ActionEvent.CTRL_MASK);
		saveAsItem = addMenuItem(fileMenu, handler, "Save as...");
		reloadImagesItem = addMenuItem(fileMenu, handler, "Reload images", KeyEvent.VK_R, ActionEvent.CTRL_MASK);
		quitItem = addMenuItem(fileMenu, handler, "Quit");

		helpItem = addMenuItem(helpMenu, handler, "Help");

		undoItem = addMenuItem(editMenu, handler, "Undo", KeyEvent.VK_Z, ActionEvent.CTRL_MASK);

		moveLeftItem = addMenuItem(dummyMenu, handler, "Move left", KeyEvent.VK_LEFT, 0);
		moveRightItem = addMenuItem(dummyMenu, handler, "Move right", KeyEvent.VK_RIGHT, 0);
		moveUpItem = addMenuItem(dummyMenu, handler, "Move up", KeyEvent.VK_UP, 0);
		moveDownItem = addMenuItem(dummyMenu, handler, "Move down", KeyEvent.VK_DOWN, 0);

		nudgeLeftItem = addMenuItem(dummyMenu, handler, "Nudge left", KeyEvent.VK_LEFT, ActionEvent.ALT_MASK);
		nudgeRightItem = addMenuItem(dummyMenu, handler, "Nudge right", KeyEvent.VK_RIGHT, ActionEvent.ALT_MASK);
		nudgeUpItem = addMenuItem(dummyMenu, handler, "Nudge up", KeyEvent.VK_UP, ActionEvent.ALT_MASK);
		nudgeDownItem = addMenuItem(dummyMenu, handler, "Nudge down", KeyEvent.VK_DOWN, ActionEvent.ALT_MASK);
		
		scrollLeftItem = addMenuItem(levelMenu, handler, "Scroll left", KeyEvent.VK_A, 0);
		scrollRightItem = addMenuItem(levelMenu, handler, "Scroll right", KeyEvent.VK_D, 0);
		scrollUpItem = addMenuItem(levelMenu, handler, "Scroll up", KeyEvent.VK_W, 0);
		scrollDownItem = addMenuItem(levelMenu, handler, "Scroll down", KeyEvent.VK_S, 0);
		zoomInItem = addMenuItem(levelMenu, handler, "Zoom in", KeyEvent.VK_PERIOD, 0);
		zoomOutItem = addMenuItem(levelMenu, handler, "Zoom out", KeyEvent.VK_COMMA, 0);

		add(fileMenu);
		add(editMenu);
		add(levelMenu);
		add(dummyMenu);
		add(tileMenu);
		add(helpMenu);
	}

}

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
	protected JMenuItem zoomInItem;
	protected JMenuItem zoomOutItem;
	protected JMenuItem scrollLeftItem;
	protected JMenuItem scrollRightItem;
	protected JMenuItem scrollUpItem;
	protected JMenuItem scrollDownItem;
	protected JMenuItem preview1FpsItem;
	protected JMenuItem preview2FpsItem;
	protected JMenuItem preview10FpsItem;
	protected JMenuItem preview30FpsItem;
	protected JMenuItem preview60FpsItem;
	protected JMenuItem previewStopItem;
	protected JMenuItem previewNextFrameItem;
	protected JMenuItem previewPreviousFrameItem;
	protected JMenuItem newFrameItem;
	protected JMenuItem moveFrameUpItem;
	protected JMenuItem moveFrameDownItem;
	

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
		JMenu framesMenu = new JMenu("Frames");
		JMenu previewMenu = new JMenu("Preview");
		JMenu imageMenu = new JMenu("Image");
		JMenu helpMenu = new JMenu("Help");

		newItem = addMenuItem(fileMenu, handler, "New", KeyEvent.VK_N, ActionEvent.CTRL_MASK);
		openItem = addMenuItem(fileMenu, handler, "Open", KeyEvent.VK_O, ActionEvent.CTRL_MASK);
		saveItem = addMenuItem(fileMenu, handler, "Save", KeyEvent.VK_S, ActionEvent.CTRL_MASK);
		saveAsItem = addMenuItem(fileMenu, handler, "Save as...");
		reloadImagesItem = addMenuItem(fileMenu, handler, "Reload images", KeyEvent.VK_R, ActionEvent.CTRL_MASK);
		quitItem = addMenuItem(fileMenu, handler, "Quit");

		helpItem = addMenuItem(helpMenu, handler, "Help");

		undoItem = addMenuItem(editMenu, handler, "Undo", KeyEvent.VK_Z, ActionEvent.CTRL_MASK);

		preview1FpsItem = addMenuItem(previewMenu, handler, "1 FPS", KeyEvent.VK_1, 0);
		preview2FpsItem = addMenuItem(previewMenu, handler, "2 FPS", KeyEvent.VK_2, 0);
		preview10FpsItem = addMenuItem(previewMenu, handler, "10 FPS", KeyEvent.VK_3, 0);
		preview30FpsItem = addMenuItem(previewMenu, handler, "30 FPS", KeyEvent.VK_4, 0);
		preview60FpsItem = addMenuItem(previewMenu, handler, "60 FPS", KeyEvent.VK_5, 0);
		previewStopItem = addMenuItem(previewMenu, handler, "Stop", KeyEvent.VK_0, 0);
		previewNextFrameItem = addMenuItem(previewMenu, handler, "Next frame", KeyEvent.VK_N, 0);
		previewPreviousFrameItem = addMenuItem(previewMenu, handler, "Previous frame", KeyEvent.VK_B, 0);
		
		newFrameItem = addMenuItem(framesMenu, handler, "New frame", KeyEvent.VK_N, KeyEvent.ALT_MASK);
		moveFrameUpItem = addMenuItem(framesMenu, handler, "Move frame up", KeyEvent.VK_UP, KeyEvent.ALT_MASK);;
		moveFrameDownItem = addMenuItem(framesMenu, handler, "Move frame down", KeyEvent.VK_DOWN, KeyEvent.ALT_MASK);;
		
		scrollLeftItem = addMenuItem(imageMenu, handler, "Scroll left", KeyEvent.VK_A, 0);
		scrollRightItem = addMenuItem(imageMenu, handler, "Scroll right", KeyEvent.VK_D, 0);
		scrollUpItem = addMenuItem(imageMenu, handler, "Scroll up", KeyEvent.VK_W, 0);
		scrollDownItem = addMenuItem(imageMenu, handler, "Scroll down", KeyEvent.VK_S, 0);
		zoomInItem = addMenuItem(imageMenu, handler, "Zoom in", KeyEvent.VK_PERIOD, 0);
		zoomOutItem = addMenuItem(imageMenu, handler, "Zoom out", KeyEvent.VK_COMMA, 0);
	
		add(fileMenu);
		add(editMenu);
		add(framesMenu);
		add(previewMenu);
		add(imageMenu);
		add(helpMenu);
	}

}

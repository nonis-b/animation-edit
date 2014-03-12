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
	protected JMenuItem deleteFrameItem;
	protected JMenuItem moveFrameUpItem;
	protected JMenuItem moveFrameDownItem;
	
	protected JMenuItem onionSkinOffItem;
	protected JMenuItem onionSkin1DepthItem;
	protected JMenuItem onionSkin2DepthItem;
	protected JMenuItem onionSkin3DepthItem;
	protected JMenuItem onionSkin4DepthItem;
	protected JMenuItem onionSkin5DepthItem;
	
	protected JMenuItem offsetXPlus1PixItem;
	protected JMenuItem offsetXMinus1PixItem;
	protected JMenuItem offsetYPlus1PixItem;
	protected JMenuItem offsetYMinus1PixItem;
	protected JMenuItem offsetXPlus10PixItem;
	protected JMenuItem offsetXMinus10PixItem;
	protected JMenuItem offsetYPlus10PixItem;
	protected JMenuItem offsetYMinus10PixItem;
	protected JMenuItem editFrameTicsItem;
	protected JMenuItem editFrameTagItem;
	protected JMenuItem editFrameNextItem;
	
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
		deleteFrameItem = addMenuItem(framesMenu, handler, "Delete frame", KeyEvent.VK_BACK_SPACE, 0);
		moveFrameUpItem = addMenuItem(framesMenu, handler, "Move frame up", KeyEvent.VK_UP, KeyEvent.ALT_MASK);;
		moveFrameDownItem = addMenuItem(framesMenu, handler, "Move frame down", KeyEvent.VK_DOWN, KeyEvent.ALT_MASK);;
		
		offsetXPlus1PixItem = addMenuItem(imageMenu, handler, "Offset X +1", KeyEvent.VK_L, 0);
		offsetXMinus1PixItem = addMenuItem(imageMenu, handler, "Offset X -1", KeyEvent.VK_J, 0);
		offsetYPlus1PixItem = addMenuItem(imageMenu, handler, "Offset Y +1", KeyEvent.VK_K, 0);
		offsetYMinus1PixItem = addMenuItem(imageMenu, handler, "Offset Y -1", KeyEvent.VK_I, 0);
		offsetXPlus10PixItem = addMenuItem(imageMenu, handler, "Offset X +10", KeyEvent.VK_L, KeyEvent.CTRL_MASK);
		offsetXMinus10PixItem = addMenuItem(imageMenu, handler, "Offset X -10", KeyEvent.VK_J, KeyEvent.CTRL_MASK);
		offsetYPlus10PixItem = addMenuItem(imageMenu, handler, "Offset Y +10", KeyEvent.VK_K, KeyEvent.CTRL_MASK);
		offsetYMinus10PixItem = addMenuItem(imageMenu, handler, "Offset Y -10", KeyEvent.VK_I, KeyEvent.CTRL_MASK);
		editFrameTicsItem = addMenuItem(imageMenu, handler, "Edit frame tics", KeyEvent.VK_T, KeyEvent.CTRL_MASK);
		editFrameTagItem = addMenuItem(imageMenu, handler, "Edit frame tag", KeyEvent.VK_G, KeyEvent.CTRL_MASK);
		editFrameNextItem = addMenuItem(imageMenu, handler, "Edit frame next tag", KeyEvent.VK_F, KeyEvent.CTRL_MASK);
		
		scrollLeftItem = addMenuItem(imageMenu, handler, "Scroll left", KeyEvent.VK_A, 0);
		scrollRightItem = addMenuItem(imageMenu, handler, "Scroll right", KeyEvent.VK_D, 0);
		scrollUpItem = addMenuItem(imageMenu, handler, "Scroll up", KeyEvent.VK_W, 0);
		scrollDownItem = addMenuItem(imageMenu, handler, "Scroll down", KeyEvent.VK_S, 0);
		zoomInItem = addMenuItem(imageMenu, handler, "Zoom in", KeyEvent.VK_PERIOD, 0);
		zoomOutItem = addMenuItem(imageMenu, handler, "Zoom out", KeyEvent.VK_COMMA, 0);
	
		onionSkinOffItem = addMenuItem(imageMenu, handler, "Onion skin off", KeyEvent.VK_0, KeyEvent.ALT_MASK);
		onionSkin1DepthItem = addMenuItem(imageMenu, handler, "Onion skin 1 frame", KeyEvent.VK_1, KeyEvent.ALT_MASK);
		onionSkin2DepthItem = addMenuItem(imageMenu, handler, "Onion skin 2 frame", KeyEvent.VK_2, KeyEvent.ALT_MASK);
		onionSkin3DepthItem = addMenuItem(imageMenu, handler, "Onion skin 3 frame", KeyEvent.VK_3, KeyEvent.ALT_MASK);
		onionSkin4DepthItem = addMenuItem(imageMenu, handler, "Onion skin 4 frame", KeyEvent.VK_4, KeyEvent.ALT_MASK);
		onionSkin5DepthItem = addMenuItem(imageMenu, handler, "Onion skin 5 frame", KeyEvent.VK_5, KeyEvent.ALT_MASK);
		
		add(fileMenu);
		//add(editMenu);
		add(framesMenu);
		add(imageMenu);
		add(previewMenu);
		add(helpMenu);
	}

}

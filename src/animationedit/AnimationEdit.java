package animationedit;

import java.awt.Container;
import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;


import graphicsutils.ImageStore;

import animationedit.ToolSelector.Tool;

/**
 * Main window.
 * 
 */
public class AnimationEdit extends JFrame 
	implements AnimationEditComponentsAccessor,
				AnimationFrameSequenceInfoProvider
	{

	private ToolSelector toolSelector;
	private AnimationFrameSelector animationFrameSelector;
	private Menu menu;
	private ApplicationConfig config;
	private AnimationFrameView animationFrameView;
	private AnimationPreview animationPreview;
	private File currentAnimationSequenceFile = null;
	private AnimationFrameSequence animationSequence = null;
	private final int filePollInterval = 1000;
	private Timer filePollTimer;
	
	/**
	 * Setup app.
	 * 
	 * @param configFilePath Config file path.
	 * @param animationSequenceFile File to open.
	 */
	public AnimationEdit(String configFilePath, String animationSequenceFile) {
		super("AnimationEdit");

		config = new ApplicationConfig(configFilePath);	
		
		filePollTimer = new Timer();
		filePollTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				updateChangedImageFiles();
			}
		}, filePollInterval, filePollInterval);
		
		toolSelector = new ToolSelector();

		animationFrameView = new AnimationFrameView(this, this);
		animationPreview = new AnimationPreview(this, this);
		animationFrameSelector = new AnimationFrameSelector(animationFrameView);
		
		Container container = getContentPane();
		createGui(container);

		loadAnimationSequence(animationSequenceFile);
		
		animationFrameView.revalidate();
		animationFrameView.repaint();
		
		animationPreview.revalidate();
		animationPreview.repaint();
	}

	
	/**
	 * Create the main window GUI.
	 * 
	 * @param container Container object.
	 */
	private void createGui(Container container) {
		AnimationEditHandler handler = new AnimationEditHandler();

		menu = new Menu(handler);
		setJMenuBar(menu);

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 3));

		JToolBar animationFrameSelectorToolBar = new JToolBar();
		animationFrameSelectorToolBar.add(animationFrameSelector);
		panel.add(animationFrameSelectorToolBar);

		animationFrameView.addMouseListener(animationFrameView);
		animationFrameView.addMouseMotionListener(animationFrameView);
		panel.add(animationFrameView);
		
		panel.add(animationPreview);
		
		container.add(panel);
		
		// init main window
		setSize(Toolkit.getDefaultToolkit().getScreenSize().width - 40, Toolkit
				.getDefaultToolkit().getScreenSize().height/2 - 60);
		setLocation(20, 20);
		setVisible(true);
		setResizable(true);
	}


	/**
	 * Show "export" dialog.
	 * @return Path to export file to.
	 */
	private String getExportLevelPath() {
		JFileChooser fc = new JFileChooser(new File(config.exportPath));
		fc.showSaveDialog(this);
		return fc.getSelectedFile().getAbsolutePath();
	}

	
	/**
	 * Show a "save" dialog to get path.
	 * 
	 * @param useCurrent If to use already opened level if any.
	 * @param updateCurrent If to update the current file (We don't want that for export formats).
	 * @return Path to save to.
	 */
	public String getSaveLevelPath(boolean useCurrent, boolean updateCurrent) {
		
		File selFile = null;
		if (useCurrent) {
			selFile = currentAnimationSequenceFile;
		}
		
		if (!useCurrent || currentAnimationSequenceFile == null) {
			
			JFileChooser fc;
			if (currentAnimationSequenceFile != null) {
				fc = new JFileChooser(currentAnimationSequenceFile);
			} else {
				fc = new JFileChooser(new File(config.projectPath));
			}
			fc.showSaveDialog(this);
			selFile = fc.getSelectedFile();			

			if (selFile != null && updateCurrent) {
				currentAnimationSequenceFile = selFile;				
			}
			
		} else {
			System.out.println("Save to file " + selFile.getAbsolutePath());
		}

		if (selFile == null) return null;
		return selFile.getAbsolutePath();
	}

	/**
	 * Show an "open" dialog to to get path.
	 * 
	 * @return Path to open.
	 */
	public String getOpenLevelPath() {

		JFileChooser fc;
		if (currentAnimationSequenceFile != null) {
			fc = new JFileChooser(currentAnimationSequenceFile);
		} else {
			fc = new JFileChooser(new File(config.projectPath));
		}
		int returnVal = fc.showOpenDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			return fc.getSelectedFile().getAbsolutePath();
		}
		return null;
	}
	
	private void setCurrentAnimationSequenceFile(String path) {
		currentAnimationSequenceFile = new File(path);
		setTitle("AnimationEdit - " + path);
	}
	

	/**
	 * Shows help window.
	 */
	public void showHelp() {
	    JEditorPane editorPane = new JEditorPane("text/html", "<html><body>"
	    		+ "<p>Help content TODO.</p>"
	    		+ "<p>This application is free software.</p>"
	    		+ "<p>Get the source code: "
	            + "<a href=\"https://github.com/jonath0000/animation-edit/\">" 
	    		+ "https://github.com/jonath0000/animation-edit</a>"
	            + "</body></html> </p>");

		editorPane.addHyperlinkListener(new HyperlinkListener() {
			@Override
			public void hyperlinkUpdate(HyperlinkEvent event) {
				
				if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED 
						&& Desktop.isDesktopSupported()) {
					try {
						Desktop.getDesktop().browse(event.getURL().toURI());
					} catch (IOException exception) { 
						System.out.println("Couldn't open URL.");
					} catch (URISyntaxException exception) {
						System.out.println("Couldn't open URL.");
					}
				} else { 
					System.out.println("Couldn't open URL.");
				}
			}
		});
	    editorPane.setEditable(false);
	    JOptionPane.showMessageDialog(this, editorPane);
	}

	/**
	 * Handle all actions in main window.
	 */
	private class AnimationEditHandler implements ActionListener {			
		@Override
		public void actionPerformed(ActionEvent event) {
			
			if (event.getSource() == menu.undoItem) {
//				level.undo();
			}

			// scroll
			if (event.getSource() == menu.scrollUpItem) {
				animationFrameView.scrollY(-10);
			}
			if (event.getSource() == menu.scrollDownItem) {
				animationFrameView.scrollY(10);
			}
			if (event.getSource() == menu.scrollLeftItem) {
				animationFrameView.scrollX(-10);
			}
			if (event.getSource() == menu.scrollRightItem) {
				animationFrameView.scrollX(10);
			}
			if (event.getSource() == menu.zoomInItem) {
				animationFrameView.zoom(2.0f);
			}
			if (event.getSource() == menu.zoomOutItem) {
				animationFrameView.zoom(0.5f);
			}

			if (event.getSource() == menu.newFrameItem) {
				animationSequence.addAnimationFrame(JOptionPane.showInputDialog(
						"Image for frame? (Should be a png image in the working directory)",
						""));
				animationFrameSelector.setAnimationFrames(animationSequence.getAnimationFrames());
			}
			if (event.getSource() == menu.deleteFrameItem) {
				animationSequence.deleteAnimationFrame(animationFrameSelector.getSelected());
			}
			if (event.getSource() == menu.moveFrameUpItem) {
				animationSequence.moveAnimationFrameEarlier(animationFrameSelector.getSelected());
				animationFrameSelector.setAnimationFrames(animationSequence.getAnimationFrames());
			}
			if (event.getSource() == menu.moveFrameDownItem) {
				animationSequence.moveAnimationFrameLater(animationFrameSelector.getSelected());
				animationFrameSelector.setAnimationFrames(animationSequence.getAnimationFrames());
			}
			
			if (event.getSource() == menu.preview1FpsItem) {
				animationPreview.setUpdateSpeed(100, 1000);
			}
			if (event.getSource() == menu.preview2FpsItem) {
				animationPreview.setUpdateSpeed(100, 1000/2);
			}
			if (event.getSource() == menu.preview10FpsItem) {
				animationPreview.setUpdateSpeed(100, 1000/10);
			}
			if (event.getSource() == menu.preview30FpsItem) {
				animationPreview.setUpdateSpeed(100, 1000/30);
			}
			if (event.getSource() == menu.preview60FpsItem) {
				animationPreview.setUpdateSpeed(100, 1000/60);
			}
			if (event.getSource() == menu.previewStopItem) {
				animationPreview.setUpdateSpeed(100000000, 100000000);
			}
			if (event.getSource() == menu.previewNextFrameItem) {
				animationPreview.setUpdateSpeed(100000000, 100000000);
				animationPreview.nextFrame();
			}
			if (event.getSource() == menu.previewPreviousFrameItem) {
				animationPreview.setUpdateSpeed(100000000, 100000000);
				animationPreview.previousFrame();
			}
			
			if (event.getSource() == menu.offsetXPlus1PixItem) {
				animationFrameSelector.getSelected().addToOffsetX(1);
				animationFrameSelector.onAnimationFrameSequenceChanged();
			}
			if (event.getSource() == menu.offsetXMinus1PixItem) {
				animationFrameSelector.getSelected().addToOffsetX(-1);
				animationFrameSelector.onAnimationFrameSequenceChanged();
			}
			if (event.getSource() == menu.offsetYPlus1PixItem) {
				animationFrameSelector.getSelected().addToOffsetY(1);
				animationFrameSelector.onAnimationFrameSequenceChanged();
			}
			if (event.getSource() == menu.offsetYMinus1PixItem) {
				animationFrameSelector.getSelected().addToOffsetY(-1);
				animationFrameSelector.onAnimationFrameSequenceChanged();
			}
			
			if (event.getSource() == menu.offsetXPlus10PixItem) {
				animationFrameSelector.getSelected().addToOffsetX(10);
				animationFrameSelector.onAnimationFrameSequenceChanged();
			}
			if (event.getSource() == menu.offsetXMinus10PixItem) {
				animationFrameSelector.getSelected().addToOffsetX(-10);
				animationFrameSelector.onAnimationFrameSequenceChanged();
			}
			if (event.getSource() == menu.offsetYPlus10PixItem) {
				animationFrameSelector.getSelected().addToOffsetY(10);
				animationFrameSelector.onAnimationFrameSequenceChanged();
			}
			if (event.getSource() == menu.offsetYMinus10PixItem) {
				animationFrameSelector.getSelected().addToOffsetY(-10);
				animationFrameSelector.onAnimationFrameSequenceChanged();
			}
			
			if (event.getSource() == menu.onionSkinOffItem) {
				animationFrameView.setOnionSkinDepth(0);
			}
			if (event.getSource() == menu.onionSkin1DepthItem) {
				animationFrameView.setOnionSkinDepth(1);
			}
			if (event.getSource() == menu.onionSkin2DepthItem) {
				animationFrameView.setOnionSkinDepth(2);
			}
			if (event.getSource() == menu.onionSkin3DepthItem) {
				animationFrameView.setOnionSkinDepth(3);
			}
			if (event.getSource() == menu.onionSkin4DepthItem) {
				animationFrameView.setOnionSkinDepth(4);
			}
			if (event.getSource() == menu.onionSkin5DepthItem) {
				animationFrameView.setOnionSkinDepth(5);
			}
			
			if (event.getSource() == menu.editFrameTicsItem) {
				String val = JOptionPane.showInputDialog("Set tics for frame",
						animationFrameSelector.getSelected().getTics());
				if (val != null) {
					animationFrameSelector.getSelected().setTics(Integer.parseInt(val));
				}
			}
			
			if (event.getSource() == menu.editFrameTagItem) {
				String val = JOptionPane.showInputDialog("Set tag for frame",
						animationFrameSelector.getSelected().getTag());
				if (val != null) {
					animationFrameSelector.getSelected().setTag(val);
				}
			}
			
			if (event.getSource() == menu.editFrameNextItem) {
				String val = JOptionPane.showInputDialog("Set next tag for frame",
						animationFrameSelector.getSelected().getNext());
				if (val != null) {
					animationFrameSelector.getSelected().setNext(val);
				}
			}
			
			// MENU -> "Save" (AnimationEdit format)
			if (event.getSource() == menu.saveItem) {
				String path = getSaveLevelPath(true, true);
				animationSequence.writeToFile(path);
				if (path != null) {
					setTitle("AnimationEdit - " + currentAnimationSequenceFile.getAbsolutePath() 
							+ " | Last save: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
				}
			}

			// MENU -> "Save as" AnimationEdit format
			if (event.getSource() == menu.saveAsItem) {
				String path = getSaveLevelPath(false, true);
				animationSequence.writeToFile(path);
				if (path != null) {
					setTitle("AnimationEdit - " + currentAnimationSequenceFile.getAbsolutePath() 
							+ " | Last save: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
				}
			}

			// MENU -> New
			if (event.getSource() == menu.newItem) {
				String path = getSaveLevelPath(false, true);
				loadAnimationSequence(path);
			}

			if (event.getSource() == menu.reloadImagesItem) {
				animationSequence.getImageStore().reloadAll();
			}

			if (event.getSource() == menu.quitItem) {
				System.exit(0);
			}

			if (event.getSource() == menu.helpItem) {
				showHelp();
			}

			if (event.getSource() == menu.openItem) {
				String path = getOpenLevelPath();
				loadAnimationSequence(path);
			}

			animationFrameView.repaint();
		}
	}

	
	private void updateChangedImageFiles() {
		if (currentAnimationSequenceFile == null) return;
		File dir = currentAnimationSequenceFile.getParentFile();
		for (File dirFileName : dir.listFiles()) {
			// TODO: a bit ugly, using 1.5 times polling interval to find changed files.
			if (dirFileName.getName().endsWith(".png") && 
					dirFileName.lastModified() > System.currentTimeMillis() - (filePollInterval+filePollInterval/2)) {
				animationSequence.getImageStore().reloadImage(dirFileName.getName());
				System.out.println("Found changed file " + dirFileName.getName() + ". Reloading.");
			}
		}
	}
	
	
	private void loadAnimationSequence(String path) {
		if (path != null) {
			File file = new File(path);
			if (!file.exists() && !file.isDirectory()) {
				if (!path.endsWith(".xml")) {
					path = path + ".xml";
				}
				file = new File(path);
				ArrayList<AnimationFrame> createdFrames = new ArrayList<AnimationFrame>();
				for (String dirFileName : file.getParentFile().list()) {
					if (dirFileName.endsWith(".png")) {
						System.out.println("Auto-included png file in directory: " + dirFileName);
						createdFrames.add(new AnimationFrame(dirFileName, 0, 0, 1, "", ""));
					}
				}
				AnimationFrameSequenceFile.writeAnimtionFrameSequenceToXml(path, createdFrames);
			}
			String dir = file.getParent();
			animationSequence = new AnimationFrameSequence(dir, path);
			animationSequence.addChangeListener(animationFrameSelector);
			animationFrameSelector.setAnimationFrames(animationSequence.getAnimationFrames());
			setCurrentAnimationSequenceFile(path);
		}
	}
 	
	
	/**
	 * App main entry.
	 * 
	 * Args: 
	 *   arg0: path to a config file.
	 *   arg1: path to animation sequence to open.
	 * 
	 * @param args 
	 */
	public static void main(String[] args) {
		String configFile;
		String animationSequenceFile = null;
		
		if (args.length < 1) {
			System.out.println("No config specified, using default.");
			configFile = "config.xml";
		} else {
			configFile = args[0];
		}
		
		if (args.length >= 2) {
			animationSequenceFile = args[1];
		}
		
		try {
			AnimationEdit app = new AnimationEdit(configFile, animationSequenceFile);
			app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error while initializing app.");
			System.exit(1);
		}
	}



	@Override
	public Tool getSelectedTool() {
		return toolSelector.getTool();
	}

	@Override
	public ImageStore getImageStore() {
		if (animationSequence == null) return null;
		return animationSequence.getImageStore();
	}

	@Override
	public AnimationFrame getSelectedAnimationFrame() {
		return animationFrameSelector.getSelected();
	}


	@Override
	public int getNumAnimationFrames() {
		if (animationSequence.getAnimationFrames() == null) return 0;
		return animationSequence.getAnimationFrames().size();
	}


	@Override
	public AnimationFrame getAnimationFrame(int i) {
		return animationSequence.getAnimationFrame(i);
	}


	@Override
	public int getIndexOfAnimationFrameWithTag(String tag) {
		return animationSequence.getFrameIndexOfTag(tag);
	}


	@Override
	public int getSelectedAnimationFrameIndex() {
		return animationFrameSelector.getSelectedIndex();
	}
}
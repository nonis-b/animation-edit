package animationeditgui;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import drawingtools.CurrentBrushSelector;
import drawingtools.CurrentColorSelector;
import drawingtools.DrawingTool;
import drawingtools.DrawingToolSelector;
import drawingtools.EraseDrawingTool;
import drawingtools.PenDrawingTool;
import drawingtools.PickupColorDrawingTool;

import animationeditgui.DirectoryChangeWatcher.DirectoryChangedListener;
import animationframesequence.AnimationFrame;
import animationframesequence.AnimationFrameSequence;
import animationframesequence.AnimationFrameSequenceFile;
import animationframesequence.AnimationFrameSequenceInfoProvider;


import graphicsutils.ImageStore;
import graphicsutils.ImageStore.ImageStoreMaxSizeChangedListener;


/**
 * Main window.
 * 
 */
public class AinmationEditApplication extends JFrame 
	implements ImageStoreProvider,
				AnimationFrameSequenceInfoProvider,
				ImageStoreMaxSizeChangedListener,
				DrawingToolSelector,
				CurrentColorSelector, 
				CurrentBrushSelector,
				DirectoryChangedListener
	{

	private AnimationFrameSelector animationFrameSelector;
	private ApplicationMenu menu;
	private ApplicationConfig config;
	private AnimationFrameView animationFrameView;
	private AnimationPreview animationPreview;
	private AnimationFrameSequence animationSequence = null;
	private JColorChooser colorChooser;
	private DrawingToolSelectionMenu drawingToolSelectionMenu;
	private SelectBrushSizeField selectBrushSizeField;
	private CurrentDocument currentDocument;
	private DirectoryChangeWatcher directoryChangeWatcher;
	
	/**
	 * Setup app.
	 * 
	 * @param configFilePath Config file path.
	 * @param animationSequenceFile File to open.
	 */
	public AinmationEditApplication(String configFilePath, String animationSequenceFile) {
		super("AnimationEdit");

		config = new ApplicationConfig(configFilePath);	

		directoryChangeWatcher = new DirectoryChangeWatcher(this, ".png");
		
		currentDocument = new CurrentDocument(config.projectPath);
		
		animationFrameView = new AnimationFrameView(this, this, this);
		animationPreview = new AnimationPreview(this, this);
		animationFrameSelector = new AnimationFrameSelector(animationFrameView);
		
		Container container = getContentPane();
		createGui(container);

		if (animationSequenceFile != null) {
			currentDocument.openDocument(animationSequenceFile);
			animationSequence = loadAnimationSequence(animationSequenceFile);
		}
		
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

		menu = new ApplicationMenu(handler);
		setJMenuBar(menu);

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 3));

		JToolBar animationFrameSelectorToolBar = new JToolBar();
		animationFrameSelectorToolBar.add(animationFrameSelector);
		panel.add(animationFrameSelectorToolBar);
		
		animationFrameView.addMouseListener(animationFrameView);
		animationFrameView.addMouseMotionListener(animationFrameView);
		
		JScrollPane scrollPane = new JScrollPane(animationFrameView);
		panel.add(scrollPane);
		
		panel.add(animationPreview);
		
		JPanel drawingToolsPanel = new JPanel();
		
		colorChooser = new JColorChooser();
		drawingToolsPanel.add(colorChooser);

		drawingToolSelectionMenu = new DrawingToolSelectionMenu(
				new PenDrawingTool(this, this), 
				new EraseDrawingTool(this), 
				new PickupColorDrawingTool(this));
		drawingToolsPanel.add(drawingToolSelectionMenu);
		
		selectBrushSizeField = new SelectBrushSizeField();
		drawingToolsPanel.add(selectBrushSizeField);
		
		panel.add(drawingToolsPanel);
		container.add(panel);
		
		// init main window
		setSize(Toolkit.getDefaultToolkit().getScreenSize().width - 40, Toolkit
				.getDefaultToolkit().getScreenSize().height - 60);
		setLocation(20, 20);
		setVisible(true);
		setResizable(true);
	}


	public void showHelp() {
		HelpWindow.show();
	}

	/**
	 * Handle all actions in main window.
	 */
	private class AnimationEditHandler implements ActionListener {			
		@Override
		public void actionPerformed(ActionEvent event) {
			
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
				String path = currentDocument.saveDocument(true, true);
				animationSequence.writeToFile(path);
				if (path != null) {
					animationSequence.getImageStore().writeModifiedImagesToDisk();
					setTitle("AnimationEdit - " + currentDocument.getDocumentTitle()
							+ " | Last save: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
				}
			}

			// MENU -> "Save as" AnimationEdit format
			if (event.getSource() == menu.saveAsItem) {
				String path = currentDocument.saveDocument(false, true);
				animationSequence.writeToFile(path);
				if (path != null) {
					setTitle("AnimationEdit - " + currentDocument.getDocumentTitle()
							+ " | Last save: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
				}
			}

			// MENU -> New
			if (event.getSource() == menu.newItem) {
				String path = currentDocument.saveDocument(false, true);
				AnimationFrameSequenceFile.generateNewAnimtionFrameSequenceXmlFile(path);
				animationSequence = loadAnimationSequence(path);
				setTitle("AnimationEdit - " + path);
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
				String path = currentDocument.openDocument();
				animationSequence = loadAnimationSequence(path);
				setTitle("AnimationEdit - " + path);
			}

			animationFrameView.repaint();
		}
	}
	
	@Override
	public void onDirectoryChanged(ArrayList<String> changedFiles) {
		for (String fileName : changedFiles) {
			animationSequence.getImageStore().reloadImage(fileName);
		}
	}
	

	private AnimationFrameSequence loadAnimationSequence(String path) {
		if (path == null) return null;
		File file = new File(path);
		String dir = file.getParent();
		AnimationFrameSequence animationSequence = new AnimationFrameSequence(dir, path);
		animationSequence.addChangeListener(animationFrameSelector);
		animationFrameSelector.setAnimationFrames(animationSequence.getAnimationFrames());
		animationSequence.getImageStore().addMaxSizeChangedListener(this);
		if (currentDocument.hasOpenDocument()) {
			directoryChangeWatcher.setCurrentDirectory(currentDocument.getParentDirectoryOfOpenDocument());
		}
		return animationSequence;
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
			AinmationEditApplication app = new AinmationEditApplication(configFile, animationSequenceFile);
			app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error while initializing app.");
			System.exit(1);
		}
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


	@Override
	public void maxSizeChanged(int maxX, int maxY) {
		animationFrameView.maxSizeChanged(maxX, maxY);
	}


	@Override
	public DrawingTool getTool() {
		return drawingToolSelectionMenu.getTool();
	}


	@Override
	public Color getColor() {
		return colorChooser.getColor();
	}


	@Override
	public void setColor(Color color) {
		colorChooser.setColor(color);
	}


	@Override
	public float getBrushWidth() {
		return selectBrushSizeField.getBrushSize();
	}
}
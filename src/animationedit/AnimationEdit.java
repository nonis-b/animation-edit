package animationedit;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import graphicsutils.ImageStore;

import animationedit.ToolSelector.Tool;

/**
 * The GUI class of the AnimationEdit application. Sets up all the buttons, menus
 * and stuff in the main window.
 * 
 */
public class AnimationEdit extends JFrame 
	implements AnimationEditComponentsAccessor,
				AnimationFrameSequenceInfoProvider
	{

	private static final String HELP_TEXT = 
			  "TODO";

	private ToolSelector toolSelector;
	private AnimationFrameSelector animationFrameSelector;
	private Menu menu;
	private ApplicationConfig config;
	private AnimationFrameView animationFrameView;
	private AnimationPreview animationPreview;
	private File currentAnimationSequenceFile = null;
	private AnimationFrameSequence animationSequence = null;

	
	/**
	 * Setup app.
	 * 
	 * @param configFilePath Config file path.
	 * @param animationSequenceFile File to open.
	 */
	public AnimationEdit(String configFilePath, String animationSequenceFile) {
		super("AnimationEdit");

		config = new ApplicationConfig(configFilePath);	
		
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

		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new GridLayout(3, 1));
		//JPanel bottomPanel = new JPanel();
		
		leftPanel.add(toolSelector);

		JToolBar dummyToolBar = new JToolBar();
		dummyToolBar.add(animationFrameSelector);
		leftPanel.add(dummyToolBar);
		
		container.add(leftPanel, BorderLayout.WEST);
		//container.add(bottomPanel, BorderLayout.SOUTH);
		leftPanel.add(animationPreview);
		
		animationFrameView.addMouseListener(animationFrameView);
		animationFrameView.addMouseMotionListener(animationFrameView);
		container.add(animationFrameView, BorderLayout.CENTER);
		
		
		
		// init main window
		setSize(Toolkit.getDefaultToolkit().getScreenSize().width - 40, Toolkit
				.getDefaultToolkit().getScreenSize().height - 60);
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

			currentAnimationSequenceFile = fc.getSelectedFile();
			setTitle("AnimationEdit - " + currentAnimationSequenceFile.getAbsolutePath());
			return currentAnimationSequenceFile.getAbsolutePath();
		}
		return null;
	}
	

	/**
	 * Shows help window.
	 */
	public void showHelp() {
		JOptionPane.showMessageDialog(this, HELP_TEXT);
		System.out.println(HELP_TEXT);
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

	
	private void loadAnimationSequence(String path) {
		if (path != null) {
			File file = new File(path);
			if (!file.exists() && !file.isDirectory()) {
				AnimationFrameSequenceFile.writeAnimtionFrameSequenceToXml(path, new ArrayList<AnimationFrame>());
				file = new File(path);
			}
			String dir = file.getParent();
			animationSequence = new AnimationFrameSequence(dir, path);
			animationSequence.addChangeListener(animationFrameSelector);
			animationFrameSelector.setAnimationFrames(animationSequence.getAnimationFrames());
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
	public ApplicationConfig getConfig() {
		return config;
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
		return animationSequence.getAnimationFrames().size();
	}


	@Override
	public AnimationFrame getAnimationFrame(int i) {
		return animationSequence.getAnimationFrames().get(i);
	}
}
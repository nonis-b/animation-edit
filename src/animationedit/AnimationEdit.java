package animationedit;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import framemodel.AnimationFrame;
import graphicsutils.ImageStore;

import animationedit.ToolSelector.Tool;

/**
 * The GUI class of the AnimationEdit application. Sets up all the buttons, menus
 * and stuff in the main window.
 * 
 */
public class AnimationEdit extends JFrame implements AnimationEditComponentsAccessor {

	private static final String HELP_TEXT = 
			  "TODO";

	private ToolSelector toolSelector;
	private AnimationFrameSelector animationFrameSelector;
	private Menu menu;
	private Config config;
	private String configFilePath;
	private AnimationFrameView animationFrameView;
	private File currentAnimationDirectory = null;
	private AnimationSequence animationSequence = null;

	
	/**
	 * Setup app.
	 * 
	 * @param configFilePath Config file path.
	 */
	public AnimationEdit(String configFilePath) {
		super("AnimationEdit");
		this.configFilePath = configFilePath;

		config = new Config(configFilePath);	
		
		toolSelector = new ToolSelector();

		animationFrameSelector = new AnimationFrameSelector();
		animationFrameView = new AnimationFrameView(this);
		
		Container container = getContentPane();
		createGui(container);


		animationFrameView.revalidate();
		animationFrameView.repaint();
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
		leftPanel.setLayout(new GridLayout(2, 1));
		JPanel bottomPanel = new JPanel();
		
		leftPanel.add(toolSelector);

		JToolBar dummyToolBar = new JToolBar();
		dummyToolBar.add(animationFrameSelector);
		leftPanel.add(dummyToolBar);

		container.add(leftPanel, BorderLayout.WEST);
		container.add(bottomPanel, BorderLayout.SOUTH);

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
	 * Create new blank level. Ask for size.
	 */
	public void newLevel() {
		animationSequence = null;
		currentAnimationDirectory = null;
		setTitle("AnimationEdit - untitled");
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
			selFile = currentAnimationDirectory;
		}
		
		if (!useCurrent || currentAnimationDirectory == null) {
			
			JFileChooser fc;
			if (currentAnimationDirectory != null) {
				fc = new JFileChooser(currentAnimationDirectory);
			} else {
				fc = new JFileChooser(new File(config.projectPath));
			}
			fc.showSaveDialog(this);
			selFile = fc.getSelectedFile();			

			if (selFile != null && updateCurrent) {
				currentAnimationDirectory = selFile;				
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
		if (currentAnimationDirectory != null) {
			fc = new JFileChooser(currentAnimationDirectory);
		} else {
			fc = new JFileChooser(new File(config.projectPath));
		}
		int returnVal = fc.showOpenDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {

			currentAnimationDirectory = fc.getSelectedFile();
			setTitle("AnimationEdit - " + currentAnimationDirectory.getAbsolutePath());
			return currentAnimationDirectory.getAbsolutePath();
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


			// move
//			if (event.getSource() == menu.moveUpItem) {
//				level.isAboutToAlterState();
//				level.getDummyObjects().moveSelectedDummy(0, -config.representationTileSize);
//			} else if (event.getSource() == menu.moveDownItem) {
//				level.isAboutToAlterState();
//				level.getDummyObjects().moveSelectedDummy(0, config.representationTileSize);
//			} else if (event.getSource() == menu.moveLeftItem) {
//				level.isAboutToAlterState();
//				level.getDummyObjects().moveSelectedDummy(-config.representationTileSize, 0);
//			} else if (event.getSource() == menu.moveRightItem) {
//				level.isAboutToAlterState();
//				level.getDummyObjects().moveSelectedDummy(config.representationTileSize, 0);
//			} // nudge
//			else if (event.getSource() == menu.nudgeUpItem) {
//				level.isAboutToAlterState();
//				level.getDummyObjects().moveSelectedDummy(0, -1);
//			} else if (event.getSource() == menu.nudgeDownItem) {
//				level.isAboutToAlterState();
//				level.getDummyObjects().moveSelectedDummy(0, 1);
//			} else if (event.getSource() == menu.nudgeLeftItem) {
//				level.isAboutToAlterState();
//				level.getDummyObjects().moveSelectedDummy(-1, 0);
//			} else if (event.getSource() == menu.nudgeRightItem) {
//				level.isAboutToAlterState();
//				level.getDummyObjects().moveSelectedDummy(1, 0);
//			}

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

//			// MENU -> "Save" (AnimationEdit format)
//			if (event.getSource() == menu.saveItem) {
//				String path = getSaveLevelPath(true, true);
//				level.writeToFile(new InternalLevelFile(path));
//				if (path != null) {
//					setTitle("AnimationEdit - " + currentAnimationDirectory.getAbsolutePath() 
//							+ " | Last save: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
//				}
//			}

//			// MENU -> "Save as" AnimationEdit format
//			if (event.getSource() == menu.saveAsItem) {
//				String path = getSaveLevelPath(false, true);
//				level.writeToFile(new InternalLevelFile(path));
//				if (path != null) {
//					setTitle("AnimationEdit - " + currentAnimationDirectory.getAbsolutePath() 
//							+ " | Last save: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
//				}
//			}
//
//			// MENU -> Export as comma separated
//			if (event.getSource() == menu.exportAsCommaSeparatedItem) {
//				level.writeToFile(new CommaSeparatedTileMapLevelFile(
//						getExportLevelPath()));
//			}
//
//			// MENU -> Export as XML object list
//			if (event.getSource() == menu.exportAsXmlObjectListItem) {
//				level.writeToFile(new XmlObjectListLevelFile(
//						getExportLevelPath()));
//			}


			if (event.getSource() == menu.quitItem) {
				System.exit(0);
			}

			// HELP
			if (event.getSource() == menu.helpItem) {
				showHelp();
			}

			// MENU -> New map
			if (event.getSource() == menu.newMapItem) {
				newLevel();
			}

//			// MENU -> open
//			if (event.getSource() == menu.openItem) {
//				String path = getOpenLevelPath();
//				if (path != null) {
//					level.initFromFile(new InternalLevelFile(path),
//							config.dummyDefinitions.toArray(new DummyObject[0]));
//				}
//			}

			animationFrameView.repaint();
		}
	}

	/**
	 * App main entry.
	 * 
	 * Args: 
	 *   arg0: path to a config file.
	 * 
	 * @param args arg 0 
	 */
	public static void main(String[] args) {
		String configFile;
		
		if (args.length < 1) {
			System.out.println("No config specified, using default.");
			configFile = "config.xml";
		} else {
			configFile = args[0];
		}
		
		try {
			AnimationEdit app = new AnimationEdit(configFile);
			app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		} catch (Exception e) {
			System.out.println("Error while initializing app.");
		}
	}



	@Override
	public Tool getSelectedTool() {
		return toolSelector.getTool();
	}
	
	@Override 
	public Config getConfig() {
		return config;
	}

	@Override
	public ImageStore getImageStore() {
		return animationSequence.getImageStore();
	}

	@Override
	public AnimationFrame getSelectedAnimationFrame() {
		return animationFrameSelector.getSelected();
	}
}
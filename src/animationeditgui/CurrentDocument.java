package animationeditgui;

import java.io.File;

import javax.swing.JFileChooser;

/**
 * Manages a current document behavior as expected in a normal desktop application:
 * - At first no file is open.
 * - User chooses new or open file.
 * - A file is active as the current "document".
 * - Saving will overwrite the current file.
 *
 */
public class CurrentDocument {
	
	private File currentFile = null;
	private String defaultPath;
	
	public CurrentDocument(String defaultPath) {
		this.defaultPath = defaultPath;
	}
	
	public String getDocumentTitle() {
		return currentFile.getAbsolutePath();
	}
	
	public boolean hasOpenDocument() {
		if (currentFile == null) return false;
		return true;
	}
	
	/**
	 * Only valid if hasOpenDocument() is true.
	 * @return File or null.
	 */
	public File getOpenDocument() {
		return currentFile;
	}
	
	/**
	 * Get the directory the document. Only valid if hasOpenDocument() is true.
	 * @return Directory File or null.
	 */
	public File getParentDirectoryOfOpenDocument() {
		if (currentFile == null) return null;
		if (currentFile.getParentFile().isDirectory()) return currentFile.getParentFile();
		return null;
	}
	
	/**
	 * Show a "save" dialog to get a path. The caller is assumed to write to the 
	 * path returned, if it is non-null.
	 * 
	 * @param overwriteCurrent If to use already opened level if any.
	 * @param setAsCurrent If to update the current file (We don't want that for export formats).
	 * @return Path to save to.
	 */
	public String saveDocument(boolean overwriteCurrent, boolean setAsCurrent) {
		
		File selFile = null;
		if (overwriteCurrent) {
			selFile = currentFile;
		}
		
		if (!overwriteCurrent || currentFile == null) {
			
			JFileChooser fc;
			if (currentFile != null) {
				fc = new JFileChooser(currentFile);
			} else {
				fc = new JFileChooser(new File(defaultPath));
			}
			fc.showSaveDialog(null);
			selFile = fc.getSelectedFile();			

			if (selFile != null && setAsCurrent) {
				currentFile = selFile;				
			}
			
		} else {
			System.out.println("Save to file " + selFile.getAbsolutePath());
		}

		if (selFile == null) return null;
		return selFile.getAbsolutePath();
	}

	/**
	 * Show an "open" dialog to to get path. The caller is assumed to
	 * open the file from the path returned, if it is non-null.
	 * 
	 * @return Path to open.
	 */
	public String openDocument() {

		JFileChooser fc;
		if (currentFile != null) {
			fc = new JFileChooser(currentFile);
		} else {
			fc = new JFileChooser(new File(defaultPath));
		}
		int returnVal = fc.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			currentFile = fc.getSelectedFile();
			return fc.getSelectedFile().getAbsolutePath();
		}
		return null;
	}
	
	public String openDocument(String path) {
		currentFile = new File(path);
		return currentFile.getAbsolutePath();
	}
}

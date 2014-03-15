package animationedit;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class DirectoryChangeWatcher {
	private final int filePollInterval = 1000;
	private Timer filePollTimer;
	private DirectoryChangedListener listener;
	private File dir = null;
	private String watchFileSuffix;
	
	public interface DirectoryChangedListener {
		public void onDirectoryChanged(ArrayList<String> changedFiles);
	}
	
	public DirectoryChangeWatcher(DirectoryChangedListener listener, String watchFileSuffix) {
		this.listener = listener;
		this.watchFileSuffix = watchFileSuffix;
		filePollTimer = new Timer();
		filePollTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				update();
			}
		}, filePollInterval, filePollInterval);
	}
	
	public void setCurrentDirectory(File dir) {
		this.dir = dir;
	}
	
	private void update() {
		if (dir == null) return;
		ArrayList<String> changedFiles = new ArrayList<String>();
		for (File dirFileName : dir.listFiles()) {
			// TODO: a bit ugly, using 1.5 times polling interval to find changed files.
			if (dirFileName.getName().endsWith(watchFileSuffix) && 
					dirFileName.lastModified() > System.currentTimeMillis() - (filePollInterval+filePollInterval/2)) {
				changedFiles.add(dirFileName.getName());
				System.out.println("Found changed file " + dirFileName.getName() + ".");
			}
		}
		if (changedFiles.size() > 0) {
			listener.onDirectoryChanged(changedFiles);
		}
	}
}

package animationedit;

import java.util.ArrayList;

import graphicsutils.ImageStore;

/**
 * Represents the animation sequence in one directory.
 */
public class AnimationFrameSequence {

	ImageStore imageStore;
	ArrayList<AnimationFrame> animationFrames;
	ArrayList<AnimationFrameSequenceChangedListener> listeners = new ArrayList<AnimationFrameSequenceChangedListener>();
	
	public AnimationFrameSequence(String workingDirectory, String animationSequenceFile) {
		imageStore = new ImageStore(workingDirectory);
		animationFrames = AnimationFrameSequenceFile.createAnimtionFrameSequenceFromXml(animationSequenceFile);
	}
	
	public void addChangeListener(AnimationFrameSequenceChangedListener listener) {
		listeners.add(listener);
	}

	public boolean writeToFile(String path) {
		return AnimationFrameSequenceFile.writeAnimtionFrameSequenceToXml(path, animationFrames);
	}
	
	public ImageStore getImageStore() {
		return imageStore;
	}
	
	public ArrayList<AnimationFrame> getAnimationFrames() {
		return animationFrames;
	}
	
	public void deleteAnimationFrame(AnimationFrame frameToDelete) {
		for (AnimationFrame frame : animationFrames) {
			if (frame.equals(frameToDelete)) {
				animationFrames.remove(frameToDelete);
				notifyChangeListeners();
			}
		}
	}
	
	private void notifyChangeListeners() {
		for (AnimationFrameSequenceChangedListener listener : listeners) {
			listener.onAnimationFrameSequenceChanged();
		}
	}
	
	public int getFrameIndexOfTag(String tag) {
		AnimationFrame frame;
		for (int i = 0; i < animationFrames.size(); i++) {
			frame = animationFrames.get(i);
			if (frame.getTag().equals(tag)) return i;
		}
		return -1;
	}
	
	public AnimationFrame getAnimationFrame(int i) {
		if (i >= animationFrames.size()) return null;
		return animationFrames.get(i);
	}
	
	public void addAnimationFrame(String image) {
		if (image == null || image.isEmpty()) return;
		animationFrames.add(new AnimationFrame(image, 0, 0, 1, "", ""));
		notifyChangeListeners();
	}
	
	public boolean moveAnimationFrameLater(AnimationFrame frame) {
		int index = animationFrames.indexOf(frame);
		if (index == -1) return false;
		if (animationFrames.size() <= 1) return false;
		if (index > animationFrames.size() - 2) return false;
		AnimationFrame temp = animationFrames.get(index);
		animationFrames.set(index, animationFrames.get(index+1));
		animationFrames.set(index+1, temp);
		notifyChangeListeners();
		return true;
	}
	
	public boolean moveAnimationFrameEarlier(AnimationFrame frame) {
		int index = animationFrames.indexOf(frame);
		if (index == -1) return false;
		if (animationFrames.size() <= 1) return false;
		if (index < 1) return false;
		AnimationFrame temp = animationFrames.get(index);
		animationFrames.set(index, animationFrames.get(index-1));
		animationFrames.set(index-1, temp);
		notifyChangeListeners();
		return true;
	}
 }

package animationedit;

import java.util.ArrayList;

import framemodel.AnimationFrame;
import graphicsutils.ImageStore;

/**
 * Represents the animation sequence in one directory.
 */
public class AnimationSequence {

	ImageStore imageStore;
	ArrayList<AnimationFrame> animationFrames;
	
	public AnimationSequence(String workingDirectory) {
		imageStore = new ImageStore(workingDirectory);
		animationFrames = AnimationFrameSequenceCreator.createAnimtionFrameSequenceFromXml(workingDirectory + "/frames.xml");
	}

	public ImageStore getImageStore() {
		return imageStore;
	}
	
}

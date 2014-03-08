package animationedit;

import framemodel.AnimationFrame;

public interface AnimationFrameSequenceInfoProvider {
	public AnimationFrame getSelectedAnimationFrame();
	public int getNumAnimationFrames();
	public AnimationFrame getAnimationFrameIndex(int i);
}

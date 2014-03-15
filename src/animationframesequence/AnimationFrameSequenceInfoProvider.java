package animationframesequence;


public interface AnimationFrameSequenceInfoProvider {
	public AnimationFrame getSelectedAnimationFrame();
	public int getSelectedAnimationFrameIndex();
	public int getNumAnimationFrames();
	public AnimationFrame getAnimationFrame(int i);
	public int getIndexOfAnimationFrameWithTag(String tag);
}

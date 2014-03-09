package animationedit;


public interface AnimationFrameSequenceInfoProvider {
	public AnimationFrame getSelectedAnimationFrame();
	public int getNumAnimationFrames();
	public AnimationFrame getAnimationFrame(int i);
}

package animationedit;

import framemodel.AnimationFrame;
import graphicsutils.ImageStore;


public interface AnimationEditComponentsAccessor {
	public AnimationFrame getSelectedAnimationFrame();
	public ToolSelector.Tool getSelectedTool();
	public Config getConfig();
	public ImageStore getImageStore();
}

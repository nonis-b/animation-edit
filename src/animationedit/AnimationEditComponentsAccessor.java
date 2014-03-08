package animationedit;

import graphicsutils.ImageStore;


public interface AnimationEditComponentsAccessor {
	public ToolSelector.Tool getSelectedTool();
	public Config getConfig();
	public ImageStore getImageStore();
}

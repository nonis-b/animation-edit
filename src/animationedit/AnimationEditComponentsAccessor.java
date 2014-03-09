package animationedit;

import graphicsutils.ImageStore;


public interface AnimationEditComponentsAccessor {
	public ToolSelector.Tool getSelectedTool();
	public ApplicationConfig getConfig();
	public ImageStore getImageStore();
}

package animationedit;

import graphicsutils.ImageStore;


public interface AnimationEditComponentsAccessor {
	public ToolSelector.Tool getSelectedTool();
	public ImageStore getImageStore();
}

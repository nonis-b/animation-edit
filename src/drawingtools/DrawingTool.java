package drawingtools;

import java.awt.image.BufferedImage;

public interface DrawingTool {
	public void onMouseDown(BufferedImage image, int x, int y);
	public void onMouseRelease(BufferedImage image, int x, int y);
	public void onMouseMoveWhileDown(BufferedImage image, int x, int y);
	public void onSelectAnotherTool();
}

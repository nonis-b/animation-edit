	package graphicsutils;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Draws 1 pixel wide.
 */
public class PenDrawingTool implements DrawingTool {

	private int lastMouseDownX = -1;
	private int lastMouseDownY = -1;
	
	@Override
	public void onMouseDown(BufferedImage image, int x, int y) {
		image.getGraphics().setColor(Color.BLACK);
		image.getGraphics().drawLine(x, y, x, y);
		lastMouseDownX = x;
		lastMouseDownY = y;
	}

	@Override
	public void onMouseRelease(BufferedImage image, int x, int y) {
		lastMouseDownX = -1;
		lastMouseDownY = -1;
	}

	@Override
	public void onMouseMoveWhileDown(BufferedImage image, int x, int y) {
		if (lastMouseDownX < 0 || lastMouseDownY < 0) return;
		image.getGraphics().setColor(Color.BLACK);
		image.getGraphics().drawLine(lastMouseDownX, lastMouseDownY, x, y);
		lastMouseDownX = x;
		lastMouseDownY = y;
	}

}

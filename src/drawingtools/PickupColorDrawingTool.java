package drawingtools;


import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Pickup color in image.
 */
public class PickupColorDrawingTool implements DrawingTool {

	private CurrentColorSelector colorSelector;
	
	public PickupColorDrawingTool(CurrentColorSelector colorSelector) {
		this.colorSelector = colorSelector;
	}
	
	@Override
	public void onMouseDown(BufferedImage image, int x, int y) {
		colorSelector.setColor(new Color(image.getRGB(x, y)));
	}

	@Override
	public void onMouseRelease(BufferedImage image, int x, int y) {
	}

	@Override
	public void onMouseMoveWhileDown(BufferedImage image, int x, int y) {
		colorSelector.setColor(new Color(image.getRGB(x, y)));
	}

	@Override
	public void onSelectAnotherTool() {
	}
}

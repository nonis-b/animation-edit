package drawingtools;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Stack;

/**
 * Bucket / flood fill.
 */
public class BucketDrawingTool implements DrawingTool {

	private CurrentColorSelector colorSelector;
	private ColorToleranceSelector colorToleranceSelector;

	public BucketDrawingTool(CurrentColorSelector colorSelector, ColorToleranceSelector colorToleranceSelector) {
		this.colorSelector = colorSelector;
		this.colorToleranceSelector = colorToleranceSelector;
	}

	private boolean isInImage(int x, int y, BufferedImage image) {
		if (x >= 0 && x < image.getWidth() && y >= 0 && y < image.getHeight())
			return true;
		return false;
	}
	
	private boolean isEqualColor(int rgb1, int rgb2, float tolerance) {
		Color c1 = new Color(rgb1, true);
		Color c2 = new Color(rgb2, true);
		int tolerance256 = (int) (tolerance * 256);
		if (Math.abs(c1.getRed() - c2.getRed()) > tolerance256) return false;
		if (Math.abs(c1.getGreen() - c2.getGreen()) > tolerance256) return false;
		if (Math.abs(c1.getBlue() - c2.getBlue()) > tolerance256) return false;
		if (c1.getAlpha() == 0 && c2.getAlpha() != 0) return false;
		if (c1.getAlpha() != 0 && c2.getAlpha() == 0) return false;
		return true;
	}

	private boolean addToBorderAndMaskIfEqual(Stack<int[]> border, int oldColor, BufferedImage image, 
			int x, int y, boolean[][] mask, float tolerance) {
		if (isInImage(x, y, image) && isEqualColor(image.getRGB(x, y), oldColor, tolerance) && !mask[y][x] == true) {
			mask[y][x] = true;
			border.push(new int[] {x, y});
			return true;
		}
		return false;
	}

	public void fill(BufferedImage image, int x, int y) {
		boolean[][] mask = new boolean[image.getHeight()][image.getHeight()];

		if (!isInImage(x, y, image)) return;
		
		float tolerance = colorToleranceSelector.getColorTolerance();
		int fillColor = colorSelector.getColor().getRGB();
		int oldColor = image.getRGB(x, y);
		mask[y][x] = true;
		Stack<int[]> border = new Stack<int[]>();
		border.push(new int[] {x, y});
		
		// mark tiles as belonging to area
		do {
			int[] currentPixel = border.pop();
			addToBorderAndMaskIfEqual(border, oldColor, image, currentPixel[0], currentPixel[1]+1, mask, tolerance);
			addToBorderAndMaskIfEqual(border, oldColor, image, currentPixel[0], currentPixel[1]-1, mask, tolerance);
			addToBorderAndMaskIfEqual(border, oldColor, image, currentPixel[0]+1, currentPixel[1], mask, tolerance);
			addToBorderAndMaskIfEqual(border, oldColor, image, currentPixel[0]-1, currentPixel[1], mask, tolerance);
		} while (border.size() > 0);

		// change tile values
		for (int xi = 0; xi < mask[0].length; xi++) {
			for (int yi = 0; yi < mask.length; yi++) {
				if (mask[yi][xi] == true)
					image.setRGB(xi, yi, fillColor);
			}
		}
	}

	@Override
	public void onMouseDown(BufferedImage image, int x, int y) {
		Graphics2D g = (Graphics2D) image.getGraphics();
		g.setColor(colorSelector.getColor());
		fill(image, x, y);
	}

	@Override
	public void onMouseRelease(BufferedImage image, int x, int y) {
	}

	@Override
	public void onMouseMoveWhileDown(BufferedImage image, int x, int y) {
	}

}

package net.tapire_solutions.animationedit.drawingtools;


import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
 * Erase 1 pixel wide.
 */
public class EraseDrawingTool implements DrawingTool {

	private int lastMouseDownX = -1;
	private int lastMouseDownY = -1;
	private Color eraseColor = new Color(0, 0, 0, 0);
	private CurrentBrushSelector brushSelector;
	
	public EraseDrawingTool(CurrentBrushSelector brushSelector) {
		this.brushSelector = brushSelector;
	}
	
	@Override
	public void onMouseDown(BufferedImage image, int x, int y) {
		Graphics2D g = (Graphics2D)image.getGraphics();
		g.setColor(eraseColor);
		if (brushSelector.getBrushIsSmooth()) {
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		} else {
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		}
		g.setStroke(new BasicStroke(brushSelector.getBrushWidth()));
		g.setComposite(AlphaComposite.Clear);
		image.getGraphics().drawLine(x, y, x, y);
		g.setComposite(AlphaComposite.Src);
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
		Graphics2D g = (Graphics2D)image.getGraphics();
		if (brushSelector.getBrushIsSmooth()) {
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		} else {
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		}
		g.setStroke(new BasicStroke(brushSelector.getBrushWidth()));
		g.setColor(eraseColor);
		g.setComposite(AlphaComposite.Clear);
		g.drawLine(lastMouseDownX, lastMouseDownY, x, y);
		g.setComposite(AlphaComposite.Src);
		lastMouseDownX = x;
		lastMouseDownY = y;
	}

	@Override
	public void onSelectAnotherTool() {
	}

}

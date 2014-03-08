package graphicsutils;

import java.awt.Color;
import java.awt.Graphics;

public class GridDrawingUtil {
	public static void drawBoundingBox(Color c, Graphics g, int x1, int y1, int x2, int y2) {
        g.setColor(c);
		g.drawLine(x1, y1, x2, y1);
		g.drawLine(x1, y1, x1, y2);
		g.drawLine(x1, y2, x2, y2);
		g.drawLine(x2, y1, x2, y2);
	}
}

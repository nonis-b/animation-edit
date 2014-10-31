package net.tapire_solutions.animationedit.graphicsutils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class GridDrawingUtil {
	public static void drawBoundingBox(Color c, Graphics g, int x1, int y1, int x2, int y2) {
        g.setColor(c);
		g.drawLine(x1, y1, x2, y1);
		g.drawLine(x1, y1, x1, y2);
		g.drawLine(x1, y2, x2, y2);
		g.drawLine(x2, y1, x2, y2);
	}

	public static void drawDashedBoundingBox(Color c, Graphics g, int x1, int y1, int x2, int y2) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(c);
		final float dash[] = { 2.0f };
		final BasicStroke dashed = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
		g2d.setStroke(dashed);
		g2d.draw(new Rectangle2D.Double(x1, y1, x2 - x1, y2 - y1));
	}
	
	public static void drawCrossHair(Color c, Graphics g, int x, int y, int size) {
		g.setColor(c);
		int l = size/2;
		g.drawLine(x - l, y - l, x + l, y + l);
		g.drawLine(x - l, y + l, x + l, y - l);
	}
}


package drawingtools;

import java.awt.image.BufferedImage;

/**
 * Select rectangular areas.
 */
public class SelectRectDrawingTool implements DrawingTool {

	private int lastMouseDownX = -1;
	private int lastMouseDownY = -1;
	private SelectRectListener listener;
	
	public SelectRectDrawingTool(SelectRectListener listener) {
		this.listener = listener;
	}
	
	private void notifyListener(int selectX, int selectY) {
		int rectX = lastMouseDownX;
		int rectY = lastMouseDownY;
		int rectW = selectX - lastMouseDownX;
		int rectH = selectY - lastMouseDownY;
		if (selectX == lastMouseDownX) {
			return;
		}
		if (selectY == lastMouseDownY) {
			return;
		}
		if (selectX < lastMouseDownX) {
			rectX = selectX;
			rectW = lastMouseDownX - selectX;
		}
		if (selectY < lastMouseDownY) {
			rectY = selectY;
			rectH = lastMouseDownY - selectY;
		}
		listener.onSelectRect(rectX, rectY, rectW, rectH);
	}
	
	@Override
	public void onMouseDown(BufferedImage image, int x, int y) {
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
		if (lastMouseDownX < 0) return;
		notifyListener(x, y);
	}
	
	@Override
	public void onSelectAnotherTool() {
		lastMouseDownX = -1;
		lastMouseDownY = -1;
	}

}

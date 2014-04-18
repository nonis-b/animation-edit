package graphicsutils;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.Deque;
import java.util.LinkedList;

/**
 * Image with built-in undo states.
 * It uses a kind of naive approach right now, copying the whole image each time.
 * 
 * TODO: re-do feature.
 */
public class ImageWithHistory {
	private final int maxSize;
	private Deque<BufferedImage> imageHistory;
	
	public ImageWithHistory(BufferedImage image, int maxSize) {
		this.maxSize = maxSize;
		imageHistory = new LinkedList<BufferedImage>();
		imageHistory.push(image);
		wasModified(); // save the first fall back state
	}
	
	public void wasModified() {
		System.out.println(toString() + " Save undo state.");
		imageHistory.push(deepCopy(imageHistory.peek()));
		if (imageHistory.size() > maxSize) {
			imageHistory.removeLast();
			System.out.println(toString() + " Dropped oldest undo state.");
		}
	}

	public void undoLastModification() {
		if (imageHistory.size() <= 2) {
			System.out.println(toString() + " No more undos.");
			return;
		}
		System.out.println(toString() + " Undo.");
		imageHistory.pop(); // pop the current (unmodified) 
		imageHistory.pop(); // pop the last saved state, it's the same as the current
		wasModified(); // now make a copy of the save state to modify
	}

	public BufferedImage getAsBufferedImage() {
		return imageHistory.peek();
	}

	private static BufferedImage deepCopy(BufferedImage image) {
		ColorModel colorModel = image.getColorModel();
		boolean isAlphaPremultiplied = colorModel.isAlphaPremultiplied();
		WritableRaster raster = image.copyData(null);
		return new BufferedImage(colorModel, raster, isAlphaPremultiplied, null);
	}
}

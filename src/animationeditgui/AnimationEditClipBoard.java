package animationeditgui;

import graphicsutils.CompatibleImageCreator;
import graphicsutils.ImageStore;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.image.BufferedImage;



import animationframesequence.AnimationFrame;
import animationframesequence.AnimationFrameSequenceInfoProvider;

import drawingtools.SelectRectListener;

public class AnimationEditClipBoard implements ClipboardOwner, SelectRectListener {

	private int selectX;
	private int selectY;
	private int selectW;
	private int selectH;
	private boolean hasSelection = false;
	private FloatingLayer floatingLayer = null;
	private ImageStoreProvider imageStoreProvider;
	private AnimationFrameSequenceInfoProvider animationFrameSequenceInfoProvider;

	public AnimationEditClipBoard(ImageStoreProvider imageStoreProvider,
			AnimationFrameSequenceInfoProvider animationFrameSequenceInfoProvider) {
		this.imageStoreProvider = imageStoreProvider;
		this.animationFrameSequenceInfoProvider = animationFrameSequenceInfoProvider;
	}

	private BufferedImage getCurrentFrameBufferedImage() {
		ImageStore imageStore = imageStoreProvider.getImageStore();
		if (imageStore != null) {
			AnimationFrame frame = animationFrameSequenceInfoProvider.getSelectedAnimationFrame();
			if (frame != null) {
				Image image = imageStore.getImage(frame.getImage());
				if (image != null) {
					if (image instanceof BufferedImage) {
						return (BufferedImage)image;
					}
				}
			}
		}
		return null;
	}
	
	private BufferedImage createSubImage(BufferedImage source, int x, int y, int width, int height) {
		BufferedImage subImage = CompatibleImageCreator.createCompatibleImage(width, height);
		subImage.createGraphics().drawImage(source, 0, 0, width, height, x, y, x + width, y + height, null);
		return subImage;
	}

	private void deleteSelectedImageInternal() {
		if (!hasSelection) return;
		BufferedImage image = getCurrentFrameBufferedImage();
		if (image == null) return;
		Graphics2D g = image.createGraphics();
		g.setComposite(AlphaComposite.Clear);
		g.fillRect(selectX, selectY, selectW, selectH);
		g.setComposite(AlphaComposite.SrcOver);
	}
	
	public void deleteSelectedImage() {
		deleteSelectedImageInternal();
		hasSelection = false;
	}

	public void cutSelectedImage() {
		if (!hasSelection) return;
		copySelectedImageInternal();
		deleteSelectedImageInternal();
		hasSelection = false;
	}

	private void copySelectedImageInternal() {
		if (!hasSelection) return;
		BufferedImage image = getCurrentFrameBufferedImage();
		if (image == null) return;
		BufferedImage subImage = createSubImage(image, selectX, selectY, selectW, selectH);
		TransferableImage clipBoardImage = new TransferableImage(subImage);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(clipBoardImage, this);
	}
	
	public void copySelectedImage() {
		copySelectedImageInternal();
		hasSelection = false;
	}
	
	public void pasteImage() {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		Object transferObject;
		try {
			transferObject = clipboard.getContents(null).getTransferData(DataFlavor.imageFlavor);
		} catch (Exception e) {
			return;
		}
		if (!(transferObject instanceof Image)) return;
		BufferedImage imageToPaste = CompatibleImageCreator.createCompatibleImage((Image)transferObject);
		floatingLayer = new FloatingLayer(imageToPaste, selectX, selectY);
		hasSelection = false;
	}
	
	public void selectAll() {
		BufferedImage image = getCurrentFrameBufferedImage();
		if (image == null) return;
		selectX = 0;
		selectY = 0;
		selectW = image.getWidth();
		selectH = image.getHeight();
		hasSelection = true;
	}
	
	public void selectNone() {
		hasSelection = false;
	}

	/**
	 * Indicate that there is an active selection, that should be
	 * drawn at the getSelection*() coordinates.
	 * @return True if selection.
	 */
	public boolean hasSelection() {
		return hasSelection;
	}

	public int getSelectionX() {
		return selectX;
	}

	public int getSelectionY() {
		return selectY;
	}

	public int getSelectionWidth() {
		return selectW;
	}

	public int getSelectionHeight() {
		return selectH;
	}
	
	/**
	 * Indicate that there is a floating layer, that should be drawn
	 * on top of the regular image, at coordinates x,y.
	 * @return
	 */
	public boolean hasFloatingLayer() {
		if (floatingLayer == null) return false;
		return true;
	}

	/**
	 * Apply the floating layer to the image.
	 */
	public void anchorFloatingLayer() {
		if (floatingLayer == null)
			return;
		BufferedImage frameImage = getCurrentFrameBufferedImage();
		if (frameImage == null)
			return;
		frameImage.createGraphics().drawImage(floatingLayer.getImage(), floatingLayer.getPosX(), floatingLayer.getPosY(), null);
		floatingLayer = null;
	}

	public FloatingLayer getFloatingLayer() {
		return floatingLayer;
	}
	
	@Override
	public void onSelectRect(int x, int y, int width, int height) {
		if (hasFloatingLayer()) return;
		hasSelection = true;
		selectX = x;
		selectY = y;
		selectW = width;
		selectH = height;
	}

	@Override
	public void lostOwnership(Clipboard arg0, Transferable arg1) {
	}
}

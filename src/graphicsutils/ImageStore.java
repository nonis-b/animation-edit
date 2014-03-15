package graphicsutils;


import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class ImageStore {
	
	private class ImageRecord {
		public BufferedImage image;
		public String imagePath;
		public boolean wasModified;
		public ImageRecord(BufferedImage image, String imagePath) {
			this.image = image;
			this.imagePath = imagePath;
			wasModified = false;
		}
	}
	
	public interface ImageStoreMaxSizeChangedListener {
		public void maxSizeChanged(int maxX, int maxY);
	}
	
	private Map<String, ImageRecord> images;
	private final String imageDirectory;
	private int maxImageWidth = 1;
	private int maxImageHeight = 1;
	private ArrayList<ImageStoreMaxSizeChangedListener> listeners = new ArrayList<ImageStoreMaxSizeChangedListener>();
	
	public ImageStore(String imageDirectory) {
		if (!imageDirectory.endsWith("/")) {
			imageDirectory = imageDirectory + "/";
		}
		this.imageDirectory = imageDirectory;
		images = new HashMap<String, ImageRecord>();
	}
	
	public void addMaxSizeChangedListener(ImageStoreMaxSizeChangedListener listener) {
		listeners.add(listener);
	}
	
	public void removeMaxSizeChangedListener(ImageStoreMaxSizeChangedListener listener) {
		listeners.remove(listener);
	}
	
	/**
	 * Forget about image.
	 * @param imageToReload File name of image, eg. "myimage.png".
	 */
	public void reloadImage(String imageToReload) {
		if (images.containsKey(imageToReload)) {
			images.remove(imageToReload);
		} else if (imageToReload.contains(imageToReload + ".png")) {
			images.remove(imageToReload + ".png");
		}
	}
	
	/**
	 * Force reloading of all resources.
	 */
	public void reloadAll() {
		images = new HashMap<String, ImageRecord>();
	}
	
	private void calculateImageSizes() {
		int oldMaxImageWidth = maxImageWidth;
		maxImageWidth = 1;
		for (Map.Entry<String, ImageRecord> entry : images.entrySet()) {
			if (entry.getValue() != null) {
				int val = entry.getValue().image.getWidth(null);
				if (val > maxImageWidth) {
					maxImageWidth = val;
				}
			}
		}
		int oldMaxImageHeight = maxImageHeight;
		maxImageHeight = 1;
		for (Map.Entry<String, ImageRecord> entry : images.entrySet()) {
			if (entry.getValue() != null) {
				int val = entry.getValue().image.getHeight(null);
				if (val > maxImageHeight) {
					maxImageHeight = val;
				}
			}
		}
		if (oldMaxImageWidth != maxImageWidth || oldMaxImageHeight != maxImageHeight) {
			for (ImageStoreMaxSizeChangedListener listener : listeners) {
				listener.maxSizeChanged(maxImageWidth, maxImageHeight);
			}
		}
	}
	
	public int getMaxWidthOfImage() {
		return maxImageWidth;
	}
	
	public int getMaxHeightOfImage() {
		return maxImageHeight;
	}
	
	public void writeModifiedImagesToDisk() {
		for (Map.Entry<String, ImageRecord> entry : images.entrySet()) {
			if (entry.getValue() != null) {
				if (entry.getValue().wasModified) {
					try {
					    File outputfile = new File(entry.getValue().imagePath);
					    ImageIO.write(entry.getValue().image, "png", outputfile);
					    entry.getValue().wasModified = false;
					    System.out.println("Wrote image to disk " + entry.getValue().imagePath);
					} catch (IOException e) {
					    System.out.println("Error writing to disk: " + entry.getValue().imagePath);
					}
				}
			}
		}
	}
	
	public void setImageWasModified(String imageName) {
		if (images.containsKey(imageName)) {
			images.get(imageName).wasModified = true;
		}
	}
	
	private boolean loadImage(String imageName) {
		String imageToLoad = imageDirectory + imageName + ".png";
		if (imageName.endsWith(".png")) {
			imageToLoad = imageDirectory + imageName;
		}
		System.out.print("Load image " + imageToLoad);
		BufferedImage loadedImage = null;
		try {
		    loadedImage = ImageIO.read(new File(imageToLoad));
		} catch (IOException e) {
			images.put(imageName, null);
			System.out.println(" - fail.");
			return false;
		}
		BufferedImage compatibleImage = CompatibleImageCreator.createCompatibleImage(loadedImage);
		images.put(imageName, new ImageRecord(compatibleImage, imageToLoad));
		System.out.println(" - OK.");
		calculateImageSizes();
		return true;
	}
	
	public Image getImage(String imageName) {
		if (!images.containsKey(imageName)) {
			if (!loadImage(imageName)) {
				System.out.println("Couldn't find image " + imageName);
				return null;
			}
		}
		return images.get(imageName).image;
	}
}

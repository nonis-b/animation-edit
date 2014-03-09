package graphicsutils;


import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class ImageStore {
	
	private Map<String, Image> images;
	private final String imageDirectory;
	private int maxImageWidth = 1;
	private int maxImageHeight = 1;
	
	public ImageStore(String imageDirectory) {
		if (!imageDirectory.endsWith("/")) {
			imageDirectory = imageDirectory + "/";
		}
		this.imageDirectory = imageDirectory;
		images = new HashMap<String, Image>();
	}
	
	/**
	 * Force reloading of all resources.
	 */
	public void reloadAll() {
		images = new HashMap<String, Image>();
	}
	
	private void calculateImageSizes() {
		maxImageWidth = 1;
		for (Map.Entry<String, Image> entry : images.entrySet()) {
			int val = entry.getValue().getWidth(null);
			if (val > maxImageWidth) {
				maxImageWidth = val;
			}
		}
		maxImageHeight = 1;
		for (Map.Entry<String, Image> entry : images.entrySet()) {
			int val = entry.getValue().getHeight(null);
			if (val > maxImageHeight) {
				maxImageHeight = val;
			}
		}
	}
	
	public int getMaxWidthOfImage() {
		return maxImageWidth;
	}
	
	public int getMaxHeightOfImage() {
		return maxImageHeight;
	}
	
	private boolean loadImage(String imageName) {
		String imageToLoad = imageDirectory + imageName + ".png";
		if (imageName.endsWith(".png")) {
			imageToLoad = imageDirectory + imageName;
		}
		System.out.print("Load image " + imageToLoad);
		Image loadedImage = null;
		try {
		    loadedImage = ImageIO.read(new File(imageToLoad));
		} catch (IOException e) {
			images.put(imageName, null);
			System.out.println(" - fail.");
			return false;
		}
		Image compatibleImage = CompatibleImageCreator.createCompatibleImage(loadedImage);
		images.put(imageName, compatibleImage);
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
		return images.get(imageName);
	}
}

package animationeditgui;

import java.awt.image.BufferedImage;

public class FloatingLayer {
	private BufferedImage image;
	private int posX;
	private int posY;
	
	public FloatingLayer(BufferedImage image, int posX, int posY) {
		this.image = image;
		this.posX = posX;
		this.posY = posY;
	}
	
	public int getPosX() {
		return posX;
	}
	
	public int getPosY() {
		return posY;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public void setCenter(int x, int y) {
		this.posX = x - image.getWidth()/2;
		this.posY = y - image.getHeight()/2;
	}
}

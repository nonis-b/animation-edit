package animationedit;

public class AnimationFrame {
	private String image;
	private int offsetX;
	private int offsetY;
	
	public AnimationFrame(String image, int offsetX, int offsetY) {
		this.image = image;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}
	
	public String toString() {
		return image;
	}
	
	public String getImage() {
		return image;
	}
	
	public int getOffsetX() {
		return offsetX;
	}

	public int getOffsetY() {
		return offsetY;
	}
	
	public void addToOffsetX(int delta) {
		offsetX += delta;
	}
	
	public void addToOffsetY(int delta) {
		offsetY += delta;
	}
}

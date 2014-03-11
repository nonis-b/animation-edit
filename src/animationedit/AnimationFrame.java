package animationedit;

public class AnimationFrame {
	private String image;
	private int offsetX;
	private int offsetY;
	private int tics;
	
	public AnimationFrame(String image, int offsetX, int offsetY, int tics) {
		this.image = image;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.tics = tics;
	}
	
	public String toString() {
		return image + " (" + tics + ")";
	}
	
	public String getImage() {
		return image;
	}
	
	public int getTics() {
		return tics;
	}
	
	public void setTics(int tics) {
		this.tics = tics;
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

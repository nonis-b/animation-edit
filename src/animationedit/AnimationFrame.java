package animationedit;

public class AnimationFrame {
	private String image;
	private int offsetX;
	private int offsetY;
	private int tics;
	private String next = "";

	private String tag = "";
	
	public AnimationFrame(String image, int offsetX, int offsetY, int tics, String tag, String next) {
		this.image = image;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.tics = tics;
		this.tag = tag;
		this.next = next;
		if (tag == null) tag = "";
		if (next == null) next = "";
	}
	
	public String toString() {
		return image + " (tics: " + tics + ") (tag: " + tag + ") (next: " + next + ")";
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
	
	public String getNext() {
		return next;
	}

	public void setNext(String next) {
		this.next = next;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public void addToOffsetX(int delta) {
		offsetX += delta;
	}
	
	public void addToOffsetY(int delta) {
		offsetY += delta;
	}
}

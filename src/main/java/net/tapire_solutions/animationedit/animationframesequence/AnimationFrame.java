package net.tapire_solutions.animationedit.animationframesequence;

public class AnimationFrame {
	private String image;
	private int offsetX;
	private int offsetY;
	private int tics;
	private String next = "";
	private String event = "";
	private int eventX = 0;
	private int eventY = 0;

	private String tag = "";
	
	public AnimationFrame(String image, int offsetX, int offsetY, int tics, String tag, String next, String event, int eventX, int eventY) {
		this.image = image;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.tics = tics;
		this.tag = tag;
		this.next = next;
		this.event = event;
		this.eventX = eventX;
		this.eventY = eventY;
		if (tag == null) tag = "";
		if (next == null) next = "";
		if (event == null) event = "";
	}
	
	@Override
	public String toString() {
		String ret = image;
		if (tics > 1) ret += " (tics: " + tics + ")";
		if (!tag.isEmpty()) ret += " (tag: " + tag + ")";
		if (!next.isEmpty()) ret += " (next: " + next + ")";
		if (offsetX != 0) ret += " (offsetX: " + offsetX + ")";
		if (offsetY != 0) ret += " (offsetY: " + offsetY + ")";
		if (!event.isEmpty()) ret += " (event: " + event + ")";
		return ret;
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
	
	public void setOffsetX(int offset) {
		offsetX = offset;
	}
	
	public void setOffsetY(int offset) {
		offsetY = offset;
	}
	
	public void addToOffsetX(int delta) {
		offsetX += delta;
	}
	
	public void addToOffsetY(int delta) {
		offsetY += delta;
	}
	
	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}
	
	public int getEventX() {
		return eventX;
	}

	public void setEventX(int eventX) {
		this.eventX = eventX;
	}
	
	public int getEventY() {
		return eventY;
	}

	public void setEventY(int eventY) {
		this.eventY = eventY;
	}
}

package graphicsutils;

public class GeometryUtil {
	public static boolean areRectsColliding(
			float r1x, float r1y, float r1w, float r1h,
			float r2x, float r2y, float r2w, float r2h) {		
		if (r1x+r1w < r2x || r1x > r2x+r2w || r1y+r1h < r2y || r1y > r2y+r2h) 
			return false;		
		return true;
	}
	
	public static boolean isRectContainedInRect(
			float r1x, float r1y, float r1w, float r1h,
			float r2x, float r2y, float r2w, float r2h) {	
		if (r1x < r2x || r1x+r1w > r2x+r2w || r1y < r2y || r1y+r1h > r2y+r2h) 
			return false;		
		return true;
	}
	
	public static boolean isPointInRect(float px, float py, float rx, float ry, float rw, float rh) {
		if (px < rx || px > rx+rw || py < ry || py > ry+rh)
			return false;
		return true;
	}
}

package drawingtools;

public interface ColorToleranceSelector {
	/**
	 * Tolerance for color difference for tools such as flood fill and color select etc.
	 * @return Tolerance value between 0 and 1.
	 */
	public float getColorTolerance();
}

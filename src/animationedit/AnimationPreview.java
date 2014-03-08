package animationedit;

import framemodel.AnimationFrame;
import graphicsutils.ImageStore;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

/**
 * Preview animation.
 *
 */
public class AnimationPreview extends JPanel {
    
    private int scrollX = 0;
    private int scrollY = 0; 
    private float scale = 1.0f;
    private Timer timer;
    private TimerTask timerTask;
    
    /** for accessing the gui components state */
    AnimationEditComponentsAccessor componentsAccessor;
    
    /**
     * Constructor.
     * @param componentsAccessor
     */
    public AnimationPreview(AnimationEditComponentsAccessor componentsAccessor) {
        this.componentsAccessor = componentsAccessor;
        setBackground(Color.WHITE);
        timer = new Timer();
        timerTask = new TimerTask() {
			@Override
			public void run() {
				onTimer();
			}
		};
        timer.schedule(timerTask, 1000, 20);
    }


    public void onTimer() {
    	repaint();
    }
    
    
    /**
     * Scroll position x
     * @return scrollX
     */
    public int getScrollX() {
        return scrollX;
    }
 
    /**
     * Scroll position y
     * @return scrollY
     */
    public int getScrollY() {
        return scrollY;
    }
    
    /**
     * Scroll in x direction.
     * @param length Pixels to scroll.
     */
    public void scrollX(int length) {
        scrollX += length/scale;
    }
    
    /**
     * Scroll in y direction.
     * @param length Pixels to scroll.
     */
    public void scrollY(int length) {
        scrollY += length/scale;
    }

    
    /**
     * Zoom view.
     * @param scaleFactor Positive will zoom in, negative out.
     */
    public void zoom(float scaleFactor) {
    	scale *= scaleFactor;
    	System.out.println("Change zoom to " + scale);
    }
    
    
    private int screenToModelCoord(int screenCoord) {
    	return (int)(screenCoord / scale);
    }

    
    private int modelToScreenCoord(int modelCoord) {
    	return (int)(modelCoord * scale);
    }
    
    
    /**
     * Paint.
     * @param g
     */
    @Override
    public void paint(Graphics g) {

        super.paint(g);

        ImageStore imageStore = componentsAccessor.getImageStore();
        if (imageStore != null) {
        	AnimationFrame frame = componentsAccessor.getSelectedAnimationFrame();
        	if (frame != null) {
        		Image image = imageStore.getImage(frame.getImage());
	        	if (image != null) {
	            	g.drawImage(image, 0, 0, Color.BLUE, this);
	            }
        	}
        	
        }
    }
   
}
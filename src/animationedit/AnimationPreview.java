package animationedit;

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
    private int currentAnimationFrameIndex = 0;
    private int currentFrameTics = 1;
    
    private AnimationEditComponentsAccessor componentsAccessor;
    private AnimationFrameSequenceInfoProvider animationFrameSequenceInfoProvider;
    

    public AnimationPreview(AnimationEditComponentsAccessor componentsAccessor, 
    		AnimationFrameSequenceInfoProvider animationFrameSequenceInfoProvider) {
    	this.componentsAccessor = componentsAccessor;
        this.animationFrameSequenceInfoProvider = animationFrameSequenceInfoProvider;
        setBackground(Color.LIGHT_GRAY);
        timer = new Timer();
        setUpdateSpeed(1000, 100);
    }


    private void onTimer() {
    	currentFrameTics--;
    	if (currentFrameTics <= 0) {
    		AnimationFrame oldFrame = animationFrameSequenceInfoProvider.getAnimationFrame(currentAnimationFrameIndex);
    		String next = "";
        	if (oldFrame != null) {
        		next = oldFrame.getNext();
        	}
        	if (next.isEmpty()) {
        		nextFrame();
        	} else {
        		gotoFrame(next);
        	}
    		AnimationFrame newFrame = animationFrameSequenceInfoProvider.getAnimationFrame(currentAnimationFrameIndex);
        	if (newFrame != null) {
        		currentFrameTics = newFrame.getTics();
        	}
    	}
    }
    
    private void gotoFrame(String tag) {
    	int nextIndex = animationFrameSequenceInfoProvider.getIndexOfAnimationFrameWithTag(tag);
		if (nextIndex >= 0) {
			currentAnimationFrameIndex = nextIndex;
		}
		repaint();
    }
    
    public void nextFrame() {
    	currentAnimationFrameIndex++; 
    	if (currentAnimationFrameIndex >= animationFrameSequenceInfoProvider.getNumAnimationFrames()) {
    		currentAnimationFrameIndex = 0;
    	}
    	repaint();
    	
    }
    
    public void previousFrame() {
    	currentAnimationFrameIndex--; 
    	if (currentAnimationFrameIndex < 0) {
    		currentAnimationFrameIndex = animationFrameSequenceInfoProvider.getNumAnimationFrames() - 1;
    	}
    	repaint();
    }
    
    
    public void setUpdateSpeed(long delay, long millis) {
    	if (timerTask != null) {
    		timerTask.cancel();
    	}
    	timerTask = new TimerTask() {
			@Override
			public void run() {
				onTimer();
			}
		};
		timer.schedule(timerTask, delay, millis);
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
        	AnimationFrame frame = animationFrameSequenceInfoProvider.getAnimationFrame(currentAnimationFrameIndex);
        	if (frame != null) {
        		Image image = imageStore.getImage(frame.getImage());
	        	if (image != null) {
	        		float scaleToFit = 1.0f;
        			float scaleToFitX = (float)getWidth() / (float)imageStore.getMaxWidthOfImage();
        			float scaleToFitY = (float)getHeight() / (float)imageStore.getMaxHeightOfImage();
	        		if (scaleToFitX > 1.0f || scaleToFitY > 1.0f) {
	        			scaleToFit = Math.min(scaleToFitX, scaleToFitY);
	        		} else {
	        			scaleToFit = Math.max(scaleToFitX, scaleToFitY);
	        		}
	        		g.drawImage(image, 
	            			getWidth()/2 - ((int)(imageStore.getMaxWidthOfImage()*scaleToFit))/2 + (int)(frame.getOffsetX()*scaleToFit), 
	            			getHeight()/2 - ((int)(imageStore.getMaxHeightOfImage()*scaleToFit))/2 + (int)(frame.getOffsetY()*scaleToFit),
	            			(int)(image.getWidth(null)*scaleToFit),
	            			(int)(image.getHeight(null)*scaleToFit),
	            			this);
	            }
        	}
        	
        }
    }
   
}
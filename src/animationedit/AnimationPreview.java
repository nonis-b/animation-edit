package animationedit;

import graphicsutils.ImageStore;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

/**
 * Shows the animation played back.
 *
 */
public class AnimationPreview extends JPanel {
    
    private Timer timer;
    private TimerTask timerTask;
    private int currentAnimationFrameIndex = 0;
    private int currentFrameTics = 1;
    
    private ImageStoreProvider imageStoreProvider;
    private AnimationFrameSequenceInfoProvider animationFrameSequenceInfoProvider;
    

    public AnimationPreview(ImageStoreProvider imageStoreProvider, 
    		AnimationFrameSequenceInfoProvider animationFrameSequenceInfoProvider) {
    	this.imageStoreProvider = imageStoreProvider;
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
     * Paint.
     * @param g
     */
    @Override
    public void paint(Graphics g) {

        super.paint(g);

        ImageStore imageStore = imageStoreProvider.getImageStore();
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
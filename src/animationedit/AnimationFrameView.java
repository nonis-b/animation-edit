package animationedit;

import graphicsutils.CompatibleImageCreator;
import graphicsutils.ImageStore;
import graphicsutils.PenDrawingTool;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

import javax.swing.JPanel;
import javax.swing.Scrollable;

/**
 * Panel showing the currently selected frame.
 * 
 * Listens to mouse clicks and key events.
 *
 */
public class AnimationFrameView
        extends JPanel
        implements MouseListener, MouseMotionListener, SelectedAnimationFrameChangeListener, Scrollable {
	
	private Image offscreenBufferImage;
    private float scale = 1.0f;
    private int numOnionSkin = 0;
    private AnimationEditComponentsAccessor componentsAccessor;
    private AnimationFrameSequenceInfoProvider animationFrameSequenceInfoProvider;
    private PenDrawingTool penDrawingTool = new PenDrawingTool();
    
    public AnimationFrameView(AnimationEditComponentsAccessor componentsAccessor, 
    		AnimationFrameSequenceInfoProvider animationFrameSequenceInfoProvider) {
        this.componentsAccessor = componentsAccessor;
        this.animationFrameSequenceInfoProvider = animationFrameSequenceInfoProvider;
        offscreenBufferImage = CompatibleImageCreator.createCompatibleImage(1000, 1000);
    }
    
    public void setOnionSkinDepth(int depth) {
    	numOnionSkin = depth;
    }
    
    @Override
    public void mousePressed(MouseEvent e) {    
    	BufferedImage image = getCurrentFrameBufferedImage();
		if (image != null) {
			penDrawingTool.onMouseDown(image, screenToModelCoord(e.getX()), screenToModelCoord(e.getY()));
			repaint();
		}
        e.consume();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    	BufferedImage image = getCurrentFrameBufferedImage();
		if (image != null) {
			penDrawingTool.onMouseRelease(image, screenToModelCoord(e.getX()), screenToModelCoord(e.getY()));
			repaint();
		}
        e.consume();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        repaint();
    }

    // implement mousemovelistener

    @Override
    public void mouseMoved(MouseEvent e) {
        // called during motion when no buttons are down
        e.consume();
    }

	@Override
	public void mouseDragged(MouseEvent e) {
		// called during motion with buttons down
		BufferedImage image = getCurrentFrameBufferedImage();
		if (image != null) {
			penDrawingTool.onMouseMoveWhileDown(image, screenToModelCoord(e.getX()), screenToModelCoord(e.getY()));
			repaint();
		}
		e.consume();
	}

	
	private BufferedImage getCurrentFrameBufferedImage() {
		ImageStore imageStore = componentsAccessor.getImageStore();
		if (imageStore != null) {
			AnimationFrame frame = animationFrameSequenceInfoProvider.getSelectedAnimationFrame();
			if (frame != null) {
				Image image = imageStore.getImage(frame.getImage());
				if (image != null) {
					if (image instanceof BufferedImage) {
						return (BufferedImage)image;
					}
				}
			}
		}
		return null;
	}
	
    
    /**
     * Zoom view.
     * @param scaleFactor Positive will zoom in, negative out.
     */
    public void zoom(float scaleFactor) {
    	scale *= scaleFactor;
    	setPreferredSize(new Dimension((int)(getWidth()*scale), (int)(getHeight()*scale)));
    	revalidate();
    	System.out.println("Change zoom to " + scale);
    }
    
    
    private int screenToModelCoord(int screenCoord) {
    	return (int)(screenCoord / scale);
    }

    
    public void maxSizeChanged(int maxX, int maxY) {
    	setPreferredSize(new Dimension((int)(maxX*scale), (int)(maxY*scale)));
    	revalidate();
    }
    
    
    private void drawImage(Graphics g, Image image, int x, int y, float alpha, String failName) {
    	if (image != null) {
    		if (alpha < 0.00001f) alpha = 0.01f;
    		float[] scales = { alpha, alpha, alpha, 1.0f };
			float[] offsets = new float[4];
			RescaleOp rop = new RescaleOp(scales, offsets, null);
			((Graphics2D)(offscreenBufferImage.getGraphics())).drawImage((BufferedImage)image, rop, x, y);
			g.drawImage(offscreenBufferImage, (int)(x*scale), (int)(y*scale), 
					(int)(image.getWidth(null)*scale), (int)(image.getHeight(null)*scale), 
					0, 0, image.getWidth(null), image.getHeight(null), null);
			
        } else {
			g.setColor(Color.WHITE);
			String failText = "No image with found with name " + failName;
			g.drawString(failText,
					getWidth()/2 - (int)g.getFontMetrics().getStringBounds(failText, g).getWidth()/2, 
					getHeight()/2 - g.getFontMetrics().getHeight()/2);
		}
    }
    
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());

        ImageStore imageStore = componentsAccessor.getImageStore();
        if (imageStore == null) return;

    	for (int i = -numOnionSkin; i < 0; i++) {
		    	AnimationFrame frame = animationFrameSequenceInfoProvider.getAnimationFrame(
		    			animationFrameSequenceInfoProvider.getSelectedAnimationFrameIndex() + i);
	    	if (frame != null) {
	    		Image image = imageStore.getImage(frame.getImage());
	    		drawImage(g, image, frame.getOffsetX(), frame.getOffsetY(), 
	    				0.7f - ((float)Math.abs(i))/(5), frame.getImage());
	    	}
    	}
    	for (int i = numOnionSkin; i > 0; i--) {
	    	AnimationFrame frame = animationFrameSequenceInfoProvider.getAnimationFrame(
	    			animationFrameSequenceInfoProvider.getSelectedAnimationFrameIndex() + i);
	    	if (frame != null) {
	    		Image image = imageStore.getImage(frame.getImage());
	    		drawImage(g, image, frame.getOffsetX(), frame.getOffsetY(), 
	    				0.7f - ((float)Math.abs(i))/(5), frame.getImage());
	    	}
    	}
    	
    	AnimationFrame frame = animationFrameSequenceInfoProvider.getSelectedAnimationFrame();
    	if (frame != null) {
    		Image image = imageStore.getImage(frame.getImage());
    		drawImage(g, image, frame.getOffsetX(), frame.getOffsetY(), 1.0f, frame.getImage());
    	}
    
    }

	@Override
	public void onSelectedAnimationFrameChanged() {
		repaint();
	}

	@Override
	public Dimension getPreferredScrollableViewportSize() {
		return super.getPreferredSize();
	}

	@Override
	public int getScrollableBlockIncrement(Rectangle arg0, int arg1, int arg2) {
		return 100;
	}

	@Override
	public boolean getScrollableTracksViewportHeight() {
		return false;
	}

	@Override
	public boolean getScrollableTracksViewportWidth() {
		return false;
	}

	@Override
	public int getScrollableUnitIncrement(Rectangle arg0, int arg1, int arg2) {
		return 5;
	}
}
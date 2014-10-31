package net.tapire_solutions.animationedit.graphicsutils;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

public class CompatibleImageCreator {
	
	/**
	 * Create image optimized for drawing on current system.
	 * @param oldImage Image to be optimized.
	 * @return Optimized image.
	 */
	public static BufferedImage createCompatibleImage(Image oldImage) {

        GraphicsConfiguration graphicsConfiguration
                = GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .getDefaultConfiguration();
         
        BufferedImage compatibleImage
                = graphicsConfiguration.createCompatibleImage(
                oldImage.getWidth(null),
                oldImage.getHeight(null), 
                Transparency.TRANSLUCENT);

        Graphics g = compatibleImage.getGraphics();
        g.drawImage(oldImage, 0, 0, null);
        g.dispose();
        
        return compatibleImage;
	}
	
	/**
	 * Create image optimized for drawing on current system.
	 * @param w Width.
	 * @param h Height.
	 * @return Optimized image.
	 */
	public static BufferedImage createCompatibleImage(int w, int h) {

        GraphicsConfiguration graphicsConfiguration
                = GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .getDefaultConfiguration();
         
        BufferedImage compatibleImage
                = graphicsConfiguration.createCompatibleImage(w, h, Transparency.TRANSLUCENT);

        Graphics g = compatibleImage.getGraphics();
        g.dispose();
        
        return compatibleImage;
	}
}

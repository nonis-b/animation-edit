package graphicsutils;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;

import org.junit.Test;

public class ImageWithHistoryTest {

	
	
	@Test
	public void testModifyThenUndo() {
		BufferedImage originalImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
		originalImage.setRGB(0, 0, 0x00330000);
		ImageWithHistory imageWithHistory = new ImageWithHistory(originalImage, 30);
		
		// do some modification then undo back to original state
		assertEquals(0x00330000, imageWithHistory.getAsBufferedImage().getRGB(0, 0));
		imageWithHistory.getAsBufferedImage().setRGB(0, 0, 0x00110000);
		imageWithHistory.wasModified();
		assertEquals(0x00110000, imageWithHistory.getAsBufferedImage().getRGB(0, 0));
		imageWithHistory.getAsBufferedImage().setRGB(0, 0, 0x00FF0000);
		imageWithHistory.wasModified();
		assertEquals(0x00FF0000, imageWithHistory.getAsBufferedImage().getRGB(0, 0));
		imageWithHistory.undoLastModification();
		assertEquals(0x00110000, imageWithHistory.getAsBufferedImage().getRGB(0, 0));
		imageWithHistory.undoLastModification();
		assertEquals(0x00330000, imageWithHistory.getAsBufferedImage().getRGB(0, 0));
		imageWithHistory.undoLastModification();
		assertEquals(0x00330000, imageWithHistory.getAsBufferedImage().getRGB(0, 0)); // no more undos
		
		// repeat once more
		assertEquals(0x00330000, imageWithHistory.getAsBufferedImage().getRGB(0, 0));
		imageWithHistory.getAsBufferedImage().setRGB(0, 0, 0x00110000);
		imageWithHistory.wasModified();
		assertEquals(0x00110000, imageWithHistory.getAsBufferedImage().getRGB(0, 0));
		imageWithHistory.getAsBufferedImage().setRGB(0, 0, 0x00FF0000);
		imageWithHistory.wasModified();
		assertEquals(0x00FF0000, imageWithHistory.getAsBufferedImage().getRGB(0, 0));
		imageWithHistory.undoLastModification();
		assertEquals(0x00110000, imageWithHistory.getAsBufferedImage().getRGB(0, 0));
		imageWithHistory.undoLastModification();
		assertEquals(0x00330000, imageWithHistory.getAsBufferedImage().getRGB(0, 0));
		imageWithHistory.undoLastModification();
		assertEquals(0x00330000, imageWithHistory.getAsBufferedImage().getRGB(0, 0)); // no more undos
	}
	
	@Test
	public void testUseUpAllUndoStates() {
		BufferedImage originalImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
		originalImage.setRGB(0, 0, 0x00330000);
		ImageWithHistory imageWithHistory = new ImageWithHistory(originalImage, 3);
		
		assertEquals(0x00330000, imageWithHistory.getAsBufferedImage().getRGB(0, 0));
		imageWithHistory.getAsBufferedImage().setRGB(0, 0, 0x00110000);
		imageWithHistory.wasModified();
		assertEquals(0x00110000, imageWithHistory.getAsBufferedImage().getRGB(0, 0));
		imageWithHistory.getAsBufferedImage().setRGB(0, 0, 0x00FF0000);
		
		// now the max size should be used up. Adding one more should drop the originalImage reference.
		imageWithHistory.wasModified();
		assertEquals(0x00FF0000, imageWithHistory.getAsBufferedImage().getRGB(0, 0));
		imageWithHistory.getAsBufferedImage().setRGB(0, 0, 0x00FFFF00);

		imageWithHistory.undoLastModification();
		assertEquals(0x00110000, imageWithHistory.getAsBufferedImage().getRGB(0, 0));
		imageWithHistory.undoLastModification();
		assertEquals(0x00110000, imageWithHistory.getAsBufferedImage().getRGB(0, 0)); // no more undos
	}

}

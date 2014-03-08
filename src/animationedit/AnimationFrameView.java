package animationedit;

import graphicsutils.GridDrawingUtil;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Panel showing the currently selected frame.
 * 
 * Listens to mouse clicks and key events.
 *
 */
public class AnimationFrameView
        extends JPanel
        implements MouseListener, MouseMotionListener {
    
    /** Scroll of map. */
    private int scrollX = 0;
    
    /** Scroll of map. */
    private int scrollY = 0; 

    /** Layer currently edited. */
    private int editlayer = 0;

    /** Last edited tile, used to draw lines. */
    private int lastEditedTileX = 0;

    /** Last edited tile, used to draw lines. */
    private int lastEditedTileY = 0;
        
    /** Want second click to draw the line. */
    private boolean lineToolFirstClick = true;
    
    /** For saving undo state while press. */
    private long lastMousePressStateSaveTime = 0;
    
    /** Scale of view */
    private float scale = 1.0f;
    
    /** for accessing the gui components state */
    AnimationEditComponentsAccessor componentsAccessor;
    
    /**
     * Constructor.
     * @param componentsAccessor.getConfig()
     * @param toolSelector
     * @param componentsAccessor
     */
    public AnimationFrameView(AnimationEditComponentsAccessor componentsAccessor) {
        this.componentsAccessor = componentsAccessor;
        setBackground(componentsAccessor.getConfig().bgCol);
    }

    @Override
    public void mousePressed(MouseEvent e) {    	
        click(screenToModelCoord(e.getX()), screenToModelCoord(e.getY()), true);
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        click(screenToModelCoord(e.getX()), screenToModelCoord(e.getY()), false);
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
        if (componentsAccessor.getSelectedTool() == ToolSelector.Tool.SET_TILE
                || componentsAccessor.getSelectedTool() == ToolSelector.Tool.DELETE_TILE) {
            click(screenToModelCoord(e.getX()), screenToModelCoord(e.getY()), true);
            repaint();
            e.consume();
        }
    }
    
    
    
	/**
	 * Map click to tool action.
	 * 
	 * @param x
	 *            Mouse pos x (model coords)
	 * @param y
	 *            Mouse pos y (model coords)
	 * @param repeated
	 *            If the click event is from a "press down".
	 */
	public void click(int x, int y, boolean repeated) {

		switch (componentsAccessor.getSelectedTool()) {

		case NEW_DUMMY:
//			if (!repeated) {
//				componentsAccessor.getLevelModel().isAboutToAlterState();
//				DummyObject d = componentsAccessor.createNewDummyFromSelectedType();
//				componentsAccessor.getLevelModel().getDummyObjects().newDummy(x + scrollX, y + scrollY, d);
//			}
			break;

		case SELECT_DUMMY:
//			componentsAccessor.getLevelModel().getDummyObjects().selectDummy(x + scrollX, y + scrollY);
			break;

		case DELETE_DUMMY:
//			componentsAccessor.getLevelModel().isAboutToAlterState();
//			componentsAccessor.getLevelModel().getDummyObjects().deleteDummy(x + scrollX, y + scrollY);
			break;

		case SET_TILE:
		case DELETE_TILE:
//			if (!repeated || System.currentTimeMillis() - lastMousePressStateSaveTime > 500) {
//				lastMousePressStateSaveTime = System.currentTimeMillis();
//				componentsAccessor.getLevelModel().isAboutToAlterState();
//			}
//			if (componentsAccessor.getSelectedTileIndex() != -1) {
//				setTileVal(x, y, componentsAccessor.getSelectedTileIndex());
//			}
			break;
			
		case FILL_TILE:
		case LINE_TILE:
//			if (!repeated || System.currentTimeMillis() - lastMousePressStateSaveTime > 500) {
//				lastMousePressStateSaveTime = System.currentTimeMillis();
//				componentsAccessor.getLevelModel().isAboutToAlterState();
//			}
//			if (componentsAccessor.getSelectedTileIndex() != -1) {
//				setTileVal(x, y, componentsAccessor.getSelectedTileIndex());
//			}
			break;
			
		case PICKUP_TILE:
//			if (componentsAccessor.getSelectedTileIndex() != -1) {
//				setTileVal(x, y, componentsAccessor.getSelectedTileIndex());
//			}
			break;
		}
		requestFocusInWindow();
	}
    
    
    /**
     * Sets tile at mouse click, decides how to behave dependent on selected 
     * tool.
     * @param x Mouse x (model coords)
     * @param y Mouse y (model coords)
     * @param tileIndex Tile index number
     */
//    public void setTileVal(int x, int y, int tileIndex) {
//		// to tile index
//        int tX = (x + getScrollX()) / componentsAccessor.getConfig().representationTileSize;
//		int tY = (y + getScrollY()) / componentsAccessor.getConfig().representationTileSize;
//
//        // tiles are 1 indexed.
//        tileIndex++;
//        
//        // set tile
//        switch (componentsAccessor.getSelectedTool()) {
//            case SET_TILE:
//            	componentsAccessor.getLevelModel().getTileMap().setTileVal(tX, tY, editlayer, tileIndex);
//                break;
//            case DELETE_TILE:
//            	componentsAccessor.getLevelModel().getTileMap().setTileVal(tX, tY, editlayer, 0);
//                break;
//            case PICKUP_TILE:
//            	componentsAccessor.setSelectedTileIndex(tileIndex);
//                break;
//            case FILL_TILE:
//            	componentsAccessor.getLevelModel().getTileMap().fill(editlayer, tX, tY,
//            			componentsAccessor.getLevelModel().getTileMap().getTileVal(tX, tY, editlayer),
//                    tileIndex);
//                break;
//            case LINE_TILE:
//                    
//                
//                if (lineToolFirstClick) {
//                    lineToolFirstClick = false;
//                    componentsAccessor.getLevelModel().getTileMap().setTileVal(tX, tY, editlayer, tileIndex);
//                }
//                else {
//                	componentsAccessor.getLevelModel().isAboutToAlterState();
//                	componentsAccessor.getLevelModel().getTileMap().drawLine(editlayer, lastEditedTileX,
//                        lastEditedTileY, tX, tY, tileIndex);
//                }
//                break;
//		case DELETE_DUMMY:
//			break;
//		case NEW_DUMMY:
//			break;
//		case SELECT_DUMMY:
//			break;
//		default:
//			break;
//        }
//
//        lastEditedTileX = tX;
//        lastEditedTileY = tY;
//        if (componentsAccessor.getSelectedTool() != ToolSelector.Tool.LINE_TILE) {
//            lineToolFirstClick = true;
//        }
//    }

    /**
     * Select next layer in tilemap for editing.
     */
    public void selectNextLayer() {
//        if (editlayer + 1 >= componentsAccessor.getLevelModel().getTileMap().getNumLayers()) return;
//        editlayer ++ ;
    }
    
    /**
     * Select previous layer in tilemap for editing.
     */
    public void selectPrevLayer() {
        if (editlayer <= 0) return;
        editlayer -- ;
    }
    
    /**
     * Get currently edited layer. 
     */
    public int getSelectedLayer() {
        return editlayer;
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

        Image image = componentsAccessor.getImageStore().getImage(componentsAccessor.getSelectedAnimationFrame().getImage());
        
        if (image != null) {
        	g.drawImage(image, 0, 0, Color.BLACK, this);
        }
        
//        // draw tiles
//        for (int i = 0; i < componentsAccessor.getLevelModel().getTileMap().getNumLayers(); i++) {
//            paintMap(g, componentsAccessor.getLevelModel().getTileMap().getMap(i));
//        }
//
//        drawDummyObjects(g);
//
//        // show tile map borders
//        GridDrawingUtil.drawBoundingBox(Color.CYAN, g, 
//        		modelToScreenCoord(-getScrollX()),
//        		modelToScreenCoord(-getScrollY()), 
//        		modelToScreenCoord(componentsAccessor.getLevelModel().getTileMap().getWidth() * componentsAccessor.getConfig().representationTileSize - getScrollX()),
//        		modelToScreenCoord(componentsAccessor.getLevelModel().getTileMap().getHeight() * componentsAccessor.getConfig().representationTileSize - getScrollY()));
//
//        drawSelectedDummyIndicator(g);
//        
//        // show tile map layer
//        g.setColor(Color.BLACK);
//        g.drawString("Editing tilemap layer " + (editlayer+1) + "/" + 
//        		componentsAccessor.getLevelModel().getTileMap().getNumLayers(), 5, getHeight() 
//                - g.getFontMetrics().getHeight());
        
    }
   
}
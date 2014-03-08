package animationedit;

import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import framemodel.AnimationFrame;

/**
 * Linear list of AnimationFrames.
 */
public class AnimationFrameSelector extends JScrollPane {

	private JList frames;

	public AnimationFrameSelector() {
		frames = new JList();
		frames.setVisibleRowCount(3);
		frames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setViewportView(frames);
	}

	public void setAnimationFrames(ArrayList<AnimationFrame> animationFrames) {
		frames.setListData(animationFrames.toArray());
	}
	
	public AnimationFrame getSelected() {
		return (AnimationFrame)frames.getSelectedValue();
	}
}

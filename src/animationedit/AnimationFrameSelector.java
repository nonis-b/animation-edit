package animationedit;

import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import framemodel.AnimationFrame;

/**
 * Linear list of AnimationFrames.
 */
public class AnimationFrameSelector extends JScrollPane implements ListSelectionListener {

	private JList frames;
	private SelectedAnimationChangeListener listener;

	public AnimationFrameSelector(SelectedAnimationChangeListener listener) {
		frames = new JList();
		frames.setVisibleRowCount(3);
		frames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setViewportView(frames);
		this.listener = listener;
		frames.addListSelectionListener(this);
	}

	public void setAnimationFrames(ArrayList<AnimationFrame> animationFrames) {
		frames.setListData(animationFrames.toArray());
		frames.setSelectedIndex(0);
	}
	
	public AnimationFrame getSelected() {
		return (AnimationFrame)frames.getSelectedValue();
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (listener != null) {
			listener.onSelectedAnimationChanged();
		}
	}
	
	
}

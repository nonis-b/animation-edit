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
public class AnimationFrameSelector extends JScrollPane 
	implements ListSelectionListener, 
				AnimationFrameSequenceChangedListener {

	private JList guiList;
	private SelectedAnimationChangeListener listener;
	private AnimationFrame selectedFrame;
	private ArrayList<AnimationFrame> animationFrames;
	
	public AnimationFrameSelector(SelectedAnimationChangeListener listener) {
		guiList = new JList();
		guiList.setVisibleRowCount(3);
		guiList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setViewportView(guiList);
		this.listener = listener;
		guiList.addListSelectionListener(this);
		animationFrames = null;
	}

	public void setAnimationFrames(ArrayList<AnimationFrame> animationFrames) {
		int selected = guiList.getSelectedIndex();
		if (selected < 0) selected = 0;
		guiList.setListData(animationFrames.toArray());
		guiList.setSelectedIndex(selected);
		this.animationFrames = animationFrames;
	}
	
	public AnimationFrame getSelected() {
		return (AnimationFrame)guiList.getSelectedValue();
	}
	
	@Override
	public void onAnimationFrameSequenceChanged() {
		if (animationFrames == null) return;
		guiList.setSelectedIndex(animationFrames.indexOf(selectedFrame));
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		selectedFrame = getSelected();
		if (listener != null) {
			listener.onSelectedAnimationChanged();
		}
	}
	
	
}

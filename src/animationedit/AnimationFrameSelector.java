package animationedit;

import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


/**
 * Linear list of AnimationFrames where one can be selected.
 */
public class AnimationFrameSelector extends JScrollPane 
	implements ListSelectionListener, 
				AnimationFrameSequenceChangedListener {

	private JList guiList;
	private SelectedAnimationFrameChangeListener listener;
	private AnimationFrame selectedFrame;
	private ArrayList<AnimationFrame> animationFrames;
	
	public AnimationFrameSelector(SelectedAnimationFrameChangeListener listener) {
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
	
	public int getSelectedIndex() {
		return guiList.getSelectedIndex();
	}
	
	@Override
	public void onAnimationFrameSequenceChanged() {
		if (animationFrames == null) return;
		AnimationFrame tempSelected = selectedFrame; // will be set when JList is set
		guiList.setListData(animationFrames.toArray());
		guiList.setSelectedIndex(animationFrames.indexOf(tempSelected));
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		selectedFrame = getSelected();
		if (listener != null) {
			listener.onSelectedAnimationFrameChanged();
		}
	}
	
	
}

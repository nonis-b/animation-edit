package animationeditgui;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class FrameListButtonBar extends JPanel {

	private ButtonHandler buttonHandler = new ButtonHandler();

	public interface FrameListButtonBarListener {
		public void onMoveFrameUpButton();

		public void onMoveFrameDownButton();

		public void onEditFrameInfoButton();

		public void onDeleteFrameButton();

		public void onCopyFrameNewImageButton();

		public void onCopyFrameSameImageButton();
	}

	private class ButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			((ActionButton) event.getSource()).action.run();
		}
	}

	private class ActionButton extends JButton {
		public Runnable action;

		public ActionButton(ImageIcon image, Runnable action) {
			super(image);
			this.action = action;
		}
	}

	private void createButton(String imagePath, Runnable action) {
		Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource(
				"/"+imagePath)).getScaledInstance(24, 24, Image.SCALE_SMOOTH); // TODO: magic numbers
		ActionButton button = new ActionButton(new ImageIcon(image), action);
		button.addActionListener(buttonHandler);
		add(button);
	}

	public FrameListButtonBar(final FrameListButtonBarListener listener) {
		super();
		createButton("res/buttonCopyFrameNewImage.png", new Runnable() {
			@Override
			public void run() {
				listener.onCopyFrameNewImageButton();
			}
		});
		createButton("res/buttonCopyFrameSameImage.png", new Runnable() {
			@Override
			public void run() {
				listener.onCopyFrameSameImageButton();
			}
		});
		createButton("res/buttonDeleteFrame.png", new Runnable() {
			@Override
			public void run() {
				listener.onDeleteFrameButton();
			}
		});
		createButton("res/buttonEditFrameInfo.png", new Runnable() {
			@Override
			public void run() {
				listener.onEditFrameInfoButton();
			}
		});
		createButton("res/buttonMoveFrameDown.png", new Runnable() {
			@Override
			public void run() {
				listener.onMoveFrameDownButton();
			}
		});
		createButton("res/buttonMoveFrameUp.png", new Runnable() {
			@Override
			public void run() {
				listener.onMoveFrameUpButton();
			}
		});
		setLayout(new GridLayout(1, 6));
	}
}

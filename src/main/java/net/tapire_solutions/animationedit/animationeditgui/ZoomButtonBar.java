package net.tapire_solutions.animationedit.animationeditgui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;


class ZoomButtonBar extends JPanel {
	
	private JButton zoomInButton;
	private JButton zoomOutButton;
	private ZoomChangeListener listener;
	
	public static interface ZoomChangeListener {
		public void onZoomIn();
		public void onZoomOut();
	}
	
	public ZoomButtonBar(ZoomChangeListener listenerTemp) {
		this.listener = listenerTemp;
		zoomInButton = new JButton("+");
		zoomOutButton = new JButton("-");
		zoomInButton.setPreferredSize(new Dimension(30, 30));
		zoomOutButton.setPreferredSize(new Dimension(30, 30));
		add(new JLabel("Zoom:"));
		add(zoomOutButton);
		add(zoomInButton);
		zoomInButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				listener.onZoomIn();
			}
		});
		zoomOutButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				listener.onZoomOut();
			}
		});
	}
}
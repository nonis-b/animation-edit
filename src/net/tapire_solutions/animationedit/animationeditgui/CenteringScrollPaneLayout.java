package net.tapire_solutions.animationedit.animationeditgui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.ScrollPaneLayout;

public class CenteringScrollPaneLayout extends ScrollPaneLayout {
	public void layoutContainer(Container parent) {
		super.layoutContainer(parent);
		Component view = viewport.getView();
		if (view != null) {
			Dimension viewPortSize = viewport.getSize();
			Dimension viewSize = view.getSize();

			if ((viewPortSize.width > viewSize.width) || (viewPortSize.height > viewSize.height)) {

				int spaceX = (viewPortSize.width - viewSize.width) / 2;
				int spaceY = (viewPortSize.height - viewSize.height) / 2;
				if (spaceX < 0)
					spaceX = 0;
				if (spaceY < 0)
					spaceY = 0;

				viewport.setLocation(spaceX, spaceY);
				viewport.setSize(viewPortSize.width - spaceX, viewPortSize.height - spaceY);
			}
		}
	}
}

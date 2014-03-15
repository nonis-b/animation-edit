package animationedit;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

public class HelpWindow {

	public static void show() {
		JEditorPane editorPane = new JEditorPane("text/html", "<html><body>"
	    		+ "<p>Help content TODO.</p>"
	    		+ "<p>This application is free software.</p>"
	    		+ "<p>Get the source code: "
	            + "<a href=\"https://github.com/jonath0000/animation-edit/\">" 
	    		+ "https://github.com/jonath0000/animation-edit</a>"
	            + "</body></html> </p>");

		editorPane.addHyperlinkListener(new HyperlinkListener() {
			@Override
			public void hyperlinkUpdate(HyperlinkEvent event) {
				
				if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED 
						&& Desktop.isDesktopSupported()) {
					try {
						Desktop.getDesktop().browse(event.getURL().toURI());
					} catch (IOException exception) { 
						System.out.println("Couldn't open URL.");
					} catch (URISyntaxException exception) {
						System.out.println("Couldn't open URL.");
					}
				} else { 
					System.out.println("Couldn't open URL.");
				}
			}
		});
	    editorPane.setEditable(false);
	    JOptionPane.showMessageDialog(null, editorPane);
	}

}

import javax.swing.*;

public class SnakeMain {
	JFrame      mainWindow;
	SnakePanel helloPanel;
	
	// Create a new window, and put helloPanel inside it.
	public SnakeMain() {
		mainWindow = new JFrame("Counter-Snake");
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setResizable(false);
		
		helloPanel = new SnakePanel();
		mainWindow.getContentPane().add(helloPanel);
        
		mainWindow.pack();
		mainWindow.setVisible(true);
	}
    
	private static void createAndShowGUI() {
		// Make sure we have nice window decorations.
		JFrame.setDefaultLookAndFeelDecorated(true);
		// Create an actual instance of HelloBounceApp
		new SnakeMain();		
	}

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}

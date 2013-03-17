import javax.swing.*;

public class SnakeMain {
	JFrame     mainWindow;
	SnakePanel snakePanel;
	
	// Create a new window, and put snakePanel inside it.
	public SnakeMain() {
		mainWindow = new JFrame("Counter-Snake");
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setResizable(false);
		
		snakePanel = new SnakePanel(mainWindow);
		mainWindow.getContentPane().add(snakePanel);
        
		mainWindow.pack();
		mainWindow.setVisible(true);
	}
    
	private static void createAndShowGUI() {
		// Make sure we have nice window decorations.
		JFrame.setDefaultLookAndFeelDecorated(true);
		// Create an actual instance of SnakeMain
		new SnakeMain();		
	}
	
	public static void main() {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}

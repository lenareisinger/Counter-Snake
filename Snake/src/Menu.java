import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Menu extends JFrame implements ActionListener {
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	JButton		start;
	Container	cont;
	
	// Create a new window, and put helloPanel inside it.
	public Menu() {
		super("Counter-Snake");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setSize(400, 400);
		this.setVisible(true);
		this.setLayout(null);
		
		cont=this.getContentPane();
		
		start = new JButton("Start game");
		cont.add(start);
        start.setBounds(50,50,175,60);
        start.addActionListener(this);
	}
	
	public static void main(String[] args) {
		new Menu();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				SnakeMain.main();
			}
		});
		this.dispose();
	}
}

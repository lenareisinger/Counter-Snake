import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Menu extends JFrame implements ActionListener {
	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	JButton		start, inst;
	Container	cont;
	Image background;
	JLabel bg;
	TextArea ins;

	// Create a new window, and put helloPanel inside it.
	public Menu() {
		super("Counter-Snake");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setSize(600, 600);
		this.setVisible(true);
		this.setLayout(null);
		this.setLocationRelativeTo(null);

		cont=this.getContentPane();

		try {
			background = ImageIO.read(new File("menubg.png"));
		} catch (IOException e) {
		}

		ins = new TextArea("", 10, 10, TextArea.SCROLLBARS_NONE);
		cont.add(ins);
		ins.setBounds(300,200,250,150);
		ins.setFont(new Font("TimesRoman", Font.PLAIN, 14));
		ins.setForeground(Color.green);
		ins.setBackground(new Color(25,0,100));
		ins.setEditable(false);
		ins.setVisible(false);
		
		ins.append("Blue snake controls - arrows\n");
		ins.append("Blue snake shooting - space\n");
		ins.append("Red snake controls - WASD\n");
		ins.append("Red snake shooting - G\n");
		ins.append("Eat food - green squares\n");
		ins.append("Beware of obstacles - grey squares\n");
		ins.append("Be careful with black holes!\n");
		ins.append("The longest snake in the end wins\n");

		bg = new JLabel(new ImageIcon(background));
		cont.add(bg);
		bg.setBounds(0,0,600,600);

		inst = new JButton("Instructions");
		cont.add(inst);
		inst.setBounds(350,80,150,60);
		inst.addActionListener(this);
		inst.setFont(new Font("TimesRoman", Font.ITALIC, 20));

		start = new JButton("Start game");
		cont.add(start);
		start.setBounds(58,207,200,100);
		start.addActionListener(this);
		start.setFont(new Font("TimesRoman", Font.BOLD, 30));


	}

	public static void main(String[] args) {
		new Menu();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == inst) {
			if(ins.isVisible()) {
				ins.setVisible(false);
			}
			else {
				ins.setVisible(true);
			}
		}
		if (e.getSource() == start) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					SnakeMain.main();
				}
			});
			this.dispose();
		}
	}
}

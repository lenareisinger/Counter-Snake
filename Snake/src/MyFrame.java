import javax.swing.*;

import java.awt.*;

public class MyFrame extends JFrame{
	private Container cont;
	private JLabel label, background ;
	private JButton button;
	
	public MyFrame() {
		super("Hello Snake");
		this.setSize(300, 300);
		this.setLayout(null);
        this.setVisible(true);
        this.setResizable(false);
        // this is a comment
        cont=this.getContentPane();
        
        label=new JLabel("Hello Snake!");
        cont.add(label);
        label.setBounds(20,50,200,50);
        
        button=new JButton("Hello Snake!");
        cont.add(button);
        button.setBounds(20,150,200,50);
        
        background=new JLabel(new ImageIcon("Green.jpg"));
        cont.add(background);
        background.setBounds(0,0,300,300);
        background.setVisible(true);
        
	}

	public static void main(String[] args) {
		new MyFrame();

	}

}

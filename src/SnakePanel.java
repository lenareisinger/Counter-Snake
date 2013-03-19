import java.awt.*;

import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
//test
public class SnakePanel extends JComponent 
implements ComponentListener, KeyListener, Runnable {

	final static long serialVersionUID = 0;  // kill warning
	final static BasicStroke stroke = new BasicStroke(2.0f); // constants

	boolean alive;
	JFrame mainWindow;
	float xsize,ysize; // size of window

	boolean readyToMove1 = true;
	boolean readyToMove2 = true;

	float[] xp = new float[100];	// x position of snake    // WE CAN DELETE THIS, but its still used in angels shooting...
	float[] yp = new float[100];    // y position of snake
	float[] xp2 = new float[100];	// x position of snake    // WE CAN DELETE THIS, but its still used in angels shooting...
	float[] yp2 = new float[100];

	float[] tx = new float[100];	// x position of blackhole
	float[] ty = new float[100];	// y position of blackhole

	SnakeHead player1;
	SnakeHead player2;
	int initialSize = 8;
	int blackHoleNumber = 0;
	int foodCount = 0;
	float xlen,ylen; // size of snake
	float dx,dy, dx2, dy2; // current speed + direction of snake
	boolean start = true;
	float xp0, yp0, yp20, xp20;

	boolean shootActivate1 = false; // checks if trigger key 1 is hit
	boolean shootActivate2 = false; // check if trigger key 2 is hit
	int ammunitionsNumber1, ammunitionsNumber2; //number of ammunitions
	int bulletNum1, bulletNum2;
	float speedBx, speedBy;

	float xb,yb; //coordinates of bullet;

	float randomNumber1, randomNumber2, random3, random4, xval, yval, xdif, ydif;
	BufferedImage background, headB, headR;

	int delay; // delay between frames in milliseconds
	Thread animThread; // animation thread

	// SnakePanel constructor
	public SnakePanel(JFrame mw) {
		//
		mainWindow = mw;
		alive = true;

		//loads background
		try {
			background = ImageIO.read(new File("bg3.png"));
			headB = ImageIO.read(new File("headBlue.png"));
			headR = ImageIO.read(new File("headRed.png"));
		} catch (IOException e) {
		}

		// set values for all the variables
		xsize = 600;
		ysize = 600;
		xlen = 20;
		ylen = 20;
		dx = 0;
		dy = 20;
		xp0=200;
		yp0=300;
		xp20=400;
		yp20=300;

		dx2 = 0;
		dy2 = -20;

		delay = 100;

		randomNumber1 = (xlen)*Math.round(Math.random()*((xsize/xlen)-1));
		randomNumber2 = (ylen)*Math.round(Math.random()*((ysize/ylen)-1));

		player1 = new SnakeHead(initialSize, xp0, yp0);
		player1.setPos(player1.first, xp0, yp0, dx, dy);

		player2 = new SnakeHead(initialSize, xp20, yp20);
		player2.setPos(player2.first, xp20, yp20, dx2, dy2);

		xp = player1.getArrX();
		yp = player1.getArrY();

		xp2 = player2.getArrX();
		yp2 = player2.getArrY();

		// set up window properties
		setBackground(Color.white);
		setOpaque(true);
		setPreferredSize(new Dimension((int) xsize, (int) ysize));
		setFocusable(true);
		addComponentListener(this);

		bulletNum1 = 5; //initial number of bullets for player1
		bulletNum2 = 5; //initial number of bullets for player2
		ammunitionsNumber1 = bulletNum1;
		ammunitionsNumber2 = bulletNum2;

		// start the animation thread
		animThread = new Thread(this);
		animThread.start();

		addKeyListener(this);
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();

		// get the current window size
		Dimension dim = getSize();
		xsize = dim.width;
		ysize = dim.height;

		// clear background to white
		g2.drawImage(background, 0, 0, null);

		//draw food
		g2.setPaint(Color.green);
		g2.fill(new Rectangle2D.Double(randomNumber1, randomNumber2, xlen, ylen));
		g2.setColor(Color.black);
		g2.draw(new Rectangle2D.Double(randomNumber1, randomNumber2, xlen, ylen));

		//draw black holes
		for (int j=0;j<blackHoleNumber;j++){
			g2.setPaint(Color.black);
			g2.fill(new Rectangle2D.Double(tx[j], ty[j], xlen, ylen));
		}
		
		//Draw Obstacles
		Obstacles level1 = new Obstacles(xlen, ylen);
		float[] level1XPos = new float[50];
		float[] level1YPos = new float[50];
	    level1XPos = level1.DrawObstacles(1, "x", xlen, ylen, xsize, ysize);
	    level1YPos = level1.DrawObstacles(1, "y", xlen, ylen, xsize, ysize);
	    
	    for(int i = 0; i < level1XPos.length; i++)
	    {
	    	if((level1XPos[i]==0)&&(level1YPos[i]==0))
	    	{
	    	//prevents any unfilled arrays drawing rectangle at (0.0)	
	    	} 
	    	else
	    	{
	    	g2.setPaint(Color.gray);
	    	g2.fill(new Rectangle2D.Double(level1XPos[i], level1YPos[i], xlen, ylen));
	    	g2.setColor(Color.black);
	    	g2.draw(new Rectangle2D.Double(level1XPos[i], level1YPos[i], xlen, ylen));
	    	}
	    }

		if (shootActivate1 == true) { //drawing bullets for the first player
			g2.setPaint(Color.red);
			for (Bullet b : player1.bullets){
				if (b.isShot) { //draws a bullet
					g2.fill(new Rectangle2D.Double(b.getX(), b.getY(), xlen/2, ylen/2));
				}
			}
		}
		if (shootActivate2 == true) { //drawing bullets for the second player
			g2.setPaint(Color.red);
			for (Bullet b : player2.bullets){
				if (b.isShot) { //draws a bullet
					g2.fill(new Rectangle2D.Double(b.getX(), b.getY(), xlen/2, ylen/2));
				}
			}
		}

		// draw bodies
		for(int i = 1; i<player1.getSize(); i++) {
			g2.setPaint(Color.blue);
			g2.fill(new Rectangle2D.Double((int)player1.getArrX()[i], (int)player1.getArrY()[i], xlen, ylen));
			g2.setColor(Color.black);
			g2.draw(new Rectangle2D.Double((int)player1.getArrX()[i], (int)player1.getArrY()[i], xlen, ylen));
		}
		for(int i = 1; i<player2.getSize(); i++) {
			g2.setPaint(Color.red);
			g2.fill(new Rectangle2D.Double((int)player2.getArrX()[i], (int)player2.getArrY()[i], xlen, ylen));
			g2.setColor(Color.black);
			g2.draw(new Rectangle2D.Double((int)player2.getArrX()[i], (int)player2.getArrY()[i], xlen, ylen));
		}

		// draw heads
		g2.drawImage(headB, (int)player1.getX(), (int)player1.getY(), null);
		g2.drawImage(headR, (int)player2.getX(), (int)player2.getY(), null);

		g2.dispose();
	}

	// empty methods that are required by the GUI event loop
	public void componentHidden (ComponentEvent e) { }
	public void componentMoved (ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { }
	public void componentShown (ComponentEvent e) { }

	public void makeBlackHole() {
		if (foodCount%3==0){
			tx[(foodCount)/3] = (xlen)*Math.round(Math.random()*((xsize/xlen)-1));
			ty[(foodCount)/3] = (xlen)*Math.round(Math.random()*((xsize/xlen)-1));
			blackHoleNumber += 1;
		}
	}

	public void checkCollision(){

		for(int i = 0; i<player2.getSize(); i++) {
			if(player1.getArrX()[0]==player2.getArrX()[i] && player1.getArrY()[0]==player2.getArrY()[i]) {
				JOptionPane.showMessageDialog (null, "Blue Snake loses!", "GAME OVER", JOptionPane.ERROR_MESSAGE);
				mainWindow.dispose();
				alive=false;
				break;
			}
		}
		if(alive) {
			for(int i = 0; i<player1.getSize(); i++) {
				if(player2.getArrX()[0]==player1.getArrX()[i] && player2.getArrY()[0]==player1.getArrY()[i]) {
					JOptionPane.showMessageDialog (null, "Red Snake loses!", "GAME OVER", JOptionPane.ERROR_MESSAGE);
					mainWindow.dispose();
					alive=false;
					break;
				}
			}
		}
	}

	public void run() {
		while (alive) { // loop while both snakes are alive
			//update positions
			player1.setX(player1.getX()+dx);
			player1.setY(player1.getY()+dy);
			player1.first.setPos(player1.getX()-dx, player1.getY()-dy);
			player1.setPos(player1.first);

			player2.setX(player2.getX()+dx2);
			player2.setY(player2.getY()+dy2);
			player2.first.setPos(player2.getX()-dx2, player2.getY()-dy2);
			player2.setPos(player2.first);

			// check to see if the snakes have hit any walls
			player1.checkWalls(xlen, xsize, ylen, ysize);
			player2.checkWalls(xlen, xsize, ylen, ysize);

			// checks food and generate new if needed
			if (player1.checkFood(randomNumber1, randomNumber2, xsize, ysize, xlen, ylen)){
				randomNumber1 = (xlen)*Math.round(Math.random()*((xsize/xlen)-1));
				randomNumber2 = (ylen)*Math.round(Math.random()*((ysize/ylen)-1));
				makeBlackHole();
				foodCount++;
				player1.incSize();
			}

			if (player2.checkFood(randomNumber1, randomNumber2, xsize, ysize, xlen, ylen)){
				randomNumber1 = (xlen)*Math.round(Math.random()*((xsize/xlen)-1));
				randomNumber2 = (ylen)*Math.round(Math.random()*((ysize/ylen)-1));
				makeBlackHole();
				foodCount++;
				player2.incSize();
			}

			// checks whether the snakes bites themselves or not
			if (player1.checkSnake(alive)==false){
				JOptionPane.showMessageDialog (null, "You are worthless and weak!", "GAME OVER", JOptionPane.ERROR_MESSAGE);
				mainWindow.dispose();
				alive = false;
			}
			else {
				if (player2.checkSnake(alive)==false){
					JOptionPane.showMessageDialog (null, "You are worthless and weak!", "GAME OVER", JOptionPane.ERROR_MESSAGE);
					mainWindow.dispose();
					alive = false;
				}
			}
			
			//checks whether snake crashes into obstacles
			Obstacles level1 = new Obstacles(xlen, ylen);
			float[] level1XPos = new float[10];
		    level1XPos = level1.DrawObstacles(1, "x", xlen, ylen, xsize, ysize);
		    float[] level1YPos = new float[10];
		    level1YPos = level1.DrawObstacles(1, "y", xlen, ylen, xsize, ysize);
			if (player1.checkObstacles(level1XPos, level1YPos, xsize, ysize, xlen, ylen)==true){
				mainWindow.dispose();
				JOptionPane.showMessageDialog (null, "CCCRRRAAAAASSSHHHH!", "GAME OVER", JOptionPane.ERROR_MESSAGE);
				alive=false;
			}
			if (player2.checkObstacles(level1XPos, level1YPos, xsize, ysize, xlen, ylen)==true){
				mainWindow.dispose();
				JOptionPane.showMessageDialog (null, "CCCRRRAAAAASSSHHHH!", "GAME OVER", JOptionPane.ERROR_MESSAGE);
				alive=false;
			}

			// checks whether the snakes are in the black holes
			player1.checkBlackHole(blackHoleNumber, tx, ty, xval, yval, xlen, ylen, xsize, ysize);
			player2.checkBlackHole(blackHoleNumber, tx, ty, xval, yval, xlen, ylen, xsize, ysize);

			checkCollision();
			
			//shoot
			for (Bullet b: player1.bullets) b.move();
			for (Bullet b: player2.bullets) b.move();

			// sleep a bit until the next frame
			try { Thread.sleep(delay); }
			catch (InterruptedException e) {
				System.out.println("error");
				break;
			}

			// make snakes available to move
			readyToMove1 = true;
			readyToMove2 = true;

			// refresh the display
			repaint();
		}
	}

	public void keyPressed(KeyEvent e) {
		//control first snake with space and arrow keys
		if (e.getKeyCode()==32) { //space
			shootActivate1 = true;
			ammunitionsNumber1--;
			for (Bullet b : player1.bullets){
				if (!b.isShot){
					b.speedBx = 2*dx;
					b.speedBy = 2*dy;
					if (dx>0) {
						b.setX(player1.getArrX()[0] + xlen);
						b.setY(player1.getArrY()[0] + ylen/4);
					}
					else if ( dx !=0 ) {
						b.setX(player1.getArrX()[0] - xlen/2);
						b.setY(player1.getArrY()[0] + ylen/4);
					}
					if(dy<0) {
						b.setX(player1.getArrX()[0] + xlen/4);
						b.setY(player1.getArrY()[0] + ylen/2);

					}
					else if ( dy != 0 ){
						b.setX(player1.getArrX()[0] + xlen/4);
						b.setY(player1.getArrY()[0] + ylen);

					}
					b.isShot = true;
					break;
				}
			} //space ends
		}
		if(dx!=0) {
			if(e.getKeyCode()==38 && readyToMove1) {
				dy = -1*Math.abs(dx);
				dx = 0;
				readyToMove1 = false;
			}
			if(e.getKeyCode()==40 && readyToMove1) {
				dy = Math.abs(dx);
				dx = 0;
				readyToMove1 = false;
			}
		}
		else {
			if(e.getKeyCode()==37 && readyToMove1) {
				dx = -1*Math.abs(dy);
				dy = 0;
				readyToMove1 = false;
			}
			if(e.getKeyCode()==39 && readyToMove1) {
				dx = Math.abs(dy);
				dy = 0;
				readyToMove1 = false;
			}
		}

		//control second snake with G and A,S,D,W keys
		if (e.getKeyCode()==71) // G
		{ //shooting
			shootActivate2 = true;
			ammunitionsNumber2--;
			for (Bullet b : player2.bullets){
				if (!b.isShot){
					b.speedBx = 2*dx2;
					b.speedBy = 2*dy2;
					if (dx2>0) {
						b.setX(player2.getArrX()[0] + xlen);
						b.setY(player2.getArrY()[0] + ylen/4);
					}
					else if ( dx2 !=0 ) {
						b.setX(player2.getArrX()[0] - xlen/2);
						b.setY(player2.getArrY()[0] + ylen/4);
					}
					if(dy2<0) {
						b.setX(player2.getArrX()[0] + xlen/4);
						b.setY(player2.getArrY()[0] + ylen/2);

					}
					else if ( dy2 != 0 ){
						b.setX(player2.getArrX()[0] + xlen/4);
						b.setY(player2.getArrY()[0] + ylen);

					}
					b.isShot = true;
					break;
				}
			} //shooting ends
		}
		if(dx2!=0) {
			if(e.getKeyCode()==87 && readyToMove2) {
				dy2 = -1*Math.abs(dx2);
				dx2 = 0;
				readyToMove2 = false;
			}
			if(e.getKeyCode()==83 && readyToMove2) {
				dy2 = Math.abs(dx2);
				dx2 = 0;
				readyToMove2 = false;
			}
		}
		else {
			if(e.getKeyCode()==65 && readyToMove2) {
				dx2 = -1*Math.abs(dy2);
				dy2 = 0;
				readyToMove2 = false;
			}
			if(e.getKeyCode()==68 && readyToMove2) {
				dx2 = Math.abs(dy2);
				dy2 = 0;
				readyToMove2 = false;
			}
		}
	}

	// empty methods that are required by the KeyListener
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
}

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
	int snakeSize = 8;
	int snakeSize2= 8;
	int blackHoleNumber = 0;
	int foodCount = 0;
	float xlen,ylen; // size of snake
	float dx,dy, dx2, dy2; // current speed + direction of snake
	boolean start = true;
	float xp0, yp0, yp20, xp20;

	boolean shootActivate = false; // checks if "Space" is hit
	int ammunitionsNumber; //number of ammunitions
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
			background = ImageIO.read(new File("bg4.png"));
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

		player1 = new SnakeHead(snakeSize, xp0, yp0);
		player1.setPos(player1.first, xp0, yp0, dx, dy);
		
		player2 = new SnakeHead(snakeSize2, xp20, yp20);
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

		//draw static bullet (or call it a pistol:D )I'll create a function "draw" of the class Bullet soon
		if (shootActivate == true) {
			g2.setPaint(Color.red);
			if (shootActivate == true) { //creates a bullet
				g2.fill(new Rectangle2D.Double(xb, yb, xlen/2, ylen/2));
			}		
		}

		// draw bodies
		for(int i = 1; i<snakeSize; i++) {
			g2.setPaint(Color.blue);
			g2.fill(new Rectangle2D.Double((int)player1.getArrX()[i], (int)player1.getArrY()[i], xlen, ylen));
			g2.setColor(Color.black);
			g2.draw(new Rectangle2D.Double((int)player1.getArrX()[i], (int)player1.getArrY()[i], xlen, ylen));
		}
		for(int i = 1; i<snakeSize2; i++) {
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
		
		for(int i = 0; i<snakeSize2; i++) {
			if(player1.getArrX()[0]==player2.getArrX()[i] && player1.getArrY()[0]==player2.getArrY()[i]) {
				JOptionPane.showMessageDialog (null, "Red Snake loses!", "GAME OVER", JOptionPane.ERROR_MESSAGE);
				mainWindow.dispose();
				alive=false;
			    break;
			    }
		}
		if(alive) {
			for(int i = 0; i<snakeSize; i++) {
				if(player2.getArrX()[0]==player1.getArrX()[i] && player2.getArrY()[0]==player1.getArrY()[i]) {
					JOptionPane.showMessageDialog (null, "Blue Snake loses!", "GAME OVER", JOptionPane.ERROR_MESSAGE);
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
				snakeSize++;
				foodCount++;
				player1.incSize();
			}

			if (player2.checkFood(randomNumber1, randomNumber2, xsize, ysize, xlen, ylen)){
				randomNumber1 = (xlen)*Math.round(Math.random()*((xsize/xlen)-1));
				randomNumber2 = (ylen)*Math.round(Math.random()*((ysize/ylen)-1));
				makeBlackHole();
				snakeSize2++;
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

			// checks whether the snakes are in the black holes
			player1.checkBlackHole(blackHoleNumber, tx, ty, xval, yval, xlen, ylen, xsize, ysize);
			player2.checkBlackHole(blackHoleNumber, tx, ty, xval, yval, xlen, ylen, xsize, ysize);

			checkCollision();
			//shoot();
			xb += speedBx;
			yb += speedBy;

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
		if (e.getKeyCode()==32) { //space
			shootActivate = true;
			ammunitionsNumber--;
			speedBx = 2*dx;
			speedBy = 2*dy;
			if (dx>0) {
				xb = xp[0]+xlen;
				yb = yp[0]+ylen/4;
			} 
			else if ( dx !=0 ) {
				xb = xp[0]-xlen/2;
				yb = yp[0]+ylen/4;
			} 
			if(dy<0) {
				xb = xp[0]+xlen/4;
				yb = yp[0]+ylen/2;
			}
			else if ( dy != 0 ){ 						
				xb = xp[0]+xlen/4;
				yb = yp[0] + ylen;
			}			
		} //space ends
		
		//control first snake with arrow keys
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
		
		//control second snake with a,s,d,w keys
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

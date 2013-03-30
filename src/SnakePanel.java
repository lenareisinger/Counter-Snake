import java.awt.*;

import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class SnakePanel extends JComponent 
implements ComponentListener, KeyListener, Runnable {

	final static long serialVersionUID = 0;  // kill warning
	final static BasicStroke stroke = new BasicStroke(2.0f); // constants

	boolean alive;
	JFrame mainWindow;
	float xsize,ysize; // size of window

	boolean readyToMove1 = true;
	boolean readyToMove2 = true;

	float[] tx = new float[100];	// x position of blackhole
	float[] ty = new float[100];	// y position of blackhole
	
	Level level = new Level(1, 150, 5);
	
	boolean snake1Alive=true;
	boolean snake2Alive=true;

	SnakeHead player1;
	SnakeHead player2;
	int initialSize = 8;
	int blackHoleNumber = 0;
	int foodCount = 0;
	int levelLength=15; //amount of food the snakes have to eat to reach the next level
	float xlen,ylen; // size of snake
	float dx,dy, dx2, dy2; // current speed + direction of snake
	boolean start = true;
	float xp0, yp0, yp20, xp20;
	
	int score1; //score of blue snake
	int score2; //score of red snake
	
	Obstacles obstacle = new Obstacles(level.levelNumber);
	float[] XPos = new float[120];
	float[] YPos = new float[120];
	float[] randomNumbers = new float[2];
	

	boolean shootActivate1 = false; // checks if trigger key 1 is hit
	boolean shootActivate2 = false; // check if trigger key 2 is hit
	int ammunitionsNumber1, ammunitionsNumber2; //number of ammunitions
	int bulletNum1, bulletNum2;
	float speedBx, speedBy;

	float xb,yb; //coordinates of bullet;

	float randomNumber1, randomNumber2, random3, random4, xval, yval, xdif, ydif;
	BufferedImage background, headB, headR;

	long delay; // delay between frames in milliseconds
	Thread animThread; // animation thread

	// SnakePanel constructor
	public SnakePanel(JFrame mw) {
		//
		mainWindow = mw;
		alive = true;

		//loads background
		try {
			background = ImageIO.read(new File("bg1.png"));
			headB = ImageIO.read(new File("headBlue.png"));
			headR = ImageIO.read(new File("headRed.png"));
		} catch (IOException e) {
		}

		// set values for all the variables
		xsize = 600;
		ysize = 600;
		xlen = 20;
		ylen = 20;
		level.createLevel(1);
		/*dx = 0;
		dy = 20;
		xp0=200;
		yp0=300;
		xp20=400;
		yp20=300;

		dx2 = 0;
		dy2 = -20;*/
		
		//gets start position and dx/dy of the first level
		xp0=obstacle.getStartPosX(level.levelNumber);
		yp0=obstacle.getStartPosY(level.levelNumber);
		xp20=obstacle.getStartPosX2(level.levelNumber);
		yp20=obstacle.getStartPosY2(level.levelNumber);
		dx=obstacle.getDx(level.levelNumber);
		dy=obstacle.getDy(level.levelNumber);
		dx2=obstacle.getDx2(level.levelNumber);
		dy2=obstacle.getDy2(level.levelNumber);

		//speed of the snake
		delay =level.speed;

		//check random numbers are not producing food on obstacles
		randomNumber1 = (xlen)*Math.round(Math.random()*((xsize/xlen)-1));
		randomNumber2 = (ylen)*Math.round(Math.random()*((ysize/ylen)-1));
		randomNumbers = FoodChecker.foodCheck(XPos, YPos, randomNumber1, randomNumber2, xsize, xlen, ysize, ylen);
		randomNumber1 = randomNumbers[0];
		randomNumber2 = randomNumbers[1];
		
		
		player1 = new SnakeHead(initialSize, xp0, yp0);
		player1.setPos(player1.first, xp0, yp0, dx, dy);

		player2 = new SnakeHead(initialSize, xp20, yp20);
		player2.setPos(player2.first, xp20, yp20, dx2, dy2);

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
		for (int j=1;j<=blackHoleNumber;j++){
			g2.setPaint(Color.black);
			g2.fill(new Rectangle2D.Double(tx[j], ty[j], xlen, ylen));
		}
		
		//Draw Obstacles
		
		Obstacles obstacle = new Obstacles(level.levelNumber);
		float[] XPos = new float[120];
		float[] YPos = new float[120];
		
	    XPos = obstacle.DrawObstacles("x");
	    YPos = obstacle.DrawObstacles("y");
	    		
	    for(int i = 0; i < XPos.length; i++)
	    {
	    	if((XPos[i]==-1)&&(YPos[i]==-1))
	    	{
	    	//prevents any unfilled arrays drawing rectangle at (0,0)	
	    	} 
	    	else
	    	{
	    	g2.setPaint(Color.gray);
	    	g2.fill(new Rectangle2D.Double(XPos[i], YPos[i], xlen, ylen));
	    	g2.setColor(Color.black);
	    	g2.draw(new Rectangle2D.Double(XPos[i], YPos[i], xlen, ylen));
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
		if (foodCount%level.blackHoles==0){
			tx[(foodCount)/level.blackHoles] = (xlen)*Math.round(Math.random()*((xsize/xlen)-1));
			ty[(foodCount)/level.blackHoles] = (xlen)*Math.round(Math.random()*((xsize/xlen)-1));
			blackHoleNumber += 1;
		}
	}

	public void checkCollision(){
		if(player1.getArrX()[0]==player2.getArrX()[0] && player1.getArrY()[0]==player2.getArrY()[0]) {
			snake1Alive=false;
			snake2Alive=false;
		}
		else{
			for(int i = 0; i<player2.getSize(); i++) {
				if(player1.getArrX()[0]==player2.getArrX()[i] && player1.getArrY()[0]==player2.getArrY()[i]) {
					player2.setSize(i);
				}
			}
			if(alive) {
				for(int i = 0; i<player1.getSize(); i++) {
					if(player2.getArrX()[0]==player1.getArrX()[i] && player2.getArrY()[0]==player1.getArrY()[i]) {
						player1.setSize(i);
					}
				}
			}
		}
	}
	public void checkBullet(){ //checks if a bullet has shot a snake
		//--------player1-------------
		for (Bullet b: player1.bullets){
			for(int i = 0; i<player2.getSize(); i++) {
				if(((b.getX()==player2.getArrX()[i] || b.getX()==player2.getArrX()[i] + xlen) 
						&& b.getY()>=player2.getArrY()[i] && b.getY()<=player2.getArrY()[i] + ylen)
						||((b.getX() + xlen/2 ==player2.getArrX()[i] || b.getX() + xlen/2 == player2.getArrX()[i] + xlen) 
								&& b.getY()>=player2.getArrY()[i] && b.getY()<=player2.getArrY()[i] + ylen)
								//--
								||((b.getY()==player2.getArrY()[i] || b.getY()==player2.getArrY()[i] + ylen) 
										&& b.getX()>=player2.getArrX()[i] && b.getX()<=player2.getArrX()[i] + xlen)
										||((b.getY() + ylen/2 ==player2.getArrY()[i] || b.getY() + ylen/2 == player2.getArrY()[i] + ylen) 
												&& b.getX()>=player2.getArrX()[i] && b.getX()<=player2.getArrX()[i] + xlen)) {
					player2.setSize(i);
					b.setX(-100);
					b.setY(-100);
				} 
			}
			if(alive) {
				for(int i = 0; i<player1.getSize(); i++) {
					if(b.getX()==player1.getArrX()[i] && b.getY()==player1.getArrY()[i]) {
						player1.setSize(i);
					}
				}
			}
		}
		//----------player2-----------------------
		for (Bullet b: player2.bullets){
			for(int i = 0; i<player1.getSize(); i++) {
				if(((b.getX()==player1.getArrX()[i] || b.getX()==player1.getArrX()[i] + xlen) 
						&& b.getY()>=player1.getArrY()[i] && b.getY()<=player1.getArrY()[i] + ylen)
						||((b.getX() + xlen/2 ==player1.getArrX()[i] || b.getX() + xlen/2 == player1.getArrX()[i] + xlen) 
								&& b.getY()>=player1.getArrY()[i] && b.getY()<=player1.getArrY()[i] + ylen)
								//--
								||((b.getY()==player1.getArrY()[i] || b.getY()==player1.getArrY()[i] + ylen) 
										&& b.getX()>=player1.getArrX()[i] && b.getX()<=player1.getArrX()[i] + xlen)
										||((b.getY() + ylen/2 ==player1.getArrY()[i] || b.getY() + ylen/1 == player1.getArrY()[i] + ylen) 
												&& b.getX()>=player1.getArrX()[i] && b.getX()<=player1.getArrX()[i] + xlen) ) {
					player1.setSize(i);
					b.setX(-100);
					b.setY(-100);
				} 
			}
			//---
			if(alive) {
				for(int i = 0; i<player2.getSize(); i++) {
					if(b.getX()==player2.getArrX()[i] && b.getY()==player2.getArrY()[i]) {
						player2.setSize(i);
					}
				}
			}
		}
	}
	
	public void checkLevel(){
		if (foodCount==levelLength){
			
			//calculates the score of each snake
			score1=score1+player1.getSize()-2;
			score2=score2+player2.getSize()-2;
			
			snake1Alive=true;
			snake2Alive=true;
			
			//checks if the last level is reached
			if (level.levelNumber==5 && score1>score2){
				JOptionPane.showMessageDialog (null, "The blue snake wins! \n Blue Snake: "+score1+" \n Red Snake: "+score2 , "Game over", 1);
				mainWindow.dispose();
				alive = false;
				Menu.main(null);
			}
			else if (level.levelNumber==5 && score1<score2){
				JOptionPane.showMessageDialog (null, "The red snake wins! \n Blue Snake: "+score1+" \n Red Snake: "+score2 , "Game over", 1);
				mainWindow.dispose();
				alive = false;
				Menu.main(null);
			}
			else if (level.levelNumber==5 && score1==score2){
				JOptionPane.showMessageDialog (null, "Both snakes win! \n Blue Snake: "+score1+" \n Red Snake: "+score2 , "Game over", 1);
				mainWindow.dispose();
				alive = false;
				Menu.main(null);
			}
			
		
			else {
			JOptionPane.showMessageDialog (null, "You made it to the next level! \n Blue Snake: "+ score1 +" points and Red Snake: "+score2+" points" , "Level "+(level.levelNumber+1), 1);
			
			//goes to the next level
			level.moveLevelUp();
			
			//changes speed of snake
			delay=level.speed;
			
			foodCount=0;
			
			//changes the background image
			try {
				background = ImageIO.read(new File("bg"+level.levelNumber+".png"));
			} catch (IOException e) {
			}
			//deletes all black holes
			for(int i=0; i<blackHoleNumber; i++ ){
				tx[i]=0;
				ty[i]=0;
			}
			blackHoleNumber=0;
			
			//gets start positions for the next level
			xp0=obstacle.getStartPosX(level.levelNumber);
			yp0=obstacle.getStartPosY(level.levelNumber);
			xp20=obstacle.getStartPosX2(level.levelNumber);
			yp20=obstacle.getStartPosY2(level.levelNumber);
			dx=obstacle.getDx(level.levelNumber);
			dy=obstacle.getDy(level.levelNumber);
			dx2=obstacle.getDx2(level.levelNumber);
			dy2=obstacle.getDy2(level.levelNumber);
			
			player1 = new SnakeHead(initialSize, xp0, yp0);
			player1.setPos(player1.first, xp0, yp0, dx, dy);

			player2 = new SnakeHead(initialSize, xp20, yp20);
			player2.setPos(player2.first, xp20, yp20, dx2, dy2);
			
			}
		}
	}

	public void run() {
		while (alive) { // loop while both snakes are alive
			//update positions
			if (snake1Alive) {
				player1.setX(player1.getX()+dx);
				player1.setY(player1.getY()+dy);
				player1.first.setPos(player1.getX()-dx, player1.getY()-dy);
				player1.setPos(player1.first);
			}

			if (snake2Alive) {
				player2.setX(player2.getX()+dx2);
				player2.setY(player2.getY()+dy2);
				player2.first.setPos(player2.getX()-dx2, player2.getY()-dy2);
				player2.setPos(player2.first);
			}

			// check to see if the snakes have hit any walls
			player1.checkWalls(xlen, xsize, ylen, ysize);
			player2.checkWalls(xlen, xsize, ylen, ysize);

			// checks food and generate new if needed
	
			if (player1.checkFood(randomNumber1, randomNumber2, xsize, ysize, xlen, ylen)){
				randomNumbers = FoodChecker.foodCheck(XPos, YPos, randomNumber1, randomNumber2, xsize, xlen, ysize, ylen);
				randomNumber1 = randomNumbers[0];
				randomNumber2 = randomNumbers[1];
				foodCount++;
				makeBlackHole();
				player1.incSize();
			}

			if (player2.checkFood(randomNumber1, randomNumber2, xsize, ysize, xlen, ylen)){
				randomNumbers = FoodChecker.foodCheck(XPos, YPos, randomNumber1, randomNumber2, xsize, xlen, ysize, ylen);
				randomNumber1 = randomNumbers[0];
				randomNumber2 = randomNumbers[1];
				foodCount++;
				makeBlackHole();
				player2.incSize();
			}

			// checks whether the snakes bites themselves or not
			if (player1.checkSnake(alive)==false) {
				dx=0;
				dy=0;
				snake1Alive=false;

			}

			if (player2.checkSnake(alive)==false) {
				dx2=0;
				dy2=0;
				snake2Alive=false;
			}

			//checks whether snake crashes into obstacles
			Obstacles obstacle = new Obstacles(level.levelNumber);

			XPos = obstacle.DrawObstacles("x");
		    YPos = obstacle.DrawObstacles("y");
		    
			if (player1.checkObstacles(XPos, YPos, xlen, ylen, xsize, ysize )==true){
				dx=0;
				dy=0;
				snake1Alive=false;
			}
			if (player2.checkObstacles(XPos, YPos, xlen, ylen, xsize, ysize)==true){
				dx2=0;
				dy2=0;
				snake2Alive=false;
			}

			// checks whether the snakes are in the black holes
			player1.checkBlackHole(blackHoleNumber, tx, ty, xval, yval, xlen, ylen, xsize, ysize);
			player2.checkBlackHole(blackHoleNumber, tx, ty, xval, yval, xlen, ylen, xsize, ysize);

			checkCollision();
			checkBullet();
			
			//makes sure that a new level starts if both snakes died
			if (snake1Alive==false && snake2Alive==false){
				foodCount=levelLength;
			}
			
			checkLevel();
			
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

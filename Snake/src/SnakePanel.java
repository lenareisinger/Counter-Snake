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
	
    // constants
    final static BasicStroke stroke = new BasicStroke(2.0f);
      
    // fields
    float xsize,ysize;  // size of window
    float[] xp = new float[100];	// x position of snake
    float[] yp = new float[100];    // y position of snake
    int snakeSize = 4;
    float xlen,ylen;    // size of ball
    float dx,dy;        // current speed + direction of ball 
    boolean start = true;
    double randomNumber1, randomNumber2;
    BufferedImage background;
    
    int    delay;       // delay between frames in milliseconds
    Thread animThread;  // animation thread                       
                                       
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
        
        for(int i = 0; i<snakeSize; i++) {
        	if(i==0) {
        		// draw head
        		g2.setPaint(Color.red);
                g2.fill(new Rectangle2D.Double(xp[i], yp[i], xlen, ylen));
                g2.setColor(Color.black);
                g2.draw(new Rectangle2D.Double(xp[i], yp[i], xlen, ylen));
        	}
        	else {
        		// draw body
                g2.setPaint(Color.blue);
                g2.fill(new Rectangle2D.Double(xp[i], yp[i], xlen, ylen));
                g2.setColor(Color.black);
                g2.draw(new Rectangle2D.Double(xp[i], yp[i], xlen, ylen));
        	}	
        }
        

        g2.dispose();
    }
    
    // empty methods that are required by the GUI event loop
    public void componentHidden (ComponentEvent e)  { }
    public void componentMoved  (ComponentEvent e)  { }
    public void componentResized(ComponentEvent e)  { }
    public void componentShown  (ComponentEvent e)  { }
    
    public void checkWalls() {    
       if (xp[0] + xlen >/*=*/ xsize) { 
           /*xp[0] = xsize - xlen;
           dx   = 0;
           dy	= -20; */
    	   xp[0] = 0; //enables the snake to appear on the opposite place on the screen[Angel]   
       } 
       if (xp[0] < 0) {
           /*xp[0] = 0;
           dx   = 0;
           dy	= 20; */
    	   xp[0] += xsize; //enables the snake to appear on the opposite place on the screen[Angel] 
       } 
       if (yp[0] + ylen >/*=*/ ysize) {
          /* yp[0] = ysize - ylen; 
           dx   = 20;
           dy 	= 0; */
    	   yp[0]=0; //enables the snake to appear on the opposite place on the screen[Angel]
    	   
       }             
       if (yp[0] < 0) {
           /*yp[0] = 0;
           dx   = -20;
           dy	= 0; */
    	   yp[0] += ysize;
       }
    }
    
    public void checkFood() {
    	if(xp[0]==randomNumber1 && yp[0]==randomNumber2) {
            randomNumber1 = (xlen)*Math.round(Math.random()*((xsize/xlen)-1));
            randomNumber2 = (ylen)*Math.round(Math.random()*((ysize/ylen)-1));
    		snakeSize++;
    	}
    }
    
    public void run() {
      while (true) {  // loop forever          
        // update position
        for(int i = snakeSize; i>0; i--) {
        	xp[i] = xp[i-1];
        	yp[i] = yp[i-1];
        }
    	xp[0] += dx;
        yp[0] += dy;
                
        // check to see if the snake has hit any walls
        /* not finished!!!! */
        checkWalls();
       
        
        checkFood();
        //hi
        
        // sleep a bit until the next frame
        try { Thread.sleep(delay); } 
        catch (InterruptedException e) { 
        	System.out.println("error");
        	break;
        }
        
        // refresh the display
        repaint();  
      }
    }
        
    public SnakePanel() {
        //loads background
    	try {
            background = ImageIO.read(new File("bg2.png"));
        } catch (IOException e) {
        }
    	
    	// set values for all the variables
        xsize = 600;
        ysize = 600;
        xp[0]  = 300;
        yp[0]  = 300;       
        xlen  = 20;
        ylen  = 20;
        dx    = 0;
        dy    = 20;
        delay = 100;
        randomNumber1 = (xlen)*Math.round(Math.random()*((xsize/xlen)-1));
        randomNumber2 = (ylen)*Math.round(Math.random()*((ysize/ylen)-1));

        for(int i = 1; i<snakeSize; i++) {
        	xp[i] = xp[0]-(i*xlen*sign(dx));
        	yp[i] = yp[0]-(i*ylen*sign(dy));
        }
        
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
    
    private static int sign(float a) {
    	if(a>0) {
    		return 1;
    	}
    	else {
    		if(a==0) {
    			return 0;
    		}
    		else {
    			return -1;
    		}
    	}
    }

	public void keyPressed(KeyEvent e) {
		if(dx!=0) {
			if(e.getKeyCode()==38) {
				dy = -1*Math.abs(dx);
				dx = 0;
			}
			if(e.getKeyCode()==40) {
				dy = Math.abs(dx);
				dx = 0;
			}
		}
		else {
			if(e.getKeyCode()==37) {
				dx = -1*Math.abs(dy);
				dy = 0;
			}
			if(e.getKeyCode()==39) {
				dx = Math.abs(dy);
				dy = 0;
			}
		}
	}
	
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
}

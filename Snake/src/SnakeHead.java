import java.util.ArrayList;

public class SnakeHead {
	SnakeBody first;
	private int snakeSize;
	private float xp,yp;
	ArrayList<Bullet> bullets = new ArrayList<Bullet>(); //creates an Array List of bullets

	//constructor
	public SnakeHead(int size, float xp, float yp) {
		this.xp = xp;
		this.yp = yp;
		first = new SnakeBody(size);
		snakeSize = size;
		for (int i=0; i<5; i++) {
			bullets.add(new Bullet(0, 0, 0, 0));
		}
	}

	// Sets position of the snake... used only at the start of the game
	public void setPos(SnakeBody temp, float x, float y, float dx, float dy) {
		temp.setPos(x, y);
		if(temp.next != null) {
			setPos(temp.next, x-dx, y-dy, dx ,dy);
		}
	}

	// Update position of the snake
	public void setPos(SnakeBody temp) {
		if(temp.next != null) {;
		setPos(temp.next);
		temp.next.setPos(temp.getX(), temp.getY());
		}
	}

	//Returns array of X-coordinates of the snake
	public float[] getArrX() {
		float[] s = new float[snakeSize+1];
		s[0] = xp;
		int i = 1;
		SnakeBody temp = first;
		while(temp.next != null) {
			s[i]=temp.getX();
			temp=temp.next;
			i++;
		}
		return s;
	}

	//Returns array of Y-coordinates of the snake
	public float[] getArrY() {
		float[] s = new float[snakeSize+1];
		s[0] = yp;
		int i = 1;
		SnakeBody temp = first;
		while(temp.next != null) {
			s[i]=temp.getY();
			temp=temp.next;
			i++;
		}
		return s;
	}

	// Returns X-coordinate of head
	public float getX() {
		return xp;
	}

	// Returns Y-coordinate of head
	public float getY() {
		return yp;
	}

	// Sets X-coordinate of head to a given value
	public void setX(float x) {
		xp = x;
	}

	// Sets Y-coordinate of head to a given value
	public void setY(float y) {
		yp = y;
	}

	// Increase size of the snake by 1
	public void incSize() {
		snakeSize++;
		SnakeBody temp = first;
		while(temp.next != null) {
			temp = temp.next;
		}
		temp.next = new SnakeBody(0);
	}

	// Returns actual size of the snake
	public int getSize() {
		return snakeSize;
	}

	// check to see if the snake has hit any walls
	public void checkWalls(float xlen, float xsize, float ylen, float ysize) {
		if (getX() + xlen > xsize) {
			setX(0); 				//enables the snake to appear on the opposite place on the screen[Angel]
		}
		if (getX() < 0) {
			setX(getX() + xsize); 	//enables the snake to appear on the opposite place on the screen[Angel]
		}
		if (getY() + ylen > ysize) {
			setY(0); 				//enables the snake to appear on the opposite place on the screen[Angel]
		}
		if (getY() < 0) {
			setY(getY() + ysize);	//enables the snake to appear on the opposite place on the screen
		}		
	}

	// checks whether the snake bite himself or not
	public boolean checkSnake(boolean alive) {
		for(int i = 1; i<=snakeSize; i++) {
			if(getArrX()[0]==getArrX()[i] && getArrY()[0]==getArrY()[i]) { 
				alive = false;
			}
		}
		return alive;		
	}

	// Checks if snake's head is on black hole
	public void checkBlackHole(int blackHoleNumber, float[] tx, float[] ty, float xval, float yval, float xlen, float ylen, float xsize, float ysize) {
		for(int i = 0; i<blackHoleNumber; i++) {
			if(tx[i]==getArrX()[0] && ty[i]==getArrY()[0]) {
				xval= getArrX()[0];
				yval= getArrY()[0];
				float random3= (xlen)*Math.round(Math.random()*((xsize/xlen)-1));
				float random4 = (ylen)*Math.round(Math.random()*((ysize/ylen)-1));
				setX(random3);
				setY(random4);
			}
		}		
	}

	// Checks if snake's head is on food
	public boolean checkFood(float randomNumber1, float randomNumber2, float xsize, float ysize, float xlen, float ylen) {
		boolean eat=false;

		if(getArrX()[0]==randomNumber1 && getArrY()[0]==randomNumber2) {
			eat = true;
		}
		return eat;		
	}
}

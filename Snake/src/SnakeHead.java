import javax.swing.JOptionPane;

public class SnakeHead {
	SnakeBody first;
	private int snakeSize;
	private float xp,yp;
	
	public SnakeHead(int size, float xp, float yp) {
		this.xp = xp;
		this.yp = yp;
		first = new SnakeBody(size);
		snakeSize = size;
	}
	
	public void setPos(SnakeBody temp, float x, float y, float dx, float dy) {
		temp.setPos(x, y);
		if(temp.next != null) {
			setPos(temp.next, x-dx, y-dy, dx ,dy);
		}
	}
	
	public void setPos(SnakeBody temp) {
		if(temp.next != null) {;
			setPos(temp.next);
			temp.next.setPos(temp.getX(), temp.getY());
		}
	}
	
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
	
	public float getX() {
		return xp;
	}
	
	public float getY() {
		return yp;
	}
	
	public void setX(float x) {
		xp = x;
	}
	
	public void setY(float y) {
		yp = y;
	}
	
	public void incSize() {
		snakeSize++;
		SnakeBody temp = first;
		while(temp.next != null) {
			temp = temp.next;
		}
		temp.next = new SnakeBody(0);
	}
	
	public int getSize() {
		return snakeSize;
	}

	public void checkWalls(float xlen, float xsize, float ylen, float ysize) {
		if (getX() + xlen > xsize) {
			setX(0); //enables the snake to appear on the opposite place on the screen[Angel]
		}
		if (getX() < 0) {
			setX(getX() + xsize); //enables the snake to appear on the opposite place on the screen[Angel]
		}
		if (getY() + ylen > ysize) {
			setY(0); //enables the snake to appear on the opposite place on the screen[Angel]

		}
		if (getY() < 0) {
			setY(getY() + ysize); //enables the snake to appear on the opposite place on the screen
		}		
	}

	public boolean checkSnake(boolean alive) {
		for(int i = 1; i<=snakeSize; i++) {
			if(getArrX()[0]==getArrX()[i] && getArrY()[0]==getArrY()[i]) {
				 
			 alive = false; // panel appears only once
			}
		}
		return alive;		
	}

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

	public boolean checkFood(float randomNumber1, float randomNumber2, float xsize, float ysize, float xlen, float ylen) {
		boolean eat=false;
		
		if(getArrX()[0]==randomNumber1 && getArrY()[0]==randomNumber2) {
			eat = true;
			
			
		}
		return eat;		
	}
}

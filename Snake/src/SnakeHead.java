public class SnakeHead {
	SnakeBody first;
	private int snakeSize;
	private float xp,yp;
	
	public SnakeHead(int size) {
		xp = 300;
		yp = 300;
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
}

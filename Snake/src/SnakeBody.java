public class SnakeBody {
	SnakeBody next;
	int n;
	private float xp,yp;
	
	public SnakeBody(int size) {
		n=size;
		xp = 200;
		yp = 200;
		if(size>0) {
			this.next=new SnakeBody(n-1);
		}
		else {
			this.next=null;
		}
	}
	
	public void setPos(float x, float y) {
		xp = x;
		yp = y;
	}
	
	public float getX() {
		return xp;
	}
	
	public float getY() {
		return yp;
	}
	
	public void incSnake() {
		
	}
}

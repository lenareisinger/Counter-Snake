public class SnakeBody {
	SnakeBody next;
	private float xp,yp;
	
	// constructor
	public SnakeBody(int size) {
		xp = 200;
		yp = 200;
		if(size>0) {
			this.next=new SnakeBody(size-1);
		}
		else {
			this.next=null;
		}
	}
	
	// Sets position of the body to given values
	public void setPos(float x, float y) {
		xp = x;
		yp = y;
	}
	
	// Returns actual X-coordinate of the body
	public float getX() {
		return xp;
	}
	
	// Returns actual Y-coordinate of the body
	public float getY() {
		return yp;
	}
}

public class Bullet {
	private float xBullet;
	private float yBullet;
	float speedBx;
	float speedBy;
	boolean shootActivate;
	boolean isShot=false;

	public Bullet(float xBullet, float yBullet, float speedBx, float speedBy){ //constructor starts
		this.xBullet = xBullet;
		this.yBullet = yBullet;
		this.speedBx = speedBx;
		this.speedBy = speedBy;
	} //constructor ends

	public void move (){ //moves the bullet
		xBullet += speedBx;
		yBullet += speedBy;
	}

	public float getX(){
		return xBullet;
	}

	public float getY(){
		return yBullet;
	}

	public void setX(float x){
		xBullet = x;
	}

	public void setY(float y){
		yBullet = y;
	}

	public float getXSpeed(){
		return speedBx;
	}
	public float getYSpeed(){
		return speedBy;
	}
}
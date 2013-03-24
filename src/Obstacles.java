public class Obstacles 
{
	//private int level;

	public Obstacles(float xlen, float ylen)
	{
		//this.level = level;
	}
		
	public float[] DrawObstacles(int level, String plane, float xlen, float ylen, float xsize, float ysize)
	{
		float[] positions = new float[50];
		if (level==1){
			
		}
		if ((level == 2)&&(plane == "x"))
		{
			for(int i = 0; i < 15; i++)
			{
				positions[i] = 120;
			}
			for(int i = 15; i < 30; i++)
			{
				positions[i] = (600 - 140);
			}
			for(int i =30; i < 45; i++)
			{
				positions[i] = (600/2);
			}

		}
		else if ((level == 2)&&(plane == "y"))
		{
			for(int i = 0; i < 15; i++)
			{
				positions[i] = (20*i);
			}
			for(int i = 15; i < 30; i++)
			{
				positions[i] = (20*(i-15));
			}
			for(int i = 30; i < 45; i++)
			{
				positions[i] = (600 - 20*(i - 30));
			}
		}	
		return positions;
	}
	
	//sets start positions for each level
	public int getStartPosX(int level) {
		int xp0=200;
		if (level==1){
			xp0=400;
		}
		if (level==2){
			xp0=400;
		}
		if (level==3){
			xp0=400;
		}
		if (level==4){
			xp0=400;
		}
		if (level==5){
			xp0=400;
		}
		return xp0;
	}
	public int getStartPosY(int level) {
		int yp0=300;
		if (level==1){
			yp0=300;
		}
		if (level==2){
			yp0=300;
		}
		if (level==3){
			yp0=300;
		}
		if (level==4){
			yp0=300;
		}
		if (level==5){
			yp0=300;
		}
		return yp0;
	}
	public int getStartPosX2(int level) {
		int xp20=200;
		if (level==1){
			xp20=200;
		}
		if (level==2){
			xp20=200;
		}
		if (level==3){
			xp20=200;
		}
		if (level==4){
			xp20=200;
		}
		if (level==5){
			xp20=200;
		}
		return xp20;
	}
	public int getStartPosY2(int level) {
		int yp20=300;
		if (level==1){
			yp20=300;
		}
		if (level==2){
			yp20=300;
		}
		if (level==3){
			yp20=300;
		}
		if (level==4){
			yp20=300;
		}
		if (level==5){
			yp20=300;
		}
		return yp20;
	}
	public int getDx(int level) {
		int dx=0;
		if (level==1){
			dx=0;
		}
		if (level==2){
			dx=0;
		}
		if (level==3){
			dx=0;
		}
		if (level==4){
			dx=0;
		}
		if (level==5){
			dx=0;
		}
		return dx;
	}
	public int getDy(int level) {
		int dy=20;
		if (level==1){
			dy=20;
		}
		if (level==2){
			dy=20;
		}
		if (level==3){
			dy=20;
		}
		if (level==4){
			dy=20;
		}
		if (level==5){
			dy=20;
		}
		return dy;
	}
	public int getDx2(int level) {
		int dx2=0;
		if (level==1){
			dx2=0;
		}
		if (level==2){
			dx2=0;
		}
		if (level==3){
			dx2=0;
		}
		if (level==4){
			dx2=0;
		}
		if (level==5){
			dx2=0;
		}
		return dx2;
	}
	public int getDy2(int level) {
		int dy2=20;
		if (level==1){
			dy2=20;
		}
		if (level==2){
			dy2=-20;
		}
		if (level==3){
			dy2=20;
		}
		if (level==4){
			dy2=20;
		}
		if (level==5){
			dy2=20;
		}
		return dy2;
	}
}

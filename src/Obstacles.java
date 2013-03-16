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
		if ((level == 1)&&(plane == "x"))
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
		else if ((level == 1)&&(plane == "y"))
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
}

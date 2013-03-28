
public class FoodChecker 
{
	float r1;
	float r2;
	
	public FoodChecker(float r1, float r2)
	{
		this.r1 = r1;
		this.r2 = r2;
	}
	
	public static float[] foodCheck(float[] xpos, float[] ypos, float r1, float r2, float xsize, float xlen, float ysize, float ylen )
	{
		r1 = (xlen)*Math.round(Math.random()*((xsize/xlen)-1));
		r2 = (ylen)*Math.round(Math.random()*((ysize/ylen)-1));
		
		for (int i = 0; i < xpos.length; i++)
		{
			if(xpos[i]==r1 && ypos[i]==r2)
			{
				foodCheck(xpos, ypos, r1, r2, xsize, xlen, ysize, ylen);
			}
		}
		
		float[] NotFood = new float[2];
		NotFood[0] = r1;
		NotFood[1] = r2;
		
		return NotFood;
		
	}
}

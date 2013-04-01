
public class FoodChecker 
{
	float r1;
	float r2;
	
	public FoodChecker(float r1, float r2)
	{
		this.r1 = r1;
		this.r2 = r2;
	}
	
	//method with Snake objects generated - used after game begun
	public static float[] foodCheck(float[] bhX, float[] bhY, float[] getArrX1, float[] getArrY1, float[] getArrX2, float[] getArrY2, float[] xpos, float[] ypos, float r1, float r2, float xsize, float xlen, float ysize, float ylen )
	{
		r1 = (xlen)*Math.round(Math.random()*((xsize/xlen)-1));
		r2 = (ylen)*Math.round(Math.random()*((ysize/ylen)-1));
		
		for (int i = 0; i < xpos.length; i++)
		{
			if(xpos[i]==r1 && ypos[i]==r2)
			{
				foodCheck(bhX, bhY, getArrX1, getArrY1, getArrX2, getArrY2, xpos, ypos, r1, r2, xsize, xlen, ysize, ylen);
			}
		}
		
		for(int i = 0; i < getArrX1.length; i++)
		{
			if(getArrX1[i] == r1 && getArrY1[i] == r2)
			{
				foodCheck(bhX, bhY, getArrX1, getArrY1, getArrX2, getArrY2, xpos, ypos, r1, r2, xsize, xlen, ysize, ylen);
			}
		}
		
		for(int i = 0; i < getArrX2.length; i++)
		{
			if(getArrX2[i] == r1 && getArrY2[i] == r2)
			{
				foodCheck(bhX, bhY, getArrX1, getArrY1, getArrX2, getArrY2, xpos, ypos, r1, r2, xsize, xlen, ysize, ylen);
			}
		}
		
		for(int i = 0; i < bhX.length; i++)
		{
			if(bhX[i] == r1 && bhY[i] == r2)
			{
				foodCheck(bhX, bhY, getArrX1, getArrY1, getArrX2, getArrY2, xpos, ypos, r1, r2, xsize, xlen, ysize, ylen);
			}
		}
		
		float[] NotFood = new float[2];
		NotFood[0] = r1;
		NotFood[1] = r2;
		
		return NotFood;	
	}
	
	//method without Snake objects generated - used for initial setup
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

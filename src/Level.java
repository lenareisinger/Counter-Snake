
public class Level {
	Obstacles obstacle;
	int speed;
	int levelNumber;
	int blackHoles;
	
	
	public Level(int levelNumber, int speed, int blackHoles){
		this.levelNumber = levelNumber;
		this.speed=speed;
//		this.obstacle=obstacle;
	}
	
	public void createLevel(int levelNumber) {
		
		if (levelNumber==1){
			speed=150;
			blackHoles=5;
			
		}
		if (levelNumber==2){
			speed =130;
			blackHoles=4;
		}
		if (levelNumber==3){
			speed =110;
			blackHoles=3;
			
		}
		if (levelNumber==4){
			speed =95;
			blackHoles=2;
		}
		if (levelNumber==5){
			speed =90;
			blackHoles=1;
		}
				
	}
	


	public void moveLevelUp(){
		levelNumber++;
		createLevel(levelNumber);
	}

	

}

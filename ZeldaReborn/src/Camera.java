public class Camera {
	
	private int level;
	private float x, y;
	
	public Camera(float x, float y, int level) {
		this.x = x;
		this.y = y;
		this.level=level;
	}
	
	public void tick(GameObject obj) {
		//x = obj.getX() + 1000/2 -puts camera onto the middle
		//1024 and 768 are dimensions
		x = x + (obj.getX() - x - 1024/2) * 0.05f;
		y = y + (obj.getY() - y - 768/2) * 0.05f;
		
		//activate camera for every level except level 6 - multiplayer
		if(level!=6) {
			if(x <= 0 ) x=0;
			if(x >= 1024 +7) x=1024 + 7;
			
			if(y <= 0 ) y=0;
			if(y >= 225) y=225;	
		}
		else {
			//multiplayer level
			x = 0 ;
			y = 0 ;
		}
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
}
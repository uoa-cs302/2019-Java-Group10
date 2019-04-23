
public class Camera {
	
	private int level;
	private float x, y;
	
	public Camera(float x, float y, int level) {
		this.x = x;
		this.y = y;
		this.level=level;
	}
	
	public void tick(GameObject obj) {
		
		//could also do - puts camera onto the middle
		//x = obj.getX() + 1000/2;
		
		x = x + (obj.getX() - x - 1024/2) * 0.05f;
		y = y + (obj.getY() - y - 768/2) * 0.05f;
		
		//activate camera for every level except level 6 - multiplayer
		if(level!=6) {
			if(x <= 0 ) x=0;
			//if(x >= 1024+32 ) x=1024+30;
			if(x >= 1024 +15 ) x=1024 + 15;
			if(y <= 0 ) y=0;
			//if(y >= 768+16 ) y=768;
			if(y >= 230) y=230;	
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

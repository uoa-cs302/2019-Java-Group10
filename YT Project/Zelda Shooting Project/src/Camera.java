
public class Camera {
	
	private float x, y;
	
	public Camera(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void tick(GameObject obj) {
		
		//could also do - puts camera onto the middle
		//x = obj.getX() + 1000/2;
		
		x = x + (obj.getX() - x - 1024/2) * 0.05f;
		y = y + (obj.getY() - y - 768/2) * 0.05f;
		
		if(x <= 0 ) x=0;
		if(x >= 1024+32 ) x=1024+30;
		if(y <= 0 ) y=0;
		if(y >= 768+16 ) y=768;
		
		
		
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

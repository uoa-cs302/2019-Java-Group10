import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;


//handles all our objects
public class Handler {
	private boolean up = false, down = false, left = false, right = false;
	LinkedList<GameObject> obj = new LinkedList<GameObject>();
	
	public void tick() {
		for (int i = 0 ; i < obj.size(); i++) {
			GameObject temp = obj.get(i);  //since all objects extend gameObject, we can use that here.
			temp.tick();
		}
	}
	
	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public void render(Graphics g) {
		for (int i = 0 ; i < obj.size(); i++) {
			GameObject temp = obj.get(i);
			temp.render(g);
		}
	}
	
	public void clearLevel() {
		obj.clear();
	}
	
	public void addObj(GameObject temp) {
		obj.add(temp);
	}
	
	public void removeObj(GameObject temp) {
		obj.remove(temp);
	}	
}

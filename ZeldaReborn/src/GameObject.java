import java.awt.Graphics;
import java.awt.Rectangle;

//STRUCTURE OF OUR OBJECTS DEFINED HERE. DON'T CONFUSE WITH GAME() CLASS.
//the skeleton (bones) of our objects in the game
public abstract class GameObject {

	protected int x, y;
	protected int velX = 0, velY = 0;
	protected ID id;
	protected SpriteSheet ss;
	
	public GameObject(int x, int y, ID id, SpriteSheet ss) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.ss = ss;
	}
	
	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getVelX() {
		return velX;
	}

	public void setVelX(int velX) {
		this.velX = velX;
	}

	public int getVelY() {
		return velY;
	}

	public void setVelY(int velY) {
		this.velY = velY;
	}

	public abstract void tick();			
	public abstract void render(Graphics g);
	public abstract Rectangle getBounds();

}
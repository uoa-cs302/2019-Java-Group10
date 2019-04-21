import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

//Bullet class
public class Arrow extends GameObject{

	private Handler handler;
	
	public Arrow(int x, int y, ID id, Handler handler, int mouseX, int mouseY, SpriteSheet ss) {
		super(x, y, id, ss);
		this.handler = handler;
		
		//10 acts as a speed divider here. Basically time to reach location
		velY = (mouseY - y) / 10;
		velX = (mouseX - x) / 10;
	}

	@Override
	public void tick() {
		x = x + velX;
		y= y + velY;
		
		//collision detection of bullet
		for(int i=0; i < handler.obj.size(); i++) {
			GameObject temp = handler.obj.get(i);
			
			if(temp.getId() == ID.Block) {
				if(getBounds().intersects(temp.getBounds())) {
					//this is the instance of our bullet
					handler.removeObj(this);
				}
			}
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.green);
		g.fillOval(x, y, 8, 8);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, 8, 8);
	}

}

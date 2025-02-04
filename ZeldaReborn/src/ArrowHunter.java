import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

//Arrow class for Boss level
public class ArrowHunter extends GameObject{

	private Handler handler;
	
	public ArrowHunter(int x, int y, ID id, Handler handler, int zeldaX, int zeldaY, SpriteSheet ss) {
		super(x, y, id, ss);
		this.handler = handler;
		
		//111 acts as a speed divider here. Basically time to reach location.
		velY = (zeldaY - y) / 11;
		velX = (zeldaX - x) / 11;
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

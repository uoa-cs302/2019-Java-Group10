import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Crate extends GameObject {
	
	private BufferedImage crate_image;

	public Crate(int x, int y, ID id, SpriteSheet ss, int level) {
		super(x, y, id, ss);
		
		//based on the ID of powerup/powerdown set, grab the according image from the sprite sheet to render
		if (id == ID.Crate_healthPlus) {
			crate_image = ss.grabBigImage(1, 1, 60, 60);
		}
		else if (id == ID.Crate_healthMinus) {
			crate_image = ss.grabBigImage(2, 1, 60, 60);
		}
		else if (id == ID.Crate_speedPlus) {
			crate_image = ss.grabBigImage(1, 2, 60, 60);
		}
		else if (id == ID.Crate_speedMinus) {
			crate_image = ss.grabBigImage(2, 2, 60, 60);
		}
		else if (id == ID.Crate_ammoPlus) {
			crate_image = ss.grabBigImage(1, 3, 60, 60);
		}
		
		if (id == ID.Crate_fruits) {
			if (level == 1) {
				crate_image = ss.grabImage(4, 4, 32, 32);	
			}
			
			if (level == 2){
				crate_image = ss.grabImage(4, 5, 32, 32);
			}
			else if (level == 3){
				crate_image = ss.grabImage(5, 5, 32, 32);
			}
			else if (level == 4){
				crate_image = ss.grabImage(6, 5, 32, 32);
			}
			else if (level == 5){
				crate_image = ss.grabImage(3, 5, 32, 32);
			}
		}
	}

	@Override
	public void tick() {

	}

	@Override
	public void render(Graphics g) {
		//draw the image of the crate selected
		g.drawImage(crate_image, x, y, null);
	}

	@Override
	public Rectangle getBounds() {
		//set a rectangle bounds around the powerup/ powerdown to use for collision detection
		return new Rectangle(x+12,y+12,32,32);
		
	}

}
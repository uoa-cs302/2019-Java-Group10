//import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

//Class representing walls 
public class Block extends GameObject {
	
	private BufferedImage block_image;
	

	public Block(int x, int y, ID id, SpriteSheet ss) {
		super(x, y, id, ss);
		block_image = ss.grabImage(2, 4, 32, 32);
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(block_image, x, y, null);		
	}

	@Override
	public Rectangle getBounds() {
		//32,32 are dimensions of our block
		return new Rectangle(x, y, 32, 32);
	}

}
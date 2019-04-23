import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class DirtTile extends GameObject {
	
	private BufferedImage dirt_image;

	public DirtTile(int x, int y, ID id, SpriteSheet ss) {
		super(x, y, id, ss);
		dirt_image = ss.grabImage(2, 1, 32, 32);
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(dirt_image, x, y, null);
	}

	@Override
	public Rectangle getBounds() {
		return null;
	}

	
}

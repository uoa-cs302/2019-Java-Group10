import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

//wizard
public class Zelda extends GameObject{
	
	Game game;  //being used to allow for ammo variable to be used here
	Handler handler;
	
	Animations anim;
	
	private BufferedImage[] zelda_image = new BufferedImage[2];

	public Zelda(int x, int y, ID id, Handler handler, Game game, SpriteSheet ss) {
		super(x, y, id, ss);
		this.handler = handler;
		this.game=game;
		
		zelda_image[0] = ss.grabImage(5, 1, 32, 32);
		zelda_image[1] = ss.grabImage(6, 1, 32, 32);
		
		//3 is the speed at which animation starts with
		anim = new Animations(3, zelda_image);
	}

	@Override
	public void tick() {
		x = x + velX;
		y = y + velY;
		
		collision();
		
		//for each key input -- vel for each direction
		if (handler.isUp()) {
			velY = -5;
		}
		//improving lag
		else if (!handler.isDown()) {
			velY = 0;
		}
		
		if (handler.isDown()) {
			velY = 5;
		}
		else if (!handler.isUp()) {
			velY = 0;
		}
		
		if (handler.isRight()) {
			velX = 5;
		}
		else if (!handler.isLeft()) {
			velX = 0;
		}
		
		if (handler.isLeft()) {
			velX = -5;
		}
		else if (!handler.isRight()) {
			velX = 0;
		}
		
		anim.runAnimation();
	}
	
	private void collision() {
		for(int i=0; i< handler.obj.size(); i++) {
			GameObject temp = handler.obj.get(i);
			
			if(temp.getId() == ID.Block) {
				//intersects is an inbuilt function
				if(getBounds().intersects(temp.getBounds())) {
					x =x+velX * -1;
					y =y+velY * -1;
				}
			}
			
			//change for power-ups/ powerdowns
			if(temp.getId() == ID.Crate) {
				//intersects is an inbuilt function
				if(getBounds().intersects(temp.getBounds())) {
					game.ammo = game.ammo + 10;
					handler.removeObj(temp);
				}
			}
			
			//collision with spider enemy
			if(temp.getId() == ID.EnemySpider) {
				if(getBounds().intersects(temp.getBounds())) {
					game.hp--;
				}
			}
			
			//collision with enemy
			if(temp.getId() == ID.Enemy) {
				if(getBounds().intersects(temp.getBounds())) {
					game.hp--;
				}
			}
			
			
			if(temp.getId() == ID.EnemyBoss) {
				if(getBounds().intersects(temp.getBounds())) {
					game.hp--;
				}
			}
			
			
		}
	}

	@Override
	public void render(Graphics g) {
		//if character is not moving, draw still frame of animation
		if (velX == 0 && velY == 0) {
			g.drawImage(zelda_image[0], x, y, null);
		}
		else {
			anim.drawAnimation(g, x, y, 0);
		}
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, 32, 48);
	}

	
}

//import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

public class EnemySpider extends GameObject{
		
	Random r = new Random();
	private Handler handler;
	private Game game;
	
	private BufferedImage[] enemy_spider = new BufferedImage[4];
	Animations anim;
	
	int choose = 0;
	int hp = 100;   //health
	boolean collidedBlock = false, collisionZelda = false;
	
	public EnemySpider(int x, int y, ID id, Handler handler, SpriteSheet ss, Game game) {
		super(x, y, id, ss);
		this.handler = handler;
		this.game = game;
		enemy_spider[0] = ss.grabBigImage(1, 1, 64, 64);
		enemy_spider[1] = ss.grabBigImage(2, 1, 64, 64);
		enemy_spider[2] = ss.grabBigImage(1, 2, 64, 64);
		enemy_spider[3] = ss.grabBigImage(1, 1, 64, 64);
		
		anim = new Animations(1, enemy_spider);
	}

	@Override
	public void tick() {
		x = x+velX;
		
		for(int i = 0; i < handler.obj.size(); i++) {
			GameObject temp = handler.obj.get(i);
			
			if(temp.getId() == ID.Block) {
				if(getBoundsOuter().intersects(temp.getBounds())) {
					//basically shoots back in opposite direction at twice the speed
					x += (velX*5) * -1;
					y +=  (velY*5) * -1;
					velX *=  -1;
					velY *= -1;
					
					collidedBlock = !collidedBlock;
				}
				//not colliding
				else {
					if(collidedBlock ==true) {
						velX = 1;
					}
					else {
						velX= -1;
					}
				}
			}
			
			if(temp.getId() == ID.Arrow) {
				if(getBounds().intersects(temp.getBounds())) {
					//if health=100, then it takes 2 shots for enemy to die
					hp = hp-50;
					handler.removeObj(temp);
				}
			}
			
			//collision with zelda
			if(temp.getId() == ID.Player) {
				if(getBounds().intersects(temp.getBounds())) {
					collisionZelda = true;
					//if collision with zelda, change array image to spider with red eyes
					enemy_spider[3] = ss.grabBigImage(2, 2, 64, 64);
				}
				else {
					collisionZelda = false;
				}
			}
		}
		
		anim.runAnimation();
		
		if(hp <= 0) {
			handler.removeObj(this);
			int a = game.gethighscore();
			a=a+20;
			game.sethighscore(a);
			//game.highscore = game.highscore + 20;
		}
	}

	@Override
	public void render(Graphics g) {
		//if spider collides with zelda, spiders image with red eyes displays
		if (collisionZelda) {
			g.drawImage(enemy_spider[3], x, y, null);
		}
		else {
			anim.drawAnimation(g, x, y, 0);
		}
	}
	
	@Override
	//collision box for spiders
	public Rectangle getBounds() {
		return new Rectangle(x+10, y+10, 40, 45);
	}
	
	//making bounding box a little bigger. To allow for spaces b/w blocks
	public Rectangle getBoundsOuter() {
		return new Rectangle(x-16 , y-16, 64, 64);
	}

}

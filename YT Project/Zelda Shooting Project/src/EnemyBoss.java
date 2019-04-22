import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

public class EnemyBoss extends GameObject{

	
	//ONE ENEMY KEEPS LEAVING FROM THE BOTTOM LEFT OFF MAP. Only one, the rest are working fine.
		
	Random r = new Random();
	private Handler handler;
	
	private BufferedImage[] enemy_image = new BufferedImage[2];
	private Game game;
	Animations anim;
	
	int zelda_x, zelda_y;
	int choose = 0;
	int hp;   //health
	double dist, deltaX, deltaY, direction, speed;
	
	public EnemyBoss(int x, int y, ID id, Handler handler, SpriteSheet ss, Game game) {
		super(x, y, id, ss);
		this.handler = handler;
		this.game = game;
		enemy_image[0] = ss.grabImage(5, 3, 32, 32);
		enemy_image[1] = ss.grabImage(6, 3, 32, 32);
		
		hp = (game.difficulty * 100) +50;
		
		anim = new Animations(3, enemy_image);
	}

	@Override
	public void tick() {
		x = x+velX;
		y = y+velY;
		
		//basically makes choose variable a random number between 0 and 9.
		//choose = r.nextInt(10);
		
		for(int i = 0; i < handler.obj.size(); i++) {
			GameObject temp = handler.obj.get(i);
			
			if(temp.getId() == ID.Block) {
				if(getBoundsOuter().intersects(temp.getBounds())) {
					//basically shoots back in opposite direction at twice the speed
					//feel free to modify
					x += (velX*5) * -1;
					y +=  (velY*5) * -1;
					velX *=  -1;
					velY *= -1;
				}
				//not colliding
				else  {
					dist= Math.hypot(x-zelda_x, y-zelda_y);
					if(dist < 400) {
						deltaX = zelda_x - x;
						deltaY = zelda_y - y;
						direction = Math.atan2(deltaY,deltaX);
						speed = game.difficulty +2;
						
						velX=  (int) (speed*Math.cos(direction));
						velY= (int) (speed*Math.sin(direction));
					}
					else {
						velX= 0;
						velY= 0;
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
			
			if(temp.getId() == ID.Player) {
				zelda_x = temp.getX();
				zelda_y = temp.getY();
			}
		}
		
		anim.runAnimation();
		
		if(hp <= 0) {
			handler.removeObj(this);
		}
	}

	@Override
	public void render(Graphics g) {
		anim.drawAnimation(g, x, y, 0);
	}

	
	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, 32, 32);
	}
	
	//MAKE bounding box bigger to avoid edge collisions
	//getBoundsBig
	//making bounding box a little bigger. To allow for spaces b/w blocks
	public Rectangle getBoundsOuter() {
		return new Rectangle(x-16 , y-16, 64, 64);
	}

}

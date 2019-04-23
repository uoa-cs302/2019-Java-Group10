import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

public class EnemyMultiplayer extends GameObject{

	
	//ONE ENEMY KEEPS LEAVING FROM THE BOTTOM LEFT OFF MAP. Only one, the rest are working fine.
		
	Random r = new Random();
	private Handler handler;
	
	private BufferedImage[] enemy_image = new BufferedImage[2];
	Animations anim;
	
	int choose = 0;
	int hp = 1000;   //health
	
	public EnemyMultiplayer(int x, int y, ID id, Handler handler, SpriteSheet ss) {
		super(x, y, id, ss);
		this.handler = handler;
		enemy_image[0] = ss.grabImage(5, 3, 32, 32);
		enemy_image[1] = ss.grabImage(6, 3, 32, 32);
		
		anim = new Animations(3, enemy_image);
	}

	@Override
	public void tick() {
		x = x+velX;
		y = y+velY;
		
		collision();
		
		if (handler.isUpM()) {
			velY = -8;
		}
		//improving lag
		else if (!handler.isDownM()) {
			velY = 0;
		}
		
		if (handler.isDownM()) {
			velY = 8;
		}
		else if (!handler.isUpM()) {
			velY = 0;
		}
		
		if (handler.isRightM()) {
			velX = 8;
		}
		else if (!handler.isLeftM()) {
			velX = 0;
		}
		
		if (handler.isLeftM()) {
			velX = -8;
		}
		else if (!handler.isRightM()) {
			velX = 0;
		}
		
		/*
		
		//basically makes choose variable a random number between 0 and 9.
		choose = r.nextInt(10);
		
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
				else if(choose == 0) {
					//velocity range of -3 to 3
					velX = (r.nextInt(6) + -3);
					velY = (r.nextInt(6) + -3);
				}
			}
			
			*/
			
			
		//}
		
		anim.runAnimation();
		
		if(hp <= 0) {
			handler.removeObj(this);
		}
	}
	
	//collision detection for multiplayer
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
			
			if(temp.getId() == ID.Arrow) {
				if(getBounds().intersects(temp.getBounds())) {
					//if health=100, then it takes 2 shots for enemy to die
					hp = hp-50;
					handler.removeObj(temp);
				}
				
			}
			
			//change for power-ups/ powerdowns
			
			//NOT CHECKING FOR COLLISION WITH OTHER ENTITIES YET.
			//HEALTH FOR MULTIPLAYER NEEDS TO BE SET UP BETTER
			/*
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
			
			if(temp.getId() == ID.EnemyMultiplayer) {
				if(getBounds().intersects(temp.getBounds())) {
					game.hp--;
				}
			}
			*/
			
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


import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Enemy extends GameObject{

	
	//ONE ENEMY KEEPS LEAVING FROM THE BOTTOM LEFT OFF MAP. Only one, the rest are working fine.
		
	Random r = new Random();
	private Handler handler;
	private Game game;
	
	private BufferedImage[] enemy_imageFront = new BufferedImage[3];
	private BufferedImage[] enemy_imageRight = new BufferedImage[3];
	private BufferedImage[] enemy_imageLeft = new BufferedImage[3];
	private BufferedImage[] enemy_imageBack = new BufferedImage[3];
	
	Animations animFront, animRight, animLeft, animBack, anim;
	
	int choose = 0;
	int hp = 100;   //health
	
	public Enemy(int x, int y, ID id, Handler handler, SpriteSheet ss, Game game) {
		super(x, y, id, ss);
		this.handler = handler;
		this.game = game;
		
		//load each angle of the enemy animal into its corresponding angle array
		for (int i = 0; i < 3; i ++) {
			enemy_imageFront[i] = ss.grabMediumImage(i+1, 1, 50, 50);
			enemy_imageLeft[i] = ss.grabMediumImage(i+1, 2, 50, 50);
			enemy_imageRight[i] = ss.grabMediumImage(i+1, 3, 50, 50);
			enemy_imageBack[i] = ss.grabMediumImage(i+1, 4, 50, 50);
		}
		
		animFront = new Animations(3, enemy_imageFront);
		animRight = new Animations(3, enemy_imageRight);
		animLeft = new Animations(3, enemy_imageLeft);
		animBack = new Animations(3, enemy_imageBack);
	}

	@Override
	public void tick() {
		x = x+velX;
		y = y+velY;
		
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
					
					if (velX < 0) {
						animLeft.runAnimation();
					}
					else {
						animRight.runAnimation();
					}
					
					velY = (r.nextInt(6) + -3);
				
					if (velY < 0) {
						animBack.runAnimation();
					}
					else {
						animFront.runAnimation();
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
		}
		
		if(hp <= 0) {
			handler.removeObj(this);
			game.highscore = game.highscore + 10;
		}
	}

	@Override
	public void render(Graphics g) {
		if (velX == 0 && velY == 0) {
			g.drawImage(enemy_imageFront[0], x, y, null);
		}
		//else draw the animation of the direction the character is moving in
		else if (velX > 0) {
			animRight.drawAnimation(g, x, y, 0);
		}
		else if (velX < 0) {
			animLeft.drawAnimation(g, x, y, 0);
		}
		else if (velY > 0) {
			animFront.drawAnimation(g, x, y, 0);
		}
		else if (velY < 0) {
			animBack.drawAnimation(g, x, y, 0);
		}
		//in the case no if condition is met, draw front animation
		else {
			animFront.drawAnimation(g, x, y, 0);
		}
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

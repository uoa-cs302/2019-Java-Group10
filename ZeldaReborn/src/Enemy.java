//import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;


public class Enemy extends GameObject{
		
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
		
		//makes choose variable a random number between 0 and 29.
		choose = r.nextInt(30);
		
		for(int i = 0; i < handler.obj.size(); i++) {
			GameObject temp = handler.obj.get(i);
			
			if(temp.getId() == ID.Block) {
				if(getBoundsOuter().intersects(temp.getBounds())) {
					//basically shoots back in opposite direction 
					x += (velX*5) * -1;
					y +=  (velY*5) * -1;
					velX *=  -1;
					velY *= -1;
				}
				//not colliding
				else if(choose == 0) {
					//velocity range of -2 to 2
					velX = (r.nextInt(4) + -2);
					
					if (velX < 0) {
						animLeft.runAnimation();
					}
					else {
						animRight.runAnimation();
					}
					
					velY = (r.nextInt(4) + -2);
				
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
			int a = game.gethighscore();
			a=a+100;
			game.sethighscore(a);
			//game.highscore = game.highscore + 100;
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
		return new Rectangle(x, y, 50, 50);
	}
	
	//making bounding box a little bigger. To allow for spaces b/w blocks
	public Rectangle getBoundsOuter() {
		return new Rectangle(x-16 , y-16, 64, 64);
	}

}
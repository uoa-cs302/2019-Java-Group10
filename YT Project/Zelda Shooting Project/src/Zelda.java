import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

//wizard
public class Zelda extends GameObject{
	
	Game game;  //being used to allow for ammo variable to be used here
	Handler handler;
	
	Animations animFront, animBack, animRight, animLeft;
	
	private BufferedImage[] zelda_Leftimage = new BufferedImage[3];
	private BufferedImage[] zelda_Rightimage = new BufferedImage[3];
	private BufferedImage[] zelda_Frontimage = new BufferedImage[3];
	private BufferedImage[] zelda_Backimage = new BufferedImage[3];

	public Zelda(int x, int y, ID id, Handler handler, Game game, SpriteSheet ss) {
		super(x, y, id, ss);
		this.handler = handler;
		this.game=game;
		
		zelda_Leftimage[0] = ss.grabBigImage(1, 1, 60, 60);
		zelda_Leftimage[1] = ss.grabBigImage(2, 1, 60, 60);
		zelda_Leftimage[2] = ss.grabBigImage(3, 1, 60, 60);
		
		zelda_Rightimage[0] = ss.grabBigImage(1, 2, 60, 60);
		zelda_Rightimage[1] = ss.grabBigImage(2, 2, 60, 60);
		zelda_Rightimage[2] = ss.grabBigImage(3, 2, 60, 60);
		
		zelda_Frontimage[0] = ss.grabBigImage(1, 3, 60, 60);
		zelda_Frontimage[1] = ss.grabBigImage(2, 3, 60, 60);
		zelda_Frontimage[2] = ss.grabBigImage(3, 3, 60, 60);
		
		zelda_Backimage[0] = ss.grabBigImage(1, 4, 60, 60);
		zelda_Backimage[1] = ss.grabBigImage(2, 4, 60, 60);
		zelda_Backimage[2] = ss.grabBigImage(3, 4, 60, 60);
		
		//2 is the speed at which animation starts with
		animFront = new Animations(2, zelda_Frontimage);
		animRight = new Animations(2, zelda_Rightimage);
		animLeft = new Animations(2, zelda_Leftimage);
		animBack = new Animations(2, zelda_Backimage);
	}

	@Override
	public void tick() {
		x = x + velX;
		y = y + velY;
		
		collision();		
		
		//for each key input -- vel for each direction
		if (handler.isUp()) {
			velY = -5;
			animBack.runAnimation();
		}
		//improving lag
		else if (!handler.isDown()) {
			velY = 0;
		}
		
		if (handler.isDown()) {
			velY = 5;
			animFront.runAnimation();
		}
		else if (!handler.isUp()) {
			velY = 0;
		}
		
		if (handler.isRight()) {
			velX = 5;
			animRight.runAnimation();
		}
		else if (!handler.isLeft()) {
			velX = 0;
		}
		
		if (handler.isLeft()) {
			velX = -5;
			animLeft.runAnimation();
		}
		else if (!handler.isRight()) {
			velX = 0;
		}
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
			
			if(temp.getId() == ID.EnemyMultiplayer) {
				if(getBounds().intersects(temp.getBounds())) {
					game.hp--;
				}
			}
			
			if(temp.getId() == ID.EnemyHunter) {
				if(getBounds().intersects(temp.getBounds())) {
					game.hp--;
				}
			}
			
			//checking for HunterArrow
			if(temp.getId() == ID.ArrowHunter) {
				if(getBounds().intersects(temp.getBounds())) {
					game.hp = game.hp - 10;
					handler.removeObj(temp);
				}
			}			
		}
	}

	@Override
	public void render(Graphics g) {
		//if character is not moving, draw still frame of animation
		if (velX == 0 && velY == 0) {
			g.drawImage(zelda_Frontimage[0], x, y, null);
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
	//creates a rectangle around the zelda character for collision detection
	public Rectangle getBounds() {
		return new Rectangle(x + 15, y + 5, 30, 45);
	}	
}

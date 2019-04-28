import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

//import javax.swing.JOptionPane;

//Player class
public class Zelda extends GameObject{

	Game game;  //being used to allow for ammo variable to be used here
	Handler handler;

	Animations animFront, animBack, animRight, animLeft;

	//change back to 3, -3
	int speedPositive = 3, speedNegative = -3;
	
	Sound powerup = new Sound();
	Sound powerdown = new Sound();
	Sound sound;
	

	private BufferedImage[] zelda_Leftimage = new BufferedImage[3];
	private BufferedImage[] zelda_Rightimage = new BufferedImage[3];
	private BufferedImage[] zelda_Frontimage = new BufferedImage[3];
	private BufferedImage[] zelda_Backimage = new BufferedImage[3];
	
	//private Sound s1, s2;

	public Zelda(int x, int y, ID id, Handler handler, Game game, SpriteSheet ss, Sound sound) {
		super(x, y, id, ss);
		this.handler = handler;
		this.game=game;
		
		this.sound=sound;

		
		//adding the multiple sides of Zelda within the corresponding array
		for (int i= 0; i < 3; i ++) {
			zelda_Leftimage[i] = ss.grabBigImage(i+1, 1, 60, 60);
			zelda_Rightimage[i] = ss.grabBigImage(i+1, 2, 60, 60);
			zelda_Frontimage[i] = ss.grabBigImage(i+1, 3, 60, 60);
			zelda_Backimage[i] = ss.grabBigImage(i+1, 4, 60, 60);
		}

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

		//for each key input, modify velocity for each direction
		if (handler.isUp()) {
			velY = speedNegative;
			animBack.runAnimation();
		}
		//improving lag
		else if (!handler.isDown()) {
			velY = 0;
		}

		if (handler.isDown()) {
			velY = speedPositive;
			animFront.runAnimation();
		}
		else if (!handler.isUp()) {
			velY = 0;
		}

		if (handler.isRight()) {
			velX = speedPositive;
			animRight.runAnimation();
		}
		else if (!handler.isLeft()) {
			velX = 0;
		}

		if (handler.isLeft()) {
			velX = speedNegative;
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

			//  POWERUP/ POWERDOWN COLLISION
			if(temp.getId() == ID.Crate_healthPlus) {
				//intersects is an inbuilt function
				if(getBounds().intersects(temp.getBounds())) {
					powerup.playCollisionSound("/powerup.wav");

					//extra life powerup
					int a=game.gethp();
					if (a < 75) {
						int b = a+25;
						game.sethp(b);
					}
					else {
						game.sethp(100);
					}
					handler.removeObj(temp);
				}
			}
			
			if(temp.getId() == ID.Crate_healthMinus) {
				//intersects is an inbuilt function
				if(getBounds().intersects(temp.getBounds())) {
					powerdown.playCollisionSound("/powerdown.wav");

					//lose life - powerdown
					int a = game.gethp();
					int b= a-25;
					game.sethp(b);
					handler.removeObj(temp);
				}
			}
			
			if(temp.getId() == ID.Crate_speedPlus) {
				//intersects is an inbuilt function
				if(getBounds().intersects(temp.getBounds())) {
					powerup.playCollisionSound("/powerup.wav");
					//speed booster
					speedPositive = 6;
					speedNegative = -6;
					handler.removeObj(temp);
				}
			}
			
			if(temp.getId() == ID.Crate_speedMinus) {
				//intersects is an inbuilt function
				if(getBounds().intersects(temp.getBounds())) {
					powerdown.playCollisionSound("/powerdown.wav");
					//speed decreaser
					speedPositive = 1;
					speedNegative = -1;
					handler.removeObj(temp);
				}
			}
			
			if(temp.getId() == ID.Crate_ammoPlus) {
				//intersects is an inbuilt function
				if(getBounds().intersects(temp.getBounds())) {
					powerup.playCollisionSound("/powerup.wav");
					int a = game.getammo();
					int b=a+25;
					game.setammo(b);
					//game.ammo = game.ammo + 25;
					handler.removeObj(temp);
				}
			}
			
			if(temp.getId() == ID.Crate_fruits) {
				//intersects is an inbuilt function
				if(getBounds().intersects(temp.getBounds())) {
					//if last level is completed
					int a =game.getlevelCounter();
					if (a == 5) {
						game.highscorePrint();
						//disposing current game frame 
						game.frame.dispose();
						sound.stopSound();
						new EndGame(game, 1);
						game.stop();						
					}
					else if (a == 3){
						game.setpauseGame(true);
						//game.pauseGame= true;
						new EndGame(game, 3);

						handler.removeObj(temp);
					}
					else if (a == 4) {
						//game.levelSwitch = true;
						game.setlevelSwitch(true);
						//game.levelCounter++;
						int b= a+1;
						game.setlevelCounter(b);
						handler.removeObj(temp);
					}
					else {
						//game.levelCounter++;
						int b=a+1;
						game.setlevelCounter(b);
						//game.levelSwitch = true;
						game.setlevelSwitch(true);
						handler.removeObj(temp);
					}
				}
			}
		
			
			//collision with spider enemy
			if(temp.getId() == ID.EnemySpider) {
				if(getBounds().intersects(temp.getBounds())) {
					//game.hp--;
					int a =game.gethp();
					int b=a-1;
					game.sethp(b);
					
				}
			}

			//collision with enemies
			
			if(temp.getId() == ID.Enemy) {
				if(getBounds().intersects(temp.getBounds())) {
					//game.hp--;
					int a =game.gethp();
					int b=a-1;
					game.sethp(b);
				}
			}

			if(temp.getId() == ID.EnemyBoss) {
				if(getBounds().intersects(temp.getBounds())) {
					//game.hp--;
					int a =game.gethp();
					int b=a-1;
					game.sethp(b);
				}
			}

			if(temp.getId() == ID.EnemyMultiplayer) {
				if(getBounds().intersects(temp.getBounds())) {
					//game.hp--;
					int a =game.gethp();
					int b=a-1;
					game.sethp(b);
				}
			}

			if(temp.getId() == ID.EnemyHunter) {
				if(getBounds().intersects(temp.getBounds())) {
					//game.hp--;
					int a =game.gethp();
					int b=a-1;
					game.sethp(b);
				}
			}

			//checking for Hunter's Arrow
			if(temp.getId() == ID.ArrowHunter) {
				if(getBounds().intersects(temp.getBounds())) {
					handler.removeObj(temp);
					//game.hp = game.hp - 1;
					int a =game.gethp();
					int b=a-1;
					game.sethp(b);
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
	//creates a rectangle around the Zelda character for collision detection
	public Rectangle getBounds() {
		return new Rectangle(x + 15, y + 5, 30, 45);
	}	
}
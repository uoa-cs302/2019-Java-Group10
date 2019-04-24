import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;

//wizard
public class Zelda extends GameObject{

	Game game;  //being used to allow for ammo variable to be used here
	Handler handler;

	Animations animFront, animBack, animRight, animLeft;

	int speedPositive = 5, speedNegative = -5;

	private BufferedImage[] zelda_Leftimage = new BufferedImage[3];
	private BufferedImage[] zelda_Rightimage = new BufferedImage[3];
	private BufferedImage[] zelda_Frontimage = new BufferedImage[3];
	private BufferedImage[] zelda_Backimage = new BufferedImage[3];

	public Zelda(int x, int y, ID id, Handler handler, Game game, SpriteSheet ss) {
		super(x, y, id, ss);
		this.handler = handler;
		this.game=game;

		//add the multiple sides of zelda within the cooresponding array
		for (int i= 0; i < 3; i ++) {
			zelda_Leftimage[i] = ss.grabBigImage(i+1, 1, 60, 60);
			zelda_Rightimage[i] = ss.grabBigImage(i+1, 2, 60, 60);
			zelda_Frontimage[i] = ss.grabBigImage(i+1, 3, 60, 60);
			zelda_Backimage[i] = ss.grabBigImage(i+1, 4, 60, 60);
		}

		//		zelda_Leftimage[0] = ss.grabBigImage(1, 1, 60, 60);
		//		zelda_Leftimage[1] = ss.grabBigImage(2, 1, 60, 60);
		//		zelda_Leftimage[2] = ss.grabBigImage(3, 1, 60, 60);
		//		
		//		zelda_Rightimage[0] = ss.grabBigImage(1, 2, 60, 60);
		//		zelda_Rightimage[1] = ss.grabBigImage(2, 2, 60, 60);
		//		zelda_Rightimage[2] = ss.grabBigImage(3, 2, 60, 60);
		//		
		//		zelda_Frontimage[0] = ss.grabBigImage(1, 3, 60, 60);
		//		zelda_Frontimage[1] = ss.grabBigImage(2, 3, 60, 60);
		//		zelda_Frontimage[2] = ss.grabBigImage(3, 3, 60, 60);
		//		
		//		zelda_Backimage[0] = ss.grabBigImage(1, 4, 60, 60);
		//		zelda_Backimage[1] = ss.grabBigImage(2, 4, 60, 60);
		//		zelda_Backimage[2] = ss.grabBigImage(3, 4, 60, 60);

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
					//extra life powerup
					if (game.hp < 75) {
						game.hp += 25;
					}
					else {
						game.hp = 100;
					}
					handler.removeObj(temp);
				}
			}
			
			if(temp.getId() == ID.Crate_healthMinus) {
				//intersects is an inbuilt function
				if(getBounds().intersects(temp.getBounds())) {
					//lose life - powerdown
					game.hp -= 25;
					handler.removeObj(temp);
				}
			}
			
			if(temp.getId() == ID.Crate_speedPlus) {
				//intersects is an inbuilt function
				if(getBounds().intersects(temp.getBounds())) {
					speedPositive = 10;
					speedNegative = -10;
					handler.removeObj(temp);
				}
			}
			
			if(temp.getId() == ID.Crate_speedMinus) {
				//intersects is an inbuilt function
				if(getBounds().intersects(temp.getBounds())) {
					speedPositive = 2;
					speedNegative = -2;
					handler.removeObj(temp);
				}
			}
			
			if(temp.getId() == ID.Crate_ammoPlus) {
				//intersects is an inbuilt function
				if(getBounds().intersects(temp.getBounds())) {
					game.ammo = game.ammo + 10;
					handler.removeObj(temp);
				}
			}
			
			if(temp.getId() == ID.Crate_fruits) {
				//intersects is an inbuilt function
				if(getBounds().intersects(temp.getBounds())) {
					//if the last level is completed, exit the game and return to the home screen for now
					if (game.levelCounter == 5) {
						//game.highscore = game.highscore + 1000;
						//game.frame.dispose();
						//Home.main(null);
								
						//source-code: https://stackoverflow.com/questions/17979438/how-to-perform-action-on-ok-of-joptionpane-showmessagedialog
//						int input = JOptionPane.showOptionDialog(null, "Congratulation! You collected all the special fruits in time to save Link. You win!", "Game Over", 
//								JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
//
						JOptionPane.showMessageDialog(null, "Congratulation! You collected all the special fruits in time to save Link. You win!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
//						if(input == JOptionPane.OK_OPTION){
//							game.frame.dispose();
//							Home.main(null);
//						}
						game.frame.dispose();
						Home.main(null);
						game.stop();
					}
					game.levelCounter++;
					game.levelSwitch = true;
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
					handler.removeObj(temp);
					game.hp = game.hp - 10;
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

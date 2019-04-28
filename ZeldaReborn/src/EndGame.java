import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class EndGame extends Canvas{

	private static final long serialVersionUID = 1L;
	private Game game;
	private int value;
	private static JFrame frame;
	private Home home;

	Rectangle rectHome, rectYes, rectNo;
	private BufferedImage homeButton = null, yes_no = null;
	
	boolean problem=true;
	Font customFontStars, customFontTeenBold, customFontTeenBoldSized;

	public EndGame(Game game, int value) {
		//initialise input variables
		this.game = game;
		this.value = value;		
		
		//create a new JFrame with set parameters
		frame = new JFrame("Zelda Reborn");
		frame.setSize(1024, 768);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.add(this);

		BufferedImageLoader loader = new BufferedImageLoader();
		homeButton = loader.loadImage("/home.png");
		yes_no = loader.loadImage("yes_no.png");

		rectHome = new Rectangle();
		rectHome.setBounds(437, 552, 128, 44);
		rectYes = new Rectangle();
		rectYes.setBounds(378, 553, 80, 47);
		rectNo = new Rectangle();
		rectNo.setBounds(549, 553, 72, 47);		

		//detect mouse input to button
		//source-code: https://www.youtube.com/watch?v=CajXXmhIndI
		addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				//locate x, y mouse input
				double x = e.getX();
				double y = e.getY();

				//value representing which screen we are on
				if (value == 1 || value == 2 || value ==4) {
					if ((x > rectHome.getX()) && (x < rectHome.getX() + 128) && (y > rectHome.getY()) && (y < rectHome.getY() + 44)) {
						EndGame.frame.dispose();
						Home.main(null);
					}
				}
				else if (value == 3) {
					//yes button
					if ((x > rectYes.getX()) && (x < rectYes.getX() + 80) && (y > rectYes.getY()) && (y < rectYes.getY() + 47)) {
						//game.levelCounter = 4;
						game.setlevelCounter(4);
						//game.levelSwitch = true;
						game.setlevelSwitch(true);
						EndGame.frame.dispose();
						//game.pauseGame =false;
						game.setpauseGame(false);
					}
					//no button
					else if ((x > rectNo.getX()) && (x < rectNo.getX() + 72) && (y > rectNo.getY()) && (y < rectNo.getY() + 47)) {
						//game.levelCounter = 5;
						game.setlevelCounter(5);
						//game.levelSwitch = true;
						game.setlevelSwitch(true);
						EndGame.frame.dispose();
						//game.pauseGame = false;
						game.setpauseGame(false);
					}
				}
			}
		});
	}

	public void paint(Graphics g) {
		//only runs once
		if(problem ==true) {			
			customFontStars= GameFont.getFont("/Stars.ttf", 80);
			customFontTeenBold= GameFont.getFont("/teen_bold.ttf", 20);
			customFontTeenBoldSized= GameFont.getFont("/teen_bold.ttf", 30);
			
			problem=false;
		}
		
		//add background image
		g.drawImage(game.background, 0, 0, 1024, 768, null);
		g.setColor(new Color(211,211,211));

		//checking current state to be executed based on value passed
		if(value ==1) {			
			//when u collect last fruit
			g.setFont(customFontStars);
			g.drawString("YOU WIN", 300, 200);

			g.setFont(customFontTeenBold);
			g.drawString("Congratulations! You collected all the fruits in time to save Link! Press Home to", 100, 300);
			g.drawString("return to the home screen.", 100, 330);

			g.setFont(customFontTeenBoldSized);
			g.drawString("Score: " + game.finalScore, 100, 450);

			g.drawImage(homeButton, 375, 450, 250, 250, null);
		}

		else if (value ==2 ) {
			//this is when u die
			g.setFont(customFontStars);
			g.drawString("GAME OVER", 225, 200);

			g.setFont(customFontTeenBold);
			g.drawString("Oh dear! You failed to collect all the special fruits in time to save Link! Start a new", 100, 300);
			g.drawString("game to try again.", 100, 330);

			g.setFont(customFontTeenBoldSized);
			g.drawString("Score: " + game.finalScore, 100, 450);

			g.drawImage(homeButton, 375, 450, 250, 250, null);
		}
		else if (value ==3) {
			//after boss level, call this
			//spin off level intro.
			g.setFont(customFontStars);
			g.drawString("SPIN OFF", 325, 200);

			g.setFont(customFontTeenBold);
			g.drawString("You have figured out there is a deadly hunter ahead in the jungle. You discovered", 75, 300);
			g.drawString("there is a cave nearby which holds lots of arrows. BEWARE THOUGH!", 75, 330);
			g.drawString("This cave is known to be filled with animals. Enter at your own risk.", 75, 360);
			g.drawString("Do you wish to proceed?", 75, 390);

			//draw the yes/ no button
			g.drawImage(yes_no, 375, 450, 250, 250, null);
		}
		else if (value ==4) {
			//highscores shown here
			g.setFont(customFontStars);
			g.drawString("HIGHSCORES", 250, 200);
			
			g.setFont(customFontTeenBold);
			g.drawString("1. " + game.scoreOne, 250, 250);
			g.drawString("2. " + game.scoreTwo, 250, 280);
			g.drawString("3. " + game.scoreThree, 250, 310);
			g.drawString("4. " + game.scoreFour, 250, 340);
			g.drawString("5. " + game.scoreFive, 250, 370);			
			
			g.drawImage(homeButton, 375, 450, 250, 250, null);
		}		
	}
}
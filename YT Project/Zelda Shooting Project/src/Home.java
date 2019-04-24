//This class creates the main home screen which is launched when the program is run. 
//This Home Class shows the first frame of the game - the home screen which guides the user to play the game, 
//along with other settings and options

//Created By: GameWarriors
//Date Modified: 19/04/19

//DHYANI'S COMMENT

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Home extends Canvas {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Font font;
	public static JFrame homeFrame;
	Rectangle rectStart, rectEnd, rectMultiplayer, rectHighscore, rect1, rect2, rect3;
	private BufferedImage backgroundZelda = null, startGame = null, endGame = null,
			multiplayerGame = null, highscoreGame = null, difficulty = null;
	private BufferedImage[] diff1 = new BufferedImage[2];
	private BufferedImage[] diff2 = new BufferedImage[2];
	private BufferedImage[] diff3 = new BufferedImage[2]; 
	public int difficultyLevel = 1;
	static Home h;
	int paintCounter=1;
	
	//private SpriteSheet ss;
	//private BufferedImage spriteSheet = null;
			
	public Home() {
		
		//load the following images to be used within the home screen
		BufferedImageLoader loader = new BufferedImageLoader();
		backgroundZelda = loader.loadImage("/ZeldaBackground.jpg");
		startGame = loader.loadImage("/play.png");
		multiplayerGame = loader.loadImage("/multiplayer.png");
		highscoreGame = loader.loadImage("/highscore.png");
		endGame = loader.loadImage("/exit.png");
		difficulty = loader.loadImage("/difficulty.png");
		
		//difficulty buttons
		diff1[0] = loader.loadImage("/1_empty.png");
		diff1[1] = loader.loadImage("/1_full.png");
		diff2[0] = loader.loadImage("/2_empty.png");
		diff2[1] = loader.loadImage("/2_full.png");
		diff3[0] = loader.loadImage("/3_empty.png");
		diff3[1] = loader.loadImage("/3_full.png");

		//spriteSheet = loader.loadImage("/sprite_sheet.png");
		//ss = new SpriteSheet(spriteSheet);
		//startGame = ss.grabImage(7, 5, 32, 32);
		
		//button rectangle detection on click
		rectStart = new Rectangle();
		rectStart.setBounds(804, 260, 200, 70);
		rectMultiplayer = new Rectangle();
		rectMultiplayer.setBounds(737, 416, 263, 55);
		rectHighscore = new Rectangle();
		rectHighscore.setBounds(780, 486, 220, 55);
		rectEnd = new Rectangle();
		rectEnd.setBounds(849, 557, 152, 55);
		rect1 = new Rectangle();
		rect1.setBounds(840, 340, 42, 42);
		rect2 = new Rectangle();
		rect2.setBounds(890, 340, 42, 42);
		rect3 = new Rectangle();
		rect3.setBounds(940, 340, 42, 42);
		
		
		//detect mouse input to buttons
		//source-code: https://www.youtube.com/watch?v=CajXXmhIndI
		addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				//locate x, y mouse input
				double x = e.getX();
				double y = e.getY();
				
				//start button input
				if ((x > rectStart.getX()) && (x < rectStart.getX() + 200) && (y > rectStart.getY()) && (y < rectStart.getY() + 70)) {
					homeFrame.dispose();
					new Game(h,1);
				}
				//multiplayer button input
				else if ((x > rectMultiplayer.getX()) && (x < rectMultiplayer.getX() + 263) && (y > rectMultiplayer.getY()) && (y < rectMultiplayer.getY() + 55)) {
					homeFrame.dispose();
					new Game(h,6);
				}
				//highscore button input
				else if ((x > rectHighscore.getX()) && (x < rectHighscore.getX() + 220) && (y > rectHighscore.getY()) && (y < rectHighscore.getY() + 55)) {
					System.out.println("highscore");
					//99 is just a default value to compare against
					//new Game(h, 99);
				}
				//end button input
				else if ((x > rectEnd.getX()) && (x < rectEnd.getX() + 152) && (y > rectEnd.getY()) && (y < rectEnd.getY() + 55)) {
					System.exit(0);
				}
				//difficulty input
				else if ((x > rect1.getX()) && (x < rect1.getX() + 42) && (y > rect1.getY()) && (y < rect1.getY() + 42)) {
					difficultyLevel = 1;
				}
				else if ((x > rect2.getX()) && (x < rect2.getX() + 42) && (y > rect2.getY()) && (y < rect2.getY() + 42)) {
					difficultyLevel = 2;
				}
				else if ((x > rect3.getX()) && (x < rect3.getX() + 42) && (y > rect3.getY()) && (y < rect3.getY() + 42)) {
					difficultyLevel = 3;
				}
				repaint();
			}
		});	
	}

	public static void main(String args []) {
		//create and set conditions and constraints on home screen JFrame
		homeFrame = new JFrame("Zelda Reborn");
		h = new Home();
		homeFrame.setSize(1024, 768);
		homeFrame.setResizable(false);
		homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		homeFrame.setLocationRelativeTo(null);
		homeFrame.setVisible(true);
		homeFrame.add(h);
		
		//start background music
		new Sound("/forest.wav", true);
	}

	public void paint (Graphics g) {
		//draw the relevant images to the screen
		g.drawImage(backgroundZelda, 0, 0, 1024, 768, null);
		//g.drawImage(startGame, 750, 250, 250, 250, null);
		g.drawImage(startGame, 750, 175, 250, 250, null);
		g.drawImage(multiplayerGame, 705, 300, 300, 300, null);
		g.drawImage(highscoreGame, 750, 365, 300, 300, null);
		g.drawImage(endGame, 705, 440, 300, 300, null);


		//grey colour used for font input
		g.setColor(new Color(211,211,211));

		//set title (and copyright text) with specific font size at specific pixel points on the screen
		//font-source: https://www.1001fonts.com/teen-font.html
		//font-source: https://www.fontspace.com/house-of-lime/stars
		g.setFont(GameFont.getFont("/Stars.ttf", 100));
		g.drawString("ZELDA", 40, 120);
		g.setFont(GameFont.getFont("/teen_bold.ttf", 50));
		g.drawString("Reborn", 50, 180);
		g.setFont(GameFont.getFont("/teen.ttf", 15));
		g.drawString("Copyright - An original game by GameWarriors", 640, 710);
		g.drawString("Background credits: GameWarriors", 30, 710);	

		
		//display correct image for certain difficulty level setting
		if (difficultyLevel == 1) {
			g.drawImage(diff1[1], 800, 300, 125, 125, null);	
		}
		else {
			g.drawImage(diff1[0], 800, 300, 125, 125, null);
		}

		if (difficultyLevel == 2) {
			g.drawImage(diff2[1], 850, 300, 125, 125, null);
		}
		else {
			g.drawImage(diff2[0], 850, 300, 125, 125, null);
		}

		if (difficultyLevel == 3) {
			g.drawImage(diff3[1], 900, 300, 125, 125, null);
		}
		else {
			g.drawImage(diff3[0], 900, 300, 125, 125, null);
		}		
		g.drawImage(difficulty, 790, 300, 200, 200, null);

		/*
		//grey colour used for font input
		g.setColor(new Color(211,211,211));
		
		//set title (and copyright text) with specific font size at specific pixel points on the screen
		//font-source: https://www.1001fonts.com/teen-font.html
		//font-source: https://www.fontspace.com/house-of-lime/stars
		g.setFont(GameFont.getFont("/Stars.ttf", 100));
		g.drawString("ZELDA", 40, 120);
		g.setFont(GameFont.getFont("/teen_bold.ttf", 50));
		g.drawString("Reborn", 50, 180);
		g.setFont(GameFont.getFont("/teen.ttf", 15));
		g.drawString("Copyright - An original game by GameWarriors", 640, 710);
		g.drawString("Background credits: GameWarriors", 30, 710);	
		*/
	}
}

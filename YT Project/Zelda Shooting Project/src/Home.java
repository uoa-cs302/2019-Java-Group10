//This class creates the main home screen which is launched when the program is run. 
//This Home Class shows the first frame of the game - the home screen which guides the user to play the game, 
//along with other settings and options

//Created By: GameWarriors
//Date Modified: 19/04/19

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
	Font font;
	public static JFrame homeFrame;
	Rectangle rectStart, rectEnd, rectMultiplayer, rectOptions;
	private BufferedImage backgroundZelda = null, startGame = null, endGame = null, multiplayerGame = null, optionsGame = null;
	
	//private SpriteSheet ss;
	//private BufferedImage spriteSheet = null;
			
	public Home() {
		
		//load the following images to be used within the home screen
		BufferedImageLoader loader = new BufferedImageLoader();
		backgroundZelda = loader.loadImage("/ZeldaBackground.jpg");
		startGame = loader.loadImage("/play.PNG");
		multiplayerGame = loader.loadImage("/multiplayer.PNG");
		optionsGame = loader.loadImage("/options.PNG");
		endGame = loader.loadImage("/exit.PNG");

		//spriteSheet = loader.loadImage("/sprite_sheet.png");
		//ss = new SpriteSheet(spriteSheet);
		//startGame = ss.grabImage(7, 5, 32, 32);
		
		//button rectangle detection on click
		rectStart = new Rectangle();
		rectStart.setBounds(804, 335, 200, 70);
		rectMultiplayer = new Rectangle();
		rectMultiplayer.setBounds(737, 416, 263, 55);
		rectOptions = new Rectangle();
		rectOptions.setBounds(805, 486, 195, 55);
		rectEnd = new Rectangle();
		rectEnd.setBounds(849, 557, 152, 55);
		
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
					new Game();
				}
				//multiplayer button input
				else if ((x > rectMultiplayer.getX()) && (x < rectMultiplayer.getX() + 263) && (y > rectMultiplayer.getY()) && (y < rectMultiplayer.getY() + 55)) {
					System.out.println("multiplayer");
				}
				//options button input
				else if ((x > rectOptions.getX()) && (x < rectOptions.getX() + 195) && (y > rectOptions.getY()) && (y < rectOptions.getY() + 55)) {
					System.out.println("options");
				}
				//end button input
				else if ((x > rectEnd.getX()) && (x < rectEnd.getX() + 152) && (y > rectEnd.getY()) && (y < rectEnd.getY() + 55)) {
					System.exit(0);
				}
			}
		});	
	}

	public static void main(String args []) {
		//create and set conditions and constraints on home screen JFrame
		homeFrame = new JFrame("Zelda Reborn");
		Home h = new Home();
		homeFrame.setSize(1024, 768);
		homeFrame.setResizable(false);
		homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		homeFrame.setLocationRelativeTo(null);
		homeFrame.setVisible(true);
		homeFrame.add(h);
	}

	public void paint (Graphics g) {   
		//draw the relevant images to the screen
		g.drawImage(backgroundZelda, 0, 0, 1024, 768, null);
		g.drawImage(startGame, 750, 250, 250, 250, null);
		g.drawImage(multiplayerGame, 705, 300, 300, 300, null);
		g.drawImage(optionsGame, 705, 370, 300, 300, null);
		g.drawImage(endGame, 705, 440, 300, 300, null);

		//grey colour used for font input
		g.setColor(new Color(211,211,211));
		
		//set title (and copyright text) with specific font size at specific pixel points on the screen
		//font-source: https://www.1001fonts.com/teen-font.html
		//font-source: https://www.fontspace.com/house-of-lime/stars
		g.setFont(getFont("/Stars.ttf", 100));
		g.drawString("ZELDA", 40, 120);
		g.setFont(getFont("/teen_bold.ttf", 50));
		g.drawString("Reborn", 50, 180);
		g.setFont(getFont("/teen.ttf", 15));
		g.drawString("� Copyright - An original game by GameWarriors", 30, 700);
	}
	
	
	//code-source : https://www.programcreek.com/java-api-examples/?class=java.awt.Font&method=createFont
	public static Font getFont(String file, int size) {
	    try {
	    	//add new font into game from file input
	        Font font = Font.createFont(Font.TRUETYPE_FONT, Home.class.getResourceAsStream(file));
	        font = font.deriveFont(Font.TRUETYPE_FONT, size);
	        return font;
	    } catch (Exception ex) {
	    	//if there is an error, display text with default font, so the game doesn't crash
	    	return new Font("Tahoma", Font.BOLD, size);
	    }
	}
}

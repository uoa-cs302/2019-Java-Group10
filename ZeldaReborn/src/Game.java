import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

//Zac. [RealTutsGML]. (2017, May). Java Game Programming Wizard Top Down Shooter [Video file]. 
//Retrieved from https://www.youtube.com/watch?v=e9jRfgjV4FQ

//game loop class
public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	public JFrame frame;
	private boolean isRunning = false;
	private Handler handler;
	private Thread thread;
	private Camera camera;
	public SpriteSheet ss, ss_zelda, ss_spider, ss_powerups, ss_multiplayer, ss_enemyBoss, ss_bear, ss_hunter;
	private Home home;
	private Sound sound;
	
	boolean dirtTile = true;
	public boolean levelSwitch= false;
	boolean countdownFlag = true;
	boolean pauseGame=false;
	
	//file to store highscores
	String file = "highscoreStorage.txt";
	
	public BufferedImage grass = null, dirt = null, background = null;
	public BufferedImage level1 = null, level2 = null, levelBoss = null, 
			levelSpinOff = null,
			levelMultiplayer = null, levelHunter = null, pause = null, esc = null, mute = null,
			up = null, right = null, left = null, down = null, mouse = null;
	private BufferedImage spriteSheet = null;
	
	private int ammo = 200; //200 arrows

	private int hp = 100;
	private int highscore =0;
	public int finalScore = 0;
	public int multiplayerHp = 1000;
	long start, timingValue;
	long finish = 0;
	boolean alive =true;
	
	private ArrayList<Integer> topScores = new ArrayList<Integer>();

	int scoreOne=0, scoreTwo=0, scoreThree=0;
	int scoreFour, scoreFive, scoreSix;
	
	private int levelCounter;
	int difficulty=1;
	
	boolean problem=true;
	Font customFontStars, customFontTeenBold, customFontTeenBoldTwelve, customFontTeenBoldFifteen, customFontTeenBoldThirty;
	
	public Game(Home home, int level, Sound sound) {
		this.home = home;
		difficulty = home.difficultyLevel;
		this.sound=sound;
		
		//make new JFrame window with the following input parameters
		int w=1024;
		int h=768;
		
		frame = new JFrame("Zelda Reborn");
		frame.setPreferredSize(new Dimension(w, h));
		frame.setMaximumSize(new Dimension(w, h));
		frame.setMinimumSize(new Dimension(w, h));
		
		frame.add(this);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);	
		
		start();
		
		handler = new Handler();
		camera = new Camera(0, 0, level);

		this.addKeyListener(new KeyInput(handler, this, sound));
		
		//image loading
		BufferedImageLoader loader = new BufferedImageLoader();
		level1 = loader.loadImage("./zelda_level_1.png");
		level2 = loader.loadImage("./zelda_level_2.png");
		
		levelBoss = loader.loadImage("./zelda_level_boss.png");
		levelSpinOff = loader.loadImage("./zelda_level_spinoff.png");
		levelMultiplayer = loader.loadImage("./zelda_level_multiplayer.png");
		levelHunter = loader.loadImage("./zelda_level_hunter.png");
		background = loader.loadImage("./backgroundForest.jpg");
		
		spriteSheet = loader.loadImage("./sprite_sheet.png");
		ss = new SpriteSheet(spriteSheet);
		
		//zelda's spritesheet
		spriteSheet = loader.loadImage("./ss_zelda.png");
		ss_zelda = new SpriteSheet(spriteSheet);
		
		spriteSheet = loader.loadImage("./ss_spider.png");
		ss_spider = new SpriteSheet(spriteSheet);
		
		spriteSheet = loader.loadImage("./ss_powerups.png");
		ss_powerups = new SpriteSheet(spriteSheet);
		
		spriteSheet = loader.loadImage("./ss_multiplayer.png");
		ss_multiplayer = new SpriteSheet(spriteSheet);
		
		spriteSheet = loader.loadImage("./ss_enemyBoss.png");
		ss_enemyBoss = new SpriteSheet(spriteSheet);
		
		spriteSheet = loader.loadImage("./ss_enemyBear.png");
		ss_bear = new SpriteSheet(spriteSheet);
		
		spriteSheet = loader.loadImage("./ss_hunter.png");
		ss_hunter = new SpriteSheet(spriteSheet);
		
		//controls images
		pause = loader.loadImage("./pause.png");
		esc = loader.loadImage("./esc.png");
		left = loader.loadImage("./left.png");
		right = loader.loadImage("./right.png");
		up = loader.loadImage("./up.png");
		down = loader.loadImage("./down.png");
		mouse = loader.loadImage("./mouse.png");
		mute = loader.loadImage("./mute.png");
		
		grass = ss.grabImage(3, 3, 32, 32);
		dirt = ss.grabImage(2, 1, 32, 32);
		
		this.addMouseListener(new MouseInput(handler, camera, this, ss));
		
		//passing initial control to current level
		if(level == 1) {
			levelCounter=1;
			loadLevel(level1);			
		}
		else if (level == 6){
			levelCounter = 6;
			hp=100;
			ammo = 200;
			switchLevel(6);
		}
		
		if (level ==7) {
			topHighscores();
			sound.stopSound();
			this.frame.dispose();
			new EndGame(this, 4);
			this.stop();

		}
	}	
	
	//Main tick() method
	//updates everything 60times/ sec
	public void tick() {
		//only run if game is not paused
		if(!pauseGame) {
			//finding the player object to pass parameters to our camera
			for (int i = 0; i < handler.obj.size(); i++) {
				if (handler.obj.get(i).getId() == ID.Player) {
					camera.tick(handler.obj.get(i));
				}
			}

			handler.tick();

			//only perform function is Zelda is still alive
			if (alive) {
				//!=6 since level 6 is multiplayer
				if (levelCounter != 6 && hp <= 0) {
					highscorePrint();
					this.frame.dispose();
					sound.stopSound();
					new EndGame(this, 2);
					alive = false;
					this.stop();
				}
			}

			if(levelSwitch ==true) {
				if (levelCounter ==2) {
					switchLevel(2);
					levelSwitch =false;
				}
				else if (levelCounter ==3) {
					switchLevel(3);
					levelSwitch =false;
				}

				//boss Level
				else if (levelCounter ==4) {
					switchLevel(4);
					hp = 100;
					levelSwitch =false;
				}

				//hunter level
				else if (levelCounter ==5) {
					//Game.LEVEL++;
					switchLevel(5);
					levelSwitch =false;
				}
			}

			//total score when game is not finished.
			//score based on time and enemies killed
			finalScore = highscore - (int) (finish+timingValue);

			//display which player wins in multiplayer
			if (levelCounter == 6) {
				if (hp <= 0) {
					//JOptionPanes used 
					JOptionPane.showMessageDialog(null, "Player 2 Wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
					this.frame.dispose();
					sound.stopSound();
					Home.main(null);
					this.stop();
				}
				else if (multiplayerHp <= 0){
					JOptionPane.showMessageDialog(null, "Player 1 Wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
					this.frame.dispose();
					sound.stopSound();
					Home.main(null);
					this.stop();
				}
			}	
		}
	}
	
	//All drawing takes place here
	//updates couple 1000times/sec
	public void render() {
		//only runs once		
		if(problem ==true) {			
			customFontTeenBold= GameFont.getFont("/teen_bold.ttf", 20);
			customFontTeenBoldTwelve= GameFont.getFont("/teen_bold.ttf", 12);
			customFontTeenBoldFifteen= GameFont.getFont("/teen_bold.ttf", 15);
			customFontTeenBoldThirty= GameFont.getFont("/teen_bold.ttf", 30);
			
			problem=false;
		}
		
		
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			//create buffer strategy with 3 frames ready to display
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;		
		
		//everything between g2d is getting translated
		g2d.translate(-camera.getX(), -camera.getY());	
		
		//draws the floor image as the background
		//change background image to dirt tiles for level 1 and level 4
		if (levelCounter == 1 || levelCounter == 4) {
			for (int i = 0; i < 30*72; i += 32) {
				for (int j = 0; j < 30*72; j +=32) {
						g.drawImage(dirt, i, j, null);
				}
			}
		}
		//for all other levels, have background as grass
		else if (levelCounter == 2 || levelCounter == 3 ||levelCounter ==5 || levelCounter ==6){
			for (int i = 0; i < 30*72; i += 32) {
				for (int j = 0; j < 30*72; j +=32) {
						g.drawImage(grass, i, j, null);
				}
			}
		}
		
		//TUTORIAL (game instructions)
		if (levelCounter == 1) {
			//grey colour used for font input
			g.setColor(new Color(211,211,211));
			g.setFont(customFontTeenBold);
			//storyline
			g.drawString("Game Storyline:", 50, 730);
			g.setFont(new Font("Arial", Font.TRUETYPE_FONT, 16));
			g.drawString("... your best friend, Link, has been injured and "
					+ "needs special fruits to survive.", 50, 760);
			g.drawString("After discovering these fruits can only be " + 
					"found in this forest, 'The Forest of", 50, 780);
			g.drawString("Panthera', your aim is to make your way through the forest in order to", 50, 800);
			g.drawString("find these special healing fruits and bring back to Link. During your journey,", 50, 820);
			g.drawString("you will oppose various animals which you will have to overcome to", 50, 840);
			g.drawString("unlock a special fruit and to get to the next stage of the forest. Save Link", 50, 860);
			g.drawString("by collecting all the special fruits before he dies.", 50, 880);
			
			//Controls
			g.setFont(customFontTeenBold);
			g.drawString("Controls", 50, 540);
			g.setFont(new Font("Arial", Font.TRUETYPE_FONT, 17));
			g.drawImage(pause, 50, 530, 100, 100, null);
			g.drawString("   - Pause", 120, 585);
			g.drawImage(esc, 50, 580, 100, 100, null);
			g.drawString("x2  - Home", 105, 635);
			g.drawImage(mute, 32, 630, 100, 100, null);
			g.drawString("   - Mute", 120, 685);
			g.drawImage(up, 300, 530, 100, 100, null);
			g.drawImage(down, 300, 570, 100, 100, null);
			g.drawImage(right, 340, 570, 100, 100, null);
			g.drawImage(left, 260, 570, 100, 100, null);
			g.drawString("Move", 325, 665);
			g.drawImage(mouse, 500, 555, 100, 100, null);
			g.drawString("Shoot", 525, 665);
			
			
			 
			//Powerups and Powerdowns
			g.setFont(customFontTeenBold);
			g.drawString("Powerups/ Powerdowns", 50, 440);
			g.setFont(new Font("Arial", Font.TRUETYPE_FONT, 17));
			g.drawImage(ss_powerups.grabBigImage(1, 1, 60, 60), 70, 440, null);
			g.drawString("Extra Life", 70, 500);
			g.drawImage(ss_powerups.grabBigImage(2, 1, 60, 60), 180, 440, null);
			g.drawString("Loss of Life", 170, 500);
			g.drawImage(ss_powerups.grabBigImage(1, 2, 60, 60), 310, 440, null);
			g.drawString("Speed booster", 280, 500);	
			g.drawImage(ss_powerups.grabBigImage(2, 2, 60, 60), 450, 440, null);
			g.drawString("Speed decreaser", 420, 500);
			g.drawImage(ss_powerups.grabBigImage(1, 3, 60, 60), 570, 440, null);
			g.drawString("Ammo", 570, 500);
			
			
			//special fruits
			g.setFont(customFontTeenBold);
			g.drawString("Special Fruits", 50, 330);
			g.setFont(new Font("Arial", Font.TRUETYPE_FONT, 17));
			g.drawImage(ss.grabImage(4, 4, 32, 32), 80, 340, null);
			g.drawString("Level 1", 70, 400);
			g.drawImage(ss.grabImage(4, 5, 32, 32), 190, 340, null);
			g.drawString("Level 2", 180, 400);
			g.drawImage(ss.grabImage(5, 5, 32, 32), 300, 340, null);
			g.drawString("Level 3", 290, 400);
			g.drawImage(ss.grabImage(6, 5, 32, 32), 410, 340, null);
			g.drawString("Level 4", 400, 400);
			g.drawImage(ss.grabImage(3, 5, 32, 32), 520, 340, null);
			g.drawString("Level 5", 510, 400);			
		}
		
		//all game objects being rendered here
		handler.render(g);
		
		
		g2d.translate(camera.getX(), camera.getY());
		
		//IMPORTANT NOTE 
		//ANYTHING BELOW TRANSALTE IS ALWAYS VISIBLE TO US ON THE SCREEN
		
		//health for the game - UI
		//health box
		g.setColor(Color.GRAY);
		
		//multiplayer
		if (levelCounter == 6) {
			//draw health box background for player 1 and player 2
			g.fillRect(805, 695, 200, 32);
			g.fillRect(5, 695, 200, 32);
			
			
			//player 2 health
			if ((multiplayerHp)/10 < 50 && (multiplayerHp)/10 > 35) {
				g.setColor(Color.YELLOW);
			}
			else if ((multiplayerHp)/10 <= 35) {
				g.setColor(Color.RED);
			}
			else {
				g.setColor(Color.GREEN);
			}
			g.fillRect(5, 695, (multiplayerHp/5), 32);
			//outline for the health bar
			g.setColor(Color.BLACK);
			g.drawRect(5, 695, 200, 32);
			
			
			
			//player 1 health
			if(hp < 50 && hp > 35) {
				g.setColor(Color.YELLOW);
			}
			else if (hp <= 35) {
				g.setColor(Color.RED);
			}
			else {
				g.setColor(Color.GREEN);
			}
			g.fillRect(805, 695, hp*2, 32);
			//outline for the health bar
			g.setColor(Color.BLACK);
			g.drawRect(805, 695, 200, 32);
			
			
			//health text for both player 1 and player 2
			g.setColor(new Color(211,211,211));
			g.setFont(customFontTeenBoldTwelve);
			g.drawString("Player 1 Health", 805, 695);
			g.drawString("Player 2 Health", 5, 695);
		}
		else if(levelCounter >= 1 && levelCounter <= 5 ){
			g.fillRect(5, 5, 200, 32);
			//health with colour coordination for specific boundaries
			if(hp < 50 && hp > 35) {
				g.setColor(Color.YELLOW);
			}
			else if (hp <= 35) {
				g.setColor(Color.RED);
			}
			else {
				g.setColor(Color.GREEN);
			}
			g.fillRect(5, 5, hp*2, 32);
			//outline for the health bar
			g.setColor(Color.BLACK);
			g.drawRect(5, 5, 200, 32);
			
			//Health string
			g.setColor(new Color(211,211,211));

			g.setFont(customFontTeenBoldFifteen);
			g.drawString("Health", 5, 50);
		}		
	
		//ammo - UI
		g.setColor(new Color(211,211,211));
		g.setFont(customFontTeenBoldFifteen);
		g.drawString("Ammo", 940, 50);
		g.setFont(customFontTeenBoldThirty);
		
		if (ammo == 0) {
			g.setColor(Color.RED);
			g.drawString("" + ammo, 940, 35);
		}
		else if (ammo < 30 && ammo != 0) {
			g.setColor(Color.YELLOW);
			g.drawString("" + ammo, 940, 35);
		}
		else {
			g.setColor(new Color(211,211,211));
			g.drawString("" + ammo, 940, 35);	
		}
		
		g.setColor(new Color(211,211,211));
		
		//game time
		int timeForScore = (int) (finish + timingValue);
		int countdownTimer = 300 - timeForScore;
		
		//used to check if time has crossed 5 minutes
		if (countdownTimer == 0) {
			countdownFlag = false;
		}
		
		
		if(countdownFlag) {
			if(countdownTimer%60 < 10) {
				g.drawString((countdownTimer/60) + ":" + 
						"0" + (countdownTimer%60), 475, 35);
			}
			else {
				g.drawString((countdownTimer/60) + ":" + 
						(countdownTimer%60), 475, 35);
			}
		}
		else {
			g.drawString("0:00", 475, 35);
		}
		
		//displaying score except on multiplayer level
		if (levelCounter != 6) {
			g.drawString(finalScore + "", 825, 35);
		}
		
		g.setFont(customFontTeenBoldFifteen);
		g.drawString("Time", 485, 50);
		if (levelCounter != 6) {
			g.drawString("Score", 825, 50);
		}
		
		g.dispose();
		bs.show();
	}
	
	
	public void switchLevel(int level) {
		//clears the current level before loading the next level
		handler.clearLevel();
		
		//random motion
		if (level == 2) {
			loadLevel(level2);
		}
		
		if (level == 3) {
			//boss level
			loadLevel(levelBoss);
		}
		
		//semi-Boss Level
		if (level == 4) {
			loadLevel(levelSpinOff);
		}
		
		//Boss-Hunter Level
		if (level == 5) {
			//allows levelCounter to be set for PgDn as well now
			levelCounter=5;
			loadLevel(levelHunter);
		}

		//multiplayer Level
		if (level == 6) {
			loadLevel(levelMultiplayer);
		}
	}
	
	//loading the level via image input
	public void loadLevel(BufferedImage image) {
		int w = image.getWidth();
		int h = image.getHeight();
		
		//checking each pixel for RGB values to load accordingly
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				int pixel = image.getRGB(i, j);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;
				
				//select powerups, powerdowns and special fruit on color detection
				//maroon colour
				if(red == 136 && green == 0 && blue == 21) {
					handler.addObj(new Crate (i*32, j*32, ID.Crate_ammoPlus, ss_powerups, levelCounter));
				}
				//gold - light orange
				if(red == 255 && green == 201 && blue == 14) {
					handler.addObj(new Crate (i*32, j*32, ID.Crate_healthMinus, ss_powerups, levelCounter));
				}
				//light purple
				if(red == 200 && green == 191 && blue == 231) {
					handler.addObj(new Crate (i*32, j*32, ID.Crate_healthPlus, ss_powerups, levelCounter));
				}
				//light blue
				if(red == 153 && green == 217 && blue == 234) {
					handler.addObj(new Crate (i*32, j*32, ID.Crate_speedPlus, ss_powerups, levelCounter));
				}
				//medium blue
				if(red == 0 && green == 162 && blue == 232) {
					handler.addObj(new Crate (i*32, j*32, ID.Crate_speedMinus, ss_powerups, levelCounter));
				}
				//special fruit - brown
				if(red == 185 && green == 122 && blue == 87) {
					handler.addObj(new Crate (i*32, j*32, ID.Crate_fruits, ss, levelCounter));
				}
				
				//adding in game objects based on colour inputs
				if (red == 237 && green ==28 && blue==36) {
					handler.addObj(new Block(i*32, j*32, ID.Block, ss));
				}
				
				if (red == 63 && green == 72 && blue == 204) {
					handler.addObj(new Zelda(i*32, j*32, ID.Player, handler, this, ss_zelda, sound));
				}
				if(red ==34 && green == 177 && blue ==76) {
					handler.addObj(new Enemy (i*32, j*32, ID.Enemy, handler, ss_bear, this));
				}
				
				if(red ==163 && green ==73 && blue ==164) {
					handler.addObj(new EnemySpider (i*32, j*32, ID.EnemySpider, handler, ss_spider, this));
				}
				
				if(red ==255 && green ==127 && blue ==39) {
					handler.addObj(new EnemyBoss (i*32, j*32, ID.EnemyBoss, handler, ss_enemyBoss, this));
				}
				
				if(red ==255 && green ==174 && blue ==201) {
					handler.addObj(new EnemyMultiplayer (i*32, j*32, ID.EnemyMultiplayer, handler, this, ss_hunter));
				}
				
				if(red ==255 && green ==242 && blue ==0) {
					handler.addObj(new EnemyHunter (i*32, j*32, ID.EnemyHunter, handler, ss_hunter, this));
				}				
			}
		}
	}
	
	//causes tick() and render(0 to work appropriately
	public void run() {
		//stock game loop timer - running frame 60 times/ sec
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		//dividing to get in nano-seconds
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		//used to see how many frames per second are used
		int frames = 0;
		while (isRunning) {
			long now = System.nanoTime();
			timingValue = ((now-start)/1000000000);
			
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				tick();
				delta--;
			}
			render();
			frames++;
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frames = 0;
			}
		}
	}
	
	public boolean getlevelSwitch() {
		return levelSwitch;
	}

	public void setlevelSwitch(boolean ls) {
		levelSwitch=ls;
	}
	
	public boolean getpauseGame() {
		return pauseGame;
	}

	public void setpauseGame(boolean pg) {
		pauseGame=pg;
	}
	
	public int getlevelCounter() {
		return levelCounter;
	}

	public void setlevelCounter(int lc) {
		levelCounter=lc;
	}
	
	public int getammo() {
		return ammo;
	}

	public void setammo(int a) {
		ammo=a;
	}
	
	public int gethp() {
		return hp;
	}

	public void sethp(int h) {
		hp=h;
	}
	
	public int gethighscore() {
		return highscore;
	}

	public void sethighscore(int h) {
		highscore=h;
	}

	//use this to print at end of game or when player dies
	public void highscorePrint() {
		int hs=0;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			while (line != null)    // read the score file line by line
			{
				try {
					int score = Integer.parseInt(line.trim());   // parse each line as an int
					if (score > hs)     // and keep track of the largest score
					{ 
						hs = score; 
					}
				} catch (NumberFormatException e1) {
					// ignore invalid scores
				}
				line = reader.readLine();
			}
			reader.close();

		} catch (IOException ex) {
			System.err.println("ERROR reading scores from file");
		}
	    
	 // append the last score to the end of the file
	    try {
	        BufferedWriter output = new BufferedWriter(new FileWriter(file, true));
	        output.newLine();
	        output.append("" + finalScore);
	        output.close();

	    } catch (IOException ex1) {
	        System.out.printf("ERROR writing score to file: %s\n", ex1);
	    }
	}

	public void topHighscores() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			//System.out.println(line);
			while (line != null)   // read the score file line by line
			{
				try {
					int score = Integer.parseInt(line.trim());   // parse each line as an int
					topScores.add(score);
				} catch (NumberFormatException e1) {
					// ignore invalid scores
				}
				line = reader.readLine();
			}
			reader.close();

		} catch (IOException ex) {
			System.err.println("ERROR reading scores from file");
		}
		
		//inbuilt function to sort arraylist
		Collections.sort(topScores);
		Collections.reverse(topScores);
		
		scoreOne = topScores.get(0).intValue();
		scoreTwo = topScores.get(1).intValue();
		scoreThree = topScores.get(2).intValue();
		scoreFour = topScores.get(3).intValue();
		scoreFive = topScores.get(4).intValue();		
	}
	
	public boolean getIsRunning() {
		return isRunning;
	}

	public void setIsRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	
	public void start() {
		isRunning = true;
		thread = new Thread(this);
		thread.start();
		start = System.nanoTime();
	}
	
	//change back to private and fix later
	public void stop() {
		isRunning = false;
		//storing value of current time once pause is called
		finish = finish + timingValue;
		timingValue=0;
	
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}	
}
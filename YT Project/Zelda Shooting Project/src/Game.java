import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JFrame;

//MAIN CLASS BASICALLY. EVRYTHING GETS PUT THROUGH HERE.
//ALL CLASSES INITIALISED HERE

//game loop class - main class - drawing/ rendering -- everything will be called from this class
public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	public JFrame frame;
	private boolean isRunning = false;
	private Handler handler;
	private Thread thread;
	private Camera camera;
	public SpriteSheet ss, ss_zelda, ss_spider, ss_powerups;
	private Home home;
	
	Random rand = new Random();
	
	//int iDirt = 0, jDirt = 0;
	boolean dirtTileOnce = true;
	public int powerup = 1;
	
	private BufferedImage grass = null, dirt = null;
	public BufferedImage level1 = null, level2 = null, levelBoss = null, 
			levelMultiplayer = null, levelHunter = null, pause = null, esc = null,
			up = null, down = null, right = null, left = null, mouse = null, mute = null;
	private BufferedImage spriteSheet = null;
	
	//declared here becoz we'll be drawing this out in a later video
	public int ammo =100; //100 bullets
	//100 health
	public int hp = 100;
	long start, timingValue;
	long finish = 0;
	//long totalTime = 0;
	
	//should start from 1. Kept at 5 to skip levels for now.
	int levelCounter=1;
	int difficulty=1;
	
	//NOTE- Should clear all objects once game ends. Allows for a fresh start on a new game.
	
	public Game(Home home, int level) {
		this.home =home;
		difficulty = home.difficultyLevel;
		
		//make new JFrame window with the following input parameters
		int w=1024;
		int h=768;
		
		//new Window(1024, 768, "Zelda Reborn", this);
		//replaced as below
		
		//
		frame = new JFrame("Zelda Reborn");
		frame.setPreferredSize(new Dimension(w, h));
		frame.setMaximumSize(new Dimension(w, h));
		frame.setMinimumSize(new Dimension(w, h));
		
		frame.add(this);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		//
		
		
		start();
		
		handler = new Handler();
		camera = new Camera(0, 0);
		//remove second this if needed
		this.addKeyListener(new KeyInput(handler, this));
		
		
		//image loading
		BufferedImageLoader loader = new BufferedImageLoader();
		level1 = loader.loadImage("/zelda_level_1.png");
		level2 = loader.loadImage("/zelda_level_2.png");
		
		levelBoss = loader.loadImage("/zelda_level_boss.png");
		levelMultiplayer = loader.loadImage("/zelda_level_multiplayer.png");
		levelHunter = loader.loadImage("/zelda_level_hunter.png");
		
		spriteSheet = loader.loadImage("/sprite_sheet.png");
		ss = new SpriteSheet(spriteSheet);
		
		//zelda's spritesheet
		spriteSheet = loader.loadImage("/ss_zelda.png");
		ss_zelda = new SpriteSheet(spriteSheet);
		
		spriteSheet = loader.loadImage("/ss_spider.png");
		ss_spider = new SpriteSheet(spriteSheet);
		
		spriteSheet = loader.loadImage("/ss_powerups.png");
		ss_powerups = new SpriteSheet(spriteSheet);
		
		//controls images
		pause = loader.loadImage("/pause.png");
		esc = loader.loadImage("/esc.png");
		left = loader.loadImage("/left.png");
		right = loader.loadImage("/right.png");
		up = loader.loadImage("/up.png");
		down = loader.loadImage("/down.png");
		mouse = loader.loadImage("/mouse.png");
		mute = loader.loadImage("/mute.png");
		
		grass = ss.grabImage(3, 3, 32, 32);
		dirt = ss.grabImage(2, 1, 32, 32);
		
		this.addMouseListener(new MouseInput(handler, camera, this, ss));
		
		if(level ==1) {
			loadLevel(level1);
			levelCounter++;
		}
		else if (level==6){
			levelCounter =6;
			switchLevel(6);
		}		
		
	}
	
	/*
	public static void main (String args[]) {
		new Game();
	}
	*/
	
	
	//MAIN TICK() METHOD
	//CONTROL GIVEN TO HANDLER.TICK() HERE WHICH CONTROLS EVERYTHING NOW.
	//updates everything 60times/ sec
	public void tick() {		
		//finding the player object to pass parameters to our camera
		for (int i = 0; i < handler.obj.size(); i++) {
			if (handler.obj.get(i).getId() == ID.Player) {
				camera.tick(handler.obj.get(i));
			}
		}
		
		handler.tick();
		
		//change level is health = 0;
		if (hp <= 0 && levelCounter ==2) {
			//Game.LEVEL++;
			switchLevel(2);
			hp = 100;
			levelCounter++;
		}
		
		if (hp <= 0 && levelCounter ==3) {
			//Game.LEVEL++;
			switchLevel(3);
			hp = 100;
			levelCounter++;
		}
		
		//boss Level
		if (hp <= 0 && levelCounter ==4) {
			//Game.LEVEL++;
			switchLevel(4);
			hp = 100;
			levelCounter++;
		}
		
		//hunter level
		if (hp <= 0 && levelCounter ==5) {
			//Game.LEVEL++;
			switchLevel(5);
			hp = 100;
		}
		
		//multiplayer level
		if (hp <= 0 && levelCounter ==6) {
			//Game.LEVEL++;
			switchLevel(6);
			hp = 100;
		}
		
		
	}
	
	//updates couple 1000times/ sec
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			//create buffer strategy with 3 arguments -- frames ready to display
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;
		
		
		///// DRAW ITEMS HERE
			
		//everything between g2d is getting translated
		g2d.translate(-camera.getX(), -camera.getY());		
		
		//draws the floor image as the background
		for (int i = 0; i < 30*72; i += 32) {
			for (int j = 0; j < 30*72; j +=32) {
					g.drawImage(grass, i, j, null);	
			}
		}	


//		if (dirtTileOnce) {
//			for (int i = 0; i < 20; i++) {
//				g.drawImage(dirt, rand.nextInt(1024), rand.nextInt(768), null);
//			}
//			dirtTileOnce = false;
//		}

//		if (dirtTile) {
//			g.drawImage(dirt, iDirt, jDirt, null);
//		}
		
		
		//TUTORIAL (game instructions)
		if (levelCounter == 2) {
			//grey colour used for font input
			g.setColor(new Color(211,211,211));
			g.setFont(GameFont.getFont("/teen_bold.ttf", 20));
			//storyline
			g.drawString("Game Storyline:", 50, 730);
			g.setFont(GameFont.getFont("/teen.ttf", 15));
			g.drawString("... your best friend, Link, has been injured and "
					+ "needs special fruits to survive", 50, 760);
			g.drawString("After discovering these fruits can only be " + 
					"found in this forest, 'The Forest of", 50, 780);
			g.drawString("Panthera' your aim is to make your way through the forest in order to", 50, 800);
			g.drawString("find these special healing fruits to bring back to Link. During your journey,", 50, 820);
			g.drawString("you will oppose various animals which you will have to overcome to", 50, 840);
			g.drawString("unlock a special fruit and to get to the next stage of the forest. Save Link", 50, 860);
			g.drawString("by collecting all the special fruits before he dies.", 50, 880);
			
			//Controls
			g.setFont(GameFont.getFont("/teen_bold.ttf", 20));
			g.drawString("Controls", 50, 540);
			g.setFont(GameFont.getFont("/teen.ttf", 15));
			g.drawImage(pause, 50, 530, 100, 100, null);
			g.drawString("Pause", 120, 580);
			g.drawImage(esc, 50, 580, 100, 100, null);
			g.drawString("Home", 120, 630);
			g.drawImage(mute, 32, 630, 100, 100, null);
			g.drawString("Mute", 120, 680);
			g.drawImage(up, 300, 530, 100, 100, null);
			g.drawImage(down, 300, 570, 100, 100, null);
			g.drawImage(right, 340, 570, 100, 100, null);
			g.drawImage(left, 260, 570, 100, 100, null);
			g.drawString("Move", 325, 665);
			g.drawImage(mouse, 500, 555, 100, 100, null);
			g.drawString("Shoot", 525, 665);
			
			//Powerups and Powerdowns
			g.setFont(GameFont.getFont("/teen_bold.ttf", 20));
			g.drawString("Powerups/ Powerdowns", 50, 440);
			g.setFont(GameFont.getFont("/teen.ttf", 15));
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
		}
		
		handler.render(g);
		
		g2d.translate(camera.getX(), camera.getY());
		
		//IMPORTANT NOTE 
		//ANYTHING BELOW TRANSALTE IS ALWAYS VISIBLE TO US ON THE SCREEN
		
		//health for the game - UI
		//health box
		g.setColor(Color.GRAY);
		g.fillRect(5, 5, 200, 32);
	
		//health with colour coordination for specific boundaries
		if(hp < 50 && hp > 35) {
			g.setColor(Color.YELLOW);
		}
		else if (hp < 35) {
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
		g.setFont(GameFont.getFont("/teen_bold.ttf", 15));
		g.drawString("Health", 5, 50);
		
		
		//ammo - UI - bottom left corner
//		g.setColor(new Color(211,211,211));
//		g.drawString("Ammo", 15, 720);
//		g.setFont(GameFont.getFont("/teen_bold.ttf", 30));
//		g.drawString("" + ammo, 15, 700);
	
		//ammo - UI
		g.setColor(new Color(211,211,211));
		g.setFont(GameFont.getFont("/teen_bold.ttf", 15));
		g.drawString("Ammo", 940, 50);
		g.setFont(GameFont.getFont("/teen_bold.ttf", 30));
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
		g.drawString("Time: " + (finish+timingValue) + "sec", 450, 50);
		
		
		g.dispose();
		bs.show();
	}
	
	
	public void switchLevel(int level) {
		//clears the current level before loading the next level
		handler.clearLevel();
		
		if (level == 2) {
			loadLevel(level2);
		}
		
		if (level == 3) {
			//loading level 2 here. Change to Level 3 when its set up.
			loadLevel(levelBoss);
		}
		
		//semi-Boss Level
		if (level == 4) {
			loadLevel(levelBoss);
		}
		
		//Boss-Hunter Level
		if (level == 5) {
			//allows levelCounter to be set for PgDn as well now
			levelCounter=5;
			loadLevel(levelHunter);
		}

		//Hunter Level
		if (level == 6) {
			loadLevel(levelMultiplayer);
		}
	}
	
	//loading the level
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
				
				//not perfect color palettes therefore >=
				
				
				//NUMBERS NEEDS TO BE CHECKED FOR MORE CORRECT VALUES.
				//Rn, just working off guess limits.
				
				
				if(red == 153 && green ==217 && blue ==234) {
					if (powerup == 1) {
						handler.addObj(new Crate (i*32, j*32, ID.Crate_healthPlus, ss_powerups));
					}
					else if (powerup == 2) {
						handler.addObj(new Crate (i*32, j*32, ID.Crate_healthMinus, ss_powerups));
					}
					else if (powerup == 3) {
						handler.addObj(new Crate (i*32, j*32, ID.Crate_speedPlus, ss_powerups));
					}
					else if (powerup == 4) {
						handler.addObj(new Crate (i*32, j*32, ID.Crate_speedMinus, ss_powerups));
					}
					else if (powerup == 5 || powerup == 6) {
						handler.addObj(new Crate (i*32, j*32, ID.Crate_ammoPlus, ss_powerups));
					}
					//handler.addObj(new Crate (i*32, j*32, ID.Crate, ss_powerups, powerup));
					//alternate between each powerup upon color detection
					if (powerup == 6) {
						powerup = 1;
					}
					else {
						powerup++;
					}
				}
				if (red == 237 && green ==28 && blue==36) {
					handler.addObj(new Block(i*32, j*32, ID.Block, ss));
				}
//				if (red == 127 && green ==127 && blue==127) {
//					dirtTile = true;
//					iDirt = i;
//					jDirt = j;
//				}
//				else {
//					dirtTile = false;
//				}
				if (red == 63 && green == 72 && blue == 204) {
					handler.addObj(new Zelda(i*32, j*32, ID.Player, handler, this, ss_zelda));
				}
				if(red ==34 && green == 177 && blue ==76) {
					handler.addObj(new Enemy (i*32, j*32, ID.Enemy, handler, ss));
				}
				
				if(red ==163 && green ==73 && blue ==164) {
					handler.addObj(new EnemySpider (i*32, j*32, ID.EnemySpider, handler, ss_spider));
				}
				
				if(red ==255 && green ==127 && blue ==39) {
					handler.addObj(new EnemyBoss (i*32, j*32, ID.EnemyBoss, handler, ss, this));
				}
				
				if(red ==255 && green ==174 && blue ==201) {
					handler.addObj(new EnemyMultiplayer (i*32, j*32, ID.EnemyMultiplayer, handler, ss));
				}
				
				if(red ==255 && green ==242 && blue ==0) {
					handler.addObj(new EnemyHunter (i*32, j*32, ID.EnemyHunter, handler, ss, this));
				}
				
				
			}
		}
		
	}
	
	public void run() {
		//System.out.println("1-outer");
		//stock game loop timer - running frame 60 times/ sec
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (isRunning) {
			//System.out.println("2-inner");
			long now = System.nanoTime();
			
			//finish = System.nanoTime();
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
	
	public boolean getIsRunning() {
		return isRunning;
	}

	public void setIsRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	//was private before
	public void start() {
		isRunning = true;
		thread = new Thread(this);
		thread.start();
		start = System.nanoTime();
	}
	
	//change back to private and fix later
	public void stop() {
		isRunning = false;
		finish = finish + timingValue;
		timingValue=0;
	
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}	
}
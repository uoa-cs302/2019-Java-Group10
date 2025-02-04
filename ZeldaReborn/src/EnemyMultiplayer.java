import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

public class EnemyMultiplayer extends GameObject{
		
	Random r = new Random();
	public Game game;
	private Handler handler;
	
	private BufferedImage[] multiplayer_imageFront = new BufferedImage[2];
	private BufferedImage[] multiplayer_imageRight = new BufferedImage[2];
	private BufferedImage[] multiplayer_imageLeft = new BufferedImage[2];
	private BufferedImage[] multiplayer_imageBack = new BufferedImage[2];

	private BufferedImage wInput = null, aInput = null, sInput = null, dInput = null;
	
	Animations animFront, animRight, animLeft, animBack;
	int choose = 0;
	
	public EnemyMultiplayer(int x, int y, ID id, Handler handler, Game game, SpriteSheet ss) {
		super(x, y, id, ss);
		this.handler = handler;
		this.game = game;
		
		for (int i = 0; i < 2; i ++) {
			multiplayer_imageFront[i] = ss.grabMediumImage(i+1, 1, 50, 50);
			multiplayer_imageRight[i] = ss.grabMediumImage(i+1, 2, 50, 50);
			multiplayer_imageBack[i] = ss.grabMediumImage(i+3, 1, 50, 50);
			multiplayer_imageLeft[i] = ss.grabMediumImage(i+3, 2, 50, 50);
		}
		
		animFront = new Animations(2, multiplayer_imageFront);
		animRight = new Animations(2, multiplayer_imageRight);
		animLeft = new Animations(2, multiplayer_imageLeft);
		animBack = new Animations(2, multiplayer_imageBack);
		
		//loading images as png files here
		BufferedImageLoader loader = new BufferedImageLoader();
		wInput = loader.loadImage("/w.png");
		aInput = loader.loadImage("/a.png");
		sInput = loader.loadImage("/s.png");
		dInput = loader.loadImage("/d.png");
	}

	@Override
	public void tick() {
		x = x+velX;
		y = y+velY;
		
		collision();
		
		if (handler.isUpM()) {
			velY = -5;
			animBack.runAnimation();
		}
		//improving lag
		else if (!handler.isDownM()) {
			velY = 0;
		}
		
		if (handler.isDownM()) {
			velY = 5;
			animFront.runAnimation();
		}
		else if (!handler.isUpM()) {
			velY = 0;
		}
		
		if (handler.isRightM()) {
			velX = 5;
			animRight.runAnimation();
		}
		else if (!handler.isLeftM()) {
			velX = 0;
		}
		
		if (handler.isLeftM()) {
			velX = -5;
			animLeft.runAnimation();
		}
		else if (!handler.isRightM()) {
			velX = 0;
		}
		
		
		if(game.multiplayerHp <= 0) {
			handler.removeObj(this);
		}
	}
	
	//collision detection for multiplayer
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
			
			if(temp.getId() == ID.Arrow) {
				if(getBounds().intersects(temp.getBounds())) {
					//if health=100, then it takes 2 shots for enemy to die
					game.multiplayerHp = game.multiplayerHp-50;
					handler.removeObj(temp);
				}
				
			}			
		}
	}
	
	
	@Override
	public void render(Graphics g) {
		g.setColor(new Color(211,211,211));
		g.setFont(new Font("Arial", Font.TRUETYPE_FONT, 17));
		
		//draw player 2 controls
		g.drawImage(wInput, 100, 500, 100, 100, null);
		g.drawImage(sInput, 100, 540, 100, 100, null);
		g.drawImage(dInput, 140, 540, 100, 100, null);
		g.drawImage(aInput, 60, 540, 100, 100, null);
		g.drawString("Player 2", 115, 640);
		
		//draw player 1 controls
		g.drawImage(game.up, 720, 500, 100, 100, null);
		g.drawImage(game.down, 720, 540, 100, 100, null);
		g.drawImage(game.right, 760, 540, 100, 100, null);
		g.drawImage(game.left, 680, 540, 100, 100, null);
		g.drawImage(game.mouse, 840, 525, 100, 100, null);
		g.drawString("Player 1", 790, 640);
		
		//draw player2 animations
		if (velX == 0 && velY == 0) {
			g.drawImage(multiplayer_imageFront[0], x, y, null);
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
		return new Rectangle(x+5, y, 40, 47);
	}
	

	//making bounding box a little bigger. To allow for spaces b/w blocks
	public Rectangle getBoundsOuter() {
		return new Rectangle(x-16 , y-16, 64, 64);
	}

}

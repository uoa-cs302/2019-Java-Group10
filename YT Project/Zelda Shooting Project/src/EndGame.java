import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JFrame;

//NEED TO ASK DEV ABOUT THREADS FOR THIS CLASS!!!!

public class EndGame extends Canvas{

	private Game game;
	private int value;
	private JFrame frame;

	public EndGame(Game game, int value) {
		this.game = game;
		this.value = value;
		frame = new JFrame("Zelda Reborn");
		frame.setSize(1024, 768);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.add(this);
	}
	
	public void paint(Graphics g) {
		for (int i = 0; i < 30*72; i += 32) {
			for (int j = 0; j < 30*72; j +=32) {
					g.drawImage(game.grass, i, j, null);
			}
		}
		
		if(value ==1) {
			//when u collect last fruit
			//put Game OVER and Link saved and stuff here
			//finalScore as well here
		}
		else if (value ==2 ) {
			g.drawString("Zelda died.Shame", 300, 300);	
			//this is when u die
			//put GAME OVER and Unfortunately u couldnt save link ...
			//your score was finalScore
		}
		else if (value ==3) {
			//after boss level, call this
			//spin off level intro.
			//this takes to spin off level
		}
		else if (value ==4) {
			//highscore
		}
		//g.drawString("DOES THIS WORK???", 300, 300);		
	}
}

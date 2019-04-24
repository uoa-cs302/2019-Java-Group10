import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JFrame;

//NEED TO ASK DEV ABOUT THREADS FOR THIS CLASS!!!!

public class EndGame extends Canvas{

	private Game game;
	private JFrame frame;

	public EndGame(Game game) {
		this.game = game;
		frame = new JFrame("Zelda Reborn");
		frame.setSize(600, 400);
		frame.setResizable(false);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		g.drawString("DOES THIS WORK???", 300, 300);
		
	}
}

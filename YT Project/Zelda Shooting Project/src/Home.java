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
	
	public static JFrame homeFrame;
	Rectangle r;
	private BufferedImage backgroundZelda = null, startGame = null;
			
	public Home() {
		
		BufferedImageLoader loader = new BufferedImageLoader();
		backgroundZelda = loader.loadImage("/ZeldaBackground.jpg");
		startGame = loader.loadImage("/start.PNG");
			
		
		r = new Rectangle();
		r.setBounds(850, 400, 100, 70);
		
		//detect mouse input to buttons
		addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				double x = e.getX();
				double y = e.getY();
			
				//start button
				if ((x > r.getX()) && (x < r.getX() + 100) && (y > r.getY()) && (y < r.getY() + 70)) {
					homeFrame.dispose();
					new Game();
				}
			}
		});
		
	
	}

	public static void main(String args []) {
		
		homeFrame = new JFrame("Zelda Reborn");
		Home h = new Home();
		homeFrame.setSize(1024, 768);
		//homeFrame.setLayout(null);
		homeFrame.setResizable(false);
		
//		JButton newGame = new JButton("New Game");//new JButton(new ImageIcon("D:\\icon.png")
//		newGame.setBounds(750, 100, 100, 50);
//		newGame.addActionListener(this);

		homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		homeFrame.setLocationRelativeTo(null);
		homeFrame.setVisible(true);
		
		homeFrame.add(h);		
	}
	
	/*
	//new game button click - start new game
	public void actionPerformed(ActionEvent click) {
		if (click.getSource() == r) {
			//JOptionPane.showMessageDialog(null, "Yay"); // above == newGame
			homeFrame.dispose();
			new Game();
		}
	}*/


	public void paint (Graphics g) {
		//g.drawImage(backgroundZelda, 50, 50, null);
		g.drawImage(backgroundZelda, 0, 0, 1024, 768, null);
		g.drawImage(startGame, 850, 400, 100, 70, null);
		g.setColor(Color.WHITE);
		g.setFont(new Font("TimesRoman", Font.BOLD, 80)); 
		g.drawString("ZELDA Reborn", 20, 90);
	}

}

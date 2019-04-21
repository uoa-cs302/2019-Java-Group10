//This class creates the main home screen which is launched when the program is run. 
//This Home Class shows the first frame of the game - the home screen which guides the user to play the game, 
//along with other settings and options

//Created By: GameWarriors
//Date Modified: 19/04/19

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Home implements ActionListener {
	
	private JButton newGame;
	public JFrame homeFrame;
	
	
	private BufferedImage backgroundZelda = null;
	

	
	public Home() {
		homeFrame = new JFrame("Zelda Reborn");
		homeFrame.setSize(1024, 768);
		homeFrame.setLayout(null);
		homeFrame.setResizable(false);
		
		newGame = new JButton("New Game");//new JButton(new ImageIcon("D:\\icon.png")
		newGame.setBounds(750, 100, 100, 50);
		newGame.addActionListener(this);
		
		homeFrame.add(newGame);

		homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		homeFrame.setVisible(true);
		
		
		BufferedImageLoader loader = new BufferedImageLoader();
		backgroundZelda = loader.loadImage("/ZeldaBackground.jpg");
		
		//render(0, 0, g);
	}

	public static void main(String args []) {		
		new Home();
	}
	
	//new game button click - start new game
	public void actionPerformed(ActionEvent click) {
		if (click.getSource() == newGame) {
			//JOptionPane.showMessageDialog(null, "Yay");
			homeFrame.dispose();
			new Game();
		}
	}
	
	
	public void render(int x, int y, Graphics g) {
		//if character is not moving, draw still frame of animation
			g.drawImage(backgroundZelda, x, y, null);
		}
	
}

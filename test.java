import javax.swing.*;
import java.awt.*;
import java.io.*;


public class test extends JFrame {

	//this function creates JPanels that create the boundary of the level in the form of trees 
	private void levelBorder(JPanel border, String direction){
		
		border.setBackground(Color.BLACK);
		//border.setBackground(new Color(16,111,30));
		
		//based on the direction, we align the trees to the respective side of the JPanel
		if (direction == "EAST") {
			add(border, BorderLayout.EAST);	
		}
		else if (direction == "WEST") {
			add(border, BorderLayout.WEST);
		}
		else if (direction == "NORTH") {
			add(border, BorderLayout.NORTH);
		}
		else if (direction == "SOUTH") {
			add(border, BorderLayout.SOUTH);
		}
		
		//this creates an x amount of trees defined by its direction
		if ((direction == "EAST") || (direction == "WEST")) {
			border.setLayout(new GridLayout(17, 10, 0, 0));
			for(int i = 0; i< 17; i++){
				JLabel tree = new JLabel(new ImageIcon("tree.png"));
				border.add(tree);
			}
		}
		if  (direction == "SOUTH") {
			border.setLayout(new GridLayout(1, 28, 0, 0));
			for(int i = 0; i< 28; i++){
				JLabel tree = new JLabel(new ImageIcon("tree.png"));
				border.add(tree);
			}
		}
		
		
		if (direction == "NORTH") {
			border.setLayout(new GridLayout(2, 28, 0, 0));
			for(int i = 0; i< 28; i++){
				JLabel empty = new JLabel("");
				border.add(empty);
			}
			for (int i = 28; i < 56; i++) {
				JLabel tree = new JLabel(new ImageIcon("tree.png"));
				border.add(tree);
			}
		}
		
		
		
		
		
	}
	
	private void topBar(JPanel top) {
		top.setBackground(Color.BLACK);
		top.setLayout(new GridLayout(1, 4, 5, 0));
		JLabel powerups = new JLabel("Powerups/ Powerdowns: ");
		JLabel time = new JLabel("Time: ");
		JLabel score = new JLabel("Score: ");
		JLabel lives = new JLabel("Lives left:");
		powerups.setForeground(Color.WHITE);
		time.setForeground(Color.WHITE);
		score.setForeground(Color.WHITE);
		lives.setForeground(Color.WHITE);
		lives.setHorizontalAlignment(JLabel.CENTER);
		score.setHorizontalAlignment(JLabel.CENTER);
		time.setHorizontalAlignment(JLabel.CENTER);
		powerups.setHorizontalAlignment(JLabel.CENTER);
		top.add(powerups);
		top.add(time);
		top.add(score);
		top.add(lives);
		add(top, BorderLayout.NORTH);
	}


	public test(){
		
		setSize (1024, 768);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//getContentPane().setBackground(Color.GREEN);
		
		JLabel grass = new JLabel(new ImageIcon("grass1.png"));
		add(grass);
		
		
		//create the different sides of the storyboard - the trees that define the area
		JPanel borderEast = new JPanel();
		levelBorder(borderEast, "EAST");
		JPanel borderWest = new JPanel();
		levelBorder(borderWest, "WEST");
		JPanel borderSouth = new JPanel();
		levelBorder(borderSouth, "SOUTH");
		JPanel borderNorth = new JPanel();
		levelBorder(borderNorth, "NORTH");
		
		
		
		//displays the score, time, lives etc.
		//JPanel topInformationBar = new JPanel();
		//topBar(topInformationBar);
		
		
		
		setVisible(true);
	}

	public static void main(String[] arguments){
		new test();
	}
}

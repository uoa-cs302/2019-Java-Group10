import javax.swing.*;
import java.awt.*;
import java.io.*;


public class test extends JFrame {

	//this function creates JPanels that create the boundary of the level in the form of trees 
	private void levelBorder(JPanel border, String direction){
		
		border.setBackground(new Color(56,187,77));
		
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
			border.setLayout(new GridLayout(15, 10, 0, 0));
			for(int i = 0; i< 15; i++){
				JLabel tree = new JLabel(new ImageIcon("tree.png"));
				border.add(tree);
			}
		}
		if ((direction == "NORTH") || (direction == "SOUTH")) {
			border.setLayout(new GridLayout(1, 25, 0, 0));
			for(int i = 0; i< 25; i++){
				JLabel tree = new JLabel(new ImageIcon("tree.png"));
				border.add(tree);
			}
		}
		
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
		JPanel borderNorth = new JPanel();
		levelBorder(borderNorth, "NORTH");
		JPanel borderSouth = new JPanel();
		levelBorder(borderSouth, "SOUTH");
		
		setVisible(true);
	}

	public static void main(String[] arguments){
		new test();
	}
}

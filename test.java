import javax.swing.*;
import java.awt.*;

public class test extends JFrame{
	public test(){

		super("TestLabel");
		setSize (300, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel pageLabel = new JLabel("This is my label. Do not touch. OK");
		FlowLayout flo = new FlowLayout(-1, 50, 50);
		setLayout(flo);
		add(pageLabel);
		setVisible(true);
		
	}
	public static void main(String[] arguments){
		test testLabel = new test();
	}
}

import java.awt.Dimension;

import javax.swing.JFrame;

//create game loop window
public class Window {

	public Window(int w, int h, String t, Game g) {
		
		JFrame frame = new JFrame(t);
		frame.setPreferredSize(new Dimension(w, h));
		frame.setMaximumSize(new Dimension(w, h));
		frame.setMinimumSize(new Dimension(w, h));
		
		frame.add(g);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}

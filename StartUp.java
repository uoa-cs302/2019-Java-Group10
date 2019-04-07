import javax.swing.*;

public class StartUp {

	public static void main(String[] args) {
		// create frame for ShapesJPanel
		JFrame frame = new JFrame("Level 1" );
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

		// create ShapesJPanel
		movement movementJPanel = new movement();
		frame.add( movementJPanel ); // add shapesJPanel to frame
		frame.setSize( 825, 600 ); // set frame size
		frame.setVisible( true ); // display frame
	}

}

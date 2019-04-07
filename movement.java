import java.awt.Color;
import java.awt.Graphics;
import java.awt.BasicStroke;
import java.awt.GradientPaint;
import java.awt.TexturePaint;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class movement extends JPanel {
	// draw shapes with Java 2D API
	public void paintComponent( Graphics g ) {
		super.paintComponent( g ); // call superclass's paintComponent

		Graphics2D g2d = ( Graphics2D ) g; // cast g to Graphics2D

		// draw 2D ellipse filled with a blue-yellow gradient
		g2d.setPaint( new GradientPaint( 5, 30, Color.BLUE, 35, 100, Color.YELLOW, true ) );
		g2d.fill( new Ellipse2D.Double( 5, 30, 65, 100 ) );
	
		
	} // end method paintComponent
}

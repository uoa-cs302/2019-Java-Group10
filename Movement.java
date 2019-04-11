//Bodnar, J (2018). Moving Sprites (modified version)
//[Source code]. http://zetcode.com/tutorials/javagamestutorial/movingsprites/

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Movement extends JPanel implements ActionListener {

    private Timer timer;
    private ZeldaCharacter zeldacharacter;
    private final int DELAY = 10;

    public Movement() {
        initMovement();
    }

    private void initMovement() {
    	
        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        zeldacharacter = new ZeldaCharacter();

        timer = new Timer(DELAY, this);
        timer.start();
    }

	//@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
        
        Toolkit.getDefaultToolkit().sync();
    }
    
    private void doDrawing(Graphics g) {
        
        Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(zeldacharacter.getImage(), zeldacharacter.getX(), zeldacharacter.getY(), this);
    }
    
    //@Override
    public void actionPerformed(ActionEvent e) {
        
        step();
    }
    
    private void step() {
        
        zeldacharacter.move();
        
        repaint(zeldacharacter.getX()-1, zeldacharacter.getY()-1, 
                zeldacharacter.getWidth()+2, zeldacharacter.getHeight()+2);     
    }    

    private class TAdapter extends KeyAdapter {

        //@Override
        public void keyReleased(KeyEvent e) {
            zeldacharacter.keyReleased(e);
        }

        //@Override
        public void keyPressed(KeyEvent e) {
            zeldacharacter.keyPressed(e);
        }
    }
}

/*
private void doDrawing(Graphics g) {
    
    Graphics2D g2d = (Graphics2D) g;
    
    g2d.drawImage(ship.getImage(), ship.getX(), ship.getY(), this);
}
In the doDrawing() method, we draw the spaceship with the drawImage() method. We get the image and the coordinates from the sprite class.

@Override
public void actionPerformed(ActionEvent e) {
    
    step();
}
The actionPerformed() method is called every DELAY ms. We call the step() method.

private void step() {
    
    ship.move();
    repaint(ship.getX()-1, ship.getY()-1, 
            ship.getWidth()+2, ship.getHeight()+2);     
}    
We move the sprite and repaint the part of the board that has changed. We use a small optimisation technique that repaints only the small area of the window that actually changed.


private class TAdapter extends KeyAdapter {

    @Override
    public void keyReleased(KeyEvent e) {
        craft.keyReleased(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        craft.keyPressed(e);
    }
}
*/

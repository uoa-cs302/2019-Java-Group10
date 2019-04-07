//Bodnar, J (2018). Moving Sprites (modified version)
//[Source code]. http://zetcode.com/tutorials/javagamestutorial/movingsprites/


import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

public class ZeldaCharacter {

    private int dx;
    private int dy;
    private int x = 10;
    private int y = 10;
    private int w;
    private int h;
    private Image image;

    public ZeldaCharacter() {

        loadImage();
    }

    private void loadImage() {
        
        ImageIcon ii = new ImageIcon("archer.jpg");
        image = ii.getImage(); 
        
        w = image.getWidth(null);
        h = image.getHeight(null);
    }

    public void move() {
        
        x += dx;
        y += dy;
    }

    public int getX() {
        
        return x;
    }

    public int getY() {
        
        return y;
    }
    
    public int getWidth() {
        
        return w;
    }
    
    public int getHeight() {
        
        return h;
    }    

    public Image getImage() {
        
        return image;
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = -1;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 1;
        }

        if (key == KeyEvent.VK_UP) {
            dy = -1;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 1;
        }
    }

    public void keyReleased(KeyEvent e) {
        
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_UP) {
            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 0;
        }
    }
}

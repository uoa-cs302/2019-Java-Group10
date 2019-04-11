//Bodnar, J (2018). Moving Sprites (modified version)
//[Source code]. http://zetcode.com/tutorials/javagamestutorial/movingsprites/

import java.awt.EventQueue;
import javax.swing.JFrame;

public class StartUp extends JFrame {
	
    public StartUp() {
        initUI();
    }
    
    private void initUI() {

        add(new Movement());

        setTitle("Level 1");
        setSize(1020, 800);
        
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            StartUp su = new StartUp();
            su.setVisible(true);
        });
    }
}

import javax.swing.*;
import java.awt.*;
//testComment

public class Home extends JFrame{
	public Home(){
		super("Zelda Reborn");
		setSize (1024, 768);

		JFrame frame = new JFrame();


		ImageIcon backgroundImage = new ImageIcon(getClass().getResource("ZeldaBackground.jpg"));
		Image background1 = backgroundImage.getImage();
		//rescale image to fit dimensions of the frame
		Image background2 = background1.getScaledInstance(1024, 768, Image.SCALE_DEFAULT);
		ImageIcon background3 = new ImageIcon(background2);
		JLabel backgroundPicture = new JLabel(background3);


		backgroundPicture.setLayout(new BorderLayout());
 		
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel pageLabel = new JLabel("ZELDA Reborn");

			
		pageLabel.setFont(new Font("Arial", Font.PLAIN, 75));
		pageLabel.setForeground(Color.WHITE);
		//backgroundPicture.add(pageLabel);
		
		//FlowLayout flo = new FlowLayout(1, 50, 50);
		//pageLabel.setLayout(flo);

		backgroundPicture.add(pageLabel, BorderLayout.NORTH);
		
		add(backgroundPicture);		

		setResizable(false);
		setVisible(true);
		
	}
	public static void main(String[] arguments){
		Home background = new Home();
	}
}

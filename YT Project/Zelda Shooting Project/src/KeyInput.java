import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

	Handler handler;
	Game game;
	
	boolean paused = false;
	
	//dont create new handler as you we want to use handler instance created from before
	public KeyInput (Handler handler, Game game) {
		this.handler = handler;
		this.game=game;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
				
		//game paused (toggle on paused variable)
		if (key == KeyEvent.VK_P) {
			if (paused == false) {
				game.stop();
				paused = true;
			}
			else {
				game.start();
				paused = false;
			}
		}
		
		//loops through all objects
		for (int i = 0; i < handler.obj.size(); i ++) {
			GameObject temp = handler.obj.get(i);
			//finds player object
			if (temp.getId() == ID.Player) {
				if (key == KeyEvent.VK_UP) {
					handler.setUp(true);
				}
				if (key == KeyEvent.VK_DOWN) {
					handler.setDown(true);
				}
				if (key == KeyEvent.VK_LEFT) {
					handler.setLeft(true);
				}
				if (key == KeyEvent.VK_RIGHT) {
					handler.setRight(true);
				}		
			}
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		//loops through all objects
		for (int i = 0; i < handler.obj.size(); i ++) {
			GameObject temp = handler.obj.get(i);
			//finds player object
			if (temp.getId() == ID.Player) {
				if (key == KeyEvent.VK_UP) {
					handler.setUp(false);
				}
				if (key == KeyEvent.VK_DOWN) {
					handler.setDown(false);
				}
				if (key == KeyEvent.VK_LEFT) {
					handler.setLeft(false);
				}
				if (key == KeyEvent.VK_RIGHT) {
					handler.setRight(false);
				}
				
//				if (key == KeyEvent.VK_P) {
//					System.out.println("P released");
//					game.start();
//				}
				
			}
		}	
	}
}

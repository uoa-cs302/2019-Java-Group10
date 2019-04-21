import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter {
	
	private Camera camera;
	private Handler handler;
	private Game game;
	private SpriteSheet ss;
	
	
	public MouseInput(Handler handler, Camera camera, Game game, SpriteSheet ss) {
		this.handler=handler;
		this.camera=camera;
		this.game=game;
		this.ss = ss;
	}
	
	public void mousePressed (MouseEvent e) {
		int mouseX = (int) (e.getX() + camera.getX());
		int mouseY = (int) (e.getY() + camera.getY());
		
		for (int i=0; i < handler.obj.size(); i++) {
			GameObject temp = handler.obj.get(i);
			
			if(temp.getId() == ID.Player && game.ammo >= 1) {
				//change 16 and 24 if needed. Used to get bullet a little away from Player object
				handler.addObj(new Arrow(temp.getX() + 16, temp.getY() +24, ID.Arrow, handler, mouseX, mouseY, ss));
				game.ammo--;   //subtracting an ammo every time we click
			}
		}
	}

}

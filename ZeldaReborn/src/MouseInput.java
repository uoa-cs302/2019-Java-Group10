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
		//finding mouse position with respect to current position of screen
		int mouseX = (int) (e.getX() + camera.getX());
		int mouseY = (int) (e.getY() + camera.getY());
		
		for (int i=0; i < handler.obj.size(); i++) {
			GameObject temp = handler.obj.get(i);
			
			int a =game.getammo();
			if(temp.getId() == ID.Player && a >= 1) {
				handler.addObj(new Arrow(temp.getX() + 16, temp.getY() +24, ID.Arrow, handler, mouseX, mouseY, ss));
				//game.ammo--;   //subtracting an ammo every time we click
				int b=a-1;
				game.setammo(b);
			}
		}
	}

}
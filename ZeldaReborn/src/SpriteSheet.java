import java.awt.image.BufferedImage;

public class SpriteSheet {

	private BufferedImage image;
	
	public SpriteSheet(BufferedImage image) {
		this.image = image;
	}
	
	//grabs specific image from spritesheet with different pixel width and height properties
	public BufferedImage grabImage(int col, int row, int w, int h) {
		return image.getSubimage((col*32) -32, (row*32) - 32, w, h);
	}
	public BufferedImage grabBigImage(int col, int row, int w, int h) {
		return image.getSubimage((col*60) -60, (row*60) - 60, w, h);
	}
	public BufferedImage grabMediumImage(int col, int row, int w, int h) {
		return image.getSubimage((col*50) -50, (row*50) - 50, w, h);
	}
}
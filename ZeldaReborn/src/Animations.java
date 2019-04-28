import java.awt.Graphics;
import java.awt.image.BufferedImage;

//source code: retrieved from https://www.youtube.com/watch?v=BhQ9mMCZTHM&list=PLWms45O3n--5vDnNd6aiu1CSWX3JlCU1n&index=12

//Class which runs through each frame of an object, and is used for display.
public class Animations {

	//14 frame animation
	private BufferedImage[] img = new BufferedImage[14];
	private BufferedImage currentImg;
	
	private int speed;
	private int frames;
	private int index = 0;
	private int count = 0;

	public Animations(int s, BufferedImage[] img){
		speed = s;
		for(int i = 0; i < img.length; i++) {
			this.img[i] = img[i];
		}
		frames = img.length;
	}

	public void runAnimation(){
		index++;
		if(index > speed){
			index = 0;
			nextFrame();
		} 
	}

	public void nextFrame(){

		for(int i = 2; i < img.length; i++) {
			if(frames == i) {
				for(int j = 0; j < i; j++) {
					if(count == j)
						currentImg = img[j];
				}
				count++;
				if(count > frames)
					count = 0;
				break;
			}
		}
	}

	public void drawAnimation(Graphics g, double x, double y, int offset){
		g.drawImage(currentImg, (int)x - offset, (int)y, null);
	}

	public void setCount(int c){
		count = c;
	}
	public int getCount(){
		return count;
	}
	public int getSpeed(){
		return speed;
	}
	public void setSpeed(int s){
		speed = s;
	}
}
package resources;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Sprite extends Entity{
	
	public Rectangle cClip;
	
	private boolean dOLeft,cOLeft;
	private Animation currentAnim;
	private HashMap<String, Animation> anims;
	
	
	public Sprite(BufferedImage img, TSI tsi){
		super.init(img);
		dOLeft = tsi.left;
	}
	
	
	
	public void changeAnimation(String s){
		currentAnim = anims.get(s);
		currentAnim.start();
	}	

	@Override
	public void update(long timePassed) {
		currentAnim.update(timePassed);
	}

	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object clone() {
		// TODO Auto-generated method stub
		return null;
	}
	
}

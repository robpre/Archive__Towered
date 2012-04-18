package resources;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Sprite extends Entity{
	
	private boolean left;
	private Animation[] a;
	
	public Sprite(BufferedImage img, TSI tsi){
		super.init(img);
	}
	

	@Override
	public void update(long timePassed) {
		// TODO Auto-generated method stub
		
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

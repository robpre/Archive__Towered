package resources;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class Entity {
	public BufferedImage img;
	public int x,y;
	public double dx, dy;
	public Entity(){
		
	}
	public void update(){
		
	}
	public void draw(Graphics2D g){
		g.drawImage(img, x, y, null);
	}
}
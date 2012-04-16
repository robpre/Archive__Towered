package resources;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class Entity {	
	public int x,y;
	public double dx, dy;	
	public BufferedImage img;
	
	public void init(BufferedImage img){
		this.img = img;
	}
	
	public void setX(int x){
		this.x=x;
	}
	public void setY(int y){
		this.y=y;
	}
	
	public void setPos(int x,int y){
		this.x=x;
		this.y=y;
	}
	
	public abstract void update(long timePassed);
	
	public abstract void draw(Graphics2D g);
	
	public abstract Object clone();
}
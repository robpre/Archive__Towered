package resources;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public abstract class Entity {	
	public int x,y;	
	public BufferedImage img;
	public boolean clickable;
	public Rectangle click;
	public String sceneName;
	public float speed;
	public String type;
	
	public void init(BufferedImage img){
		clickable=false;
		this.img = img;
		speed=0;
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
	
	public abstract Rectangle getClipping();
	
	public abstract void update(long timePassed);
	
	public abstract void draw(Graphics2D g);
	
	public abstract Object clone();
}
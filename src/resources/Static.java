package resources;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Static extends Entity{
	
	public Rectangle scene;
	private HashMap<String, Rectangle> scenes;
	
	public Static(BufferedImage img, TSI tsi){
		super.init(img);
		scene = new Rectangle(0,0,0,0);
		scenes = new HashMap<String, Rectangle>();
		scenes = tsi.tiles;
		type = "s";
	}
	
	public Static(BufferedImage img, HashMap<String, Rectangle> list){
		super.init(img);
		scene = new Rectangle(0,0,0,0);
		scenes = new HashMap<String, Rectangle>();
		scenes = list;
	}
	
	public void clickable(boolean b){
		clickable=b;
		if(clickable)
			click = new Rectangle(x, y, scene.width, scene.height);
		else
			click = null;
	}
	
	public BufferedImage getImage(){
		return img.getSubimage(scene.x, scene.y, scene.width, scene.height);
	}
	
	public void changeScene(String s){
		scene = scenes.get(s);
		sceneName = s;
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.drawImage(getImage(), x, y, null);
	}
	
	@Override
	public void update(long timePassed) {
		if(clickable && click.equals(null)){
			click = new Rectangle(x, y, scene.width, scene.height);
		}
	}

	@Override
	public Object clone() {
		Static st = new Static(img, scenes);
		st.setX(x);
		st.setY(y);
		st.clickable(clickable);
		st.type = type;
		return st;
	}
}

package resources;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;

public class Sprite{
	
	public Rectangle cClip;
	public boolean defaultOrientationLeft,isFacingLeft;
	
	private Animation currentAnim;
	private int w,h;
	private HashMap<String, Animation> anims;
	
	
	public Sprite(TSI tsi, int srcW, int srcH){
		anims = new HashMap<String, Animation>();
		defaultOrientationLeft = tsi.left;
		w = tsi.w;
		h = tsi.h;
		if(tsi.count!=tsi.tiles.size())
			System.out.println("Warning: the count in tsi:" + tsi.name + " does not equal the number of extracted rectangles: " + tsi.tiles.size());
		String d = "null";
		int i,j,k;
		i=j=k=0;
		while(tsi.tiles.size() > 0){
			String name = "name";
			Rectangle b = new Rectangle();
			for(Map.Entry<String, Rectangle> entry: tsi.tiles.entrySet()){
				if(entry.getKey().equals(i + "_" + strip(entry.getKey()))){
					name = entry.getKey();
					b = entry.getValue();
					break;
				}
			}
			if(!anims.containsKey(strip(name)))
				anims.put(strip(name), new Animation());
			anims.get(strip(name)).add(new Point(j * w,k * h), 150, b);
			j++;
			if(j>=srcW/w){
				j=0;
				k++;
			}
			if(k>=srcH/h){
				System.out.println("Fatal error: image src for:" + tsi.name + "is incorrect, image wrong or tsi wrong.");
			}
			tsi.tiles.remove(name);
			i++;
			d = strip(name);
		}
		changeAnimation(d);
	}
	
	private String strip(String s){
		return s.substring(s.indexOf("_")+1);
	}
	
	public HashMap<String, Animation> getAnimations(){
		return anims;
	}
	
	public boolean animDone(){
		return currentAnim.getDone();
	}
	
	public void changeAnimation(String s){
		//deal with if character is already animating with the changed anim
		currentAnim = anims.get(s);		
		currentAnim.start();
	}	
	
	public Rectangle getImageRect(){
		return new Rectangle(currentAnim.getPoint().x, currentAnim.getPoint().y, w, h);
	}
	
	public Rectangle getClipping(){
		return currentAnim.getClip();
	}
	
	public void update(long timePassed){
		currentAnim.update(timePassed);
	}
}

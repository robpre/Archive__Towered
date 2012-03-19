package resources;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Properties;

public class Map {
	public String NAME, DESCRIPTION;
	private Properties map;
	private ArrayList<Rectangle> bounds;
	private ArrayList<BufferedImage> tiles;
	
	public Map(Properties p){
		map = (Properties)p.clone();
		if (map.getProperty("map.opt").equals(null))
			opt();
	}

	private void opt() {
		
	}
	
	public ArrayList<Rectangle> optimiseHorizon(ArrayList<Rectangle> b){
		int counter = 0;
		while(counter<b.size()-1){
			Rectangle r = b.get(counter);
			Rectangle r2= b.get(counter+1);
			int first = r.x + r.width;
			if(first == r2.x && r.height == r2.height){
				b.set(counter, addRectWidth(r,r2));
				b.remove(r2);
			} else {
				counter++;
			}
		}
		return b;
	}
	public ArrayList<Rectangle> optimiseVertical(ArrayList<Rectangle> b){
		int count = 0;
		int count2 = count+1;
		while(count<b.size()){
			Rectangle r = b.get(count);
			Rectangle r2= b.get(count2);
			System.out.format("Checking %s with %s\nb.size(): %s\n", count, count2, b.size());
			if(r.y + r.height == r2.y && r.width == r2.width && r.x == r2.x){
				b.set(count, addRectHeight(r,r2));
				b.remove(count2);
			} else if(count<b.size()&&count2+1<b.size()){
				count2++;
			} 
			if(count2+1>=b.size()){
				count++;
				count2=count+1;
			}
			if(count2>=b.size())
				break;
		}
		return b;
	}

	public Rectangle addRectWidth(Rectangle r, Rectangle r2){
		return new Rectangle(r.x,
				r.y,
				r.width + r2.width,
				r.height);
	}
	public Rectangle addRectHeight(Rectangle r, Rectangle r2){
		return new Rectangle(r.x,
				r.y,
				r.width,
				r.height + r2.height);
	}
}

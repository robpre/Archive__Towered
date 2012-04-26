package resources;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Properties;

public class Map {
	
	public ArrayList<Rectangle> clipping;
	private Point[][] tiles;
	private BufferedImage res;
	
	public Map(Properties p, TSI tsi, BufferedImage res){
		this.res=res;
		clipping = new ArrayList<Rectangle>();
		int w = Integer.parseInt(p.getProperty("system.width"));
		int h = Integer.parseInt(p.getProperty("system.height"));
		ArrayList<Point> srcPoints = new ArrayList<Point>();
		int resW = res.getWidth() / tsi.w;
		int resH = res.getHeight() / tsi.h;
		for(int c=0;c<resW;c++){
			for(int r=0;r<resH;r++){
				if(srcPoints.size()>=tsi.count)
					break;
				srcPoints.add(new Point(r * tsi.h,c * tsi.w));
			}
		}
		tiles = new Point[w][h];
		for(int r = 0, i=0;r < h;r++){
			for(int c=0;c < w;c++){
				int j = Integer.parseInt(p.getProperty("tile" + i + ".num"));
				Rectangle rect = tsi.tiles.get(String.valueOf(j));
//				System.out.println("Setting point  : " + c + "," + r);
//				System.out.println("Pixel locatio  : " + ((tsi.w * c)) + "," + ((tsi.h * r)));
//				System.out.println("Original Rec   : " + rect.toString());
//				System.out.println("Clipping coords: " + ((tsi.w * c)+rect.x) + "," + ((tsi.h * r)+rect.y));
				if(check(rect)){					
					rect.setLocation(rect.x + (tsi.w * c),
							rect.y + (tsi.h * r));
					clipping.add(rect);
				}
				tiles[c][r] = srcPoints.get(j);
				i++;
			}
		}
		System.out.println(clipping.size());
		opt();
	}
	
	private boolean check(Rectangle r){
		if(r.width == 0|| r.height == 0)
			return false;
		return true;
	}
	private void opt(){
		clipping = optimiseHorizon(clipping);
		clipping = optimiseVertical(clipping);	
		
	}
	private ArrayList<Rectangle> optimiseHorizon(ArrayList<Rectangle> b){
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
	private ArrayList<Rectangle> optimiseVertical(ArrayList<Rectangle> b){
		int count = 0;
		int count2 = count+1;
		while(count<b.size()){
			Rectangle r = b.get(count);
			Rectangle r2= b.get(count2);
			//System.out.format("Checking %s with %s\nb.size(): %s\n", count, count2, b.size());
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
	private Rectangle addRectWidth(Rectangle r, Rectangle r2){
		return new Rectangle(r.x,
				r.y,
				r.width + r2.width,
				r.height);
	}
	private Rectangle addRectHeight(Rectangle r, Rectangle r2){
		return new Rectangle(r.x,
				r.y,
				r.width,
				r.height + r2.height);
	}
}

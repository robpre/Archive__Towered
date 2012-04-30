package resources;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Properties;

public class Map {
	
	public ArrayList<Rectangle> clipping;
	public String description;
	private BufferedImage map;
	public int x,y;
	
	public Map(Properties p, TSI tsi, BufferedImage res, String des){
		description = des;
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
		map = new BufferedImage(tsi.w * w,tsi.h * h,BufferedImage.TYPE_INT_ARGB);
		for(int r = 0, i=0;r < h;r++){
			for(int c=0;c < w;c++){
				int j = Integer.parseInt(p.getProperty("tile" + i + ".num"));
				int mapX = (tsi.w * c);
				int mapY = (tsi.h * r);
				Rectangle rect = (Rectangle) tsi.tiles.get(String.valueOf(j)).clone();
				if(!rect.isEmpty()){					
					rect.setLocation(rect.x + mapX,rect.y + mapY);
					clipping.add(rect);
				}
				map.getGraphics().drawImage(res.getSubimage(srcPoints.get(j).x, srcPoints.get(j).y, tsi.w,tsi.h),
						mapX,
						mapY,			
						null);
				i++;
			}
		}		
		opt();
	}
	public Map(BufferedImage m){
		map = m;
	}
	
	public void draw(Graphics2D g){
		g.drawImage(map, x, y, null);
		//drawClipping(g);
	}
	public void drawClipping(Graphics2D g){
		g.setColor(new Color((float)1, (float)0, (float)0, (float)0.5));
		for(Rectangle r: clipping){
			g.fill(r);
		}
	}
	
	@Override
	public Map clone(){
		Map m = new Map(map);
		m.setPos(x, y);
		m.setClipping(clipping);
		m.setDescription(description);
		return m;
	}
	
	private void setPos(int x, int y){
		this.x = x;
		this.y = y;
	}
	private void setClipping(ArrayList<Rectangle> clipping){
		this.clipping = clipping;
	}	
	private void setDescription(String description) {
		this.description = description;
	}
	//optimising functions
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

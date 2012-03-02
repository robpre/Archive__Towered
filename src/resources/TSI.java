package resources;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class TSI {
	private String 	type,
					name,
					regex;
	private int w,
				h,
				count;
	private HashMap<String, Rectangle> charTile;
	private ArrayList<Rectangle> mapTile;
	
	public TSI(Properties p){
		name = p.getProperty("System.source");
		type = p.getProperty("type");
		regex = p.getProperty("System.regex");
		w = Integer.parseInt(p.getProperty("tile.size.w"));
		h = Integer.parseInt(p.getProperty("tile.size.h"));
		count = Integer.parseInt(p.getProperty("tile." + type + ".count"));
		if(type.equals("map"))
			map(p);
		else
			chararcter(p);
	}

	private void chararcter(Properties p) {
		charTile = new HashMap<String,Rectangle>();
	}

	private void map(Properties p) {
		mapTile = new ArrayList<Rectangle>();	
	}
	
	private Rectangle conv(String s){
		String info[] = s.split(regex);
		if(info.length!=4)
			return null;
		Rectangle r = new Rectangle(
				Integer.parseInt(info[0]),
				Integer.parseInt(info[1]),
				Integer.parseInt(info[2]),
				Integer.parseInt(info[3]));
		return r;
	}
	private String conv(Rectangle r){
		String s = r.x + "#" + r.y + "#" + r.width + "#" + r.height;
		return s;
	}
	
	public Rectangle get(int i){
		if(mapTile.size()-1<i)
			return null;
		else
			return mapTile.get(i);
	}
	public Rectangle get(String s){
		return null;
	}
}

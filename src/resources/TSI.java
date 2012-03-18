package resources;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class TSI {
	private String 	type,
					res,
					regex;
	private int w,
				h,
				count;
	private HashMap<String, Rectangle> charTile;
	private ArrayList<Rectangle> mapTile;
	
	public TSI(Properties p){
		res = p.getProperty("system.source");
		type = p.getProperty("system.type");
		if(type.equals("map"))
			map(p);
		else if(type.equals("char"))
			chararcter(p);
		else
			staticImp(p);
	}

	private void chararcter(Properties p) {

	}

	private void map(Properties p) {

	}
	
	private void staticImp(Properties p){
		
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
package resources;

import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Properties;

public class TSI {
	public String 	type,
					res,
					regex;
	public int w,
				h,
				count;
	private HashMap<String, Rectangle> tiles; //Contains each tile: name, bounds info
	private HashMap<String, Integer> cha; // for characters i will store the number of each tile that corresponds to an animation
	
	public TSI(Properties p){
		type = p.getProperty("system.type");
		res = type + "/" + p.getProperty("system.source");
		count = Integer.parseInt(p.getProperty("system.count"));
		w = Integer.parseInt(p.getProperty("system.width"));
		h = Integer.parseInt(p.getProperty("system.height"));
		regex = p.getProperty("system.regex");
		switch(res.charAt(0)){
		case 's':
			misc(p);
			break;
		case 't':
			tile(p);
			break;
		case 'c':
			character(p);
			break;
		}
	}

	private void character(Properties p) {
		tiles = new HashMap<String, Rectangle>();
		cha = new HashMap<String, Integer>();
		for(int i=0, j=0;i<count;i++){
			String name = p.getProperty(type + i + ".name");
			Rectangle bounds = conv(p.getProperty(type + i + ".bounds"));
			if(cha.containsKey(name))
				j++;
			else
				j=0;			
			cha.put(name, j);		
			tiles.put(name + j, bounds);
		}
	}

	private void tile(Properties p) {
		tiles = new HashMap<String, Rectangle>();
		for(int i=0;i<count;i++){
			Rectangle bounds;
			if(p.getProperty(type + i + ".bounds").equals("0"))
				bounds = new Rectangle(0,0,0,0);
			else if (p.getProperty(type + i + ".bounds").equals("1"))
				bounds = new Rectangle(0,0,w,h);
			else
				bounds = conv(p.getProperty(type + i + ".bounds"));
			tiles.put(String.valueOf(i), bounds);
		}
	}
	
	private void misc(Properties p){
		tiles = new HashMap<String, Rectangle>();
		for(int i=0;i<count;i++){
			tiles.put(p.getProperty(type + i + ".name"), conv(p.getProperty(type + i + ".bounds")));
		}
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
}
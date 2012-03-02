package resources;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Properties;

public class Resources {
	
	private Hashtable<String, Map> mapList;
	private Hashtable<String, Character> charList;
	private Hashtable<String, BufferedImage> imageList;
	private Hashtable<String, Animation> animList;
	
	public Resources(){
		mapList = new Hashtable<String, Map>();
		charList = new Hashtable<String, Character>();
		imageList = new Hashtable<String, BufferedImage>();
		animList = new Hashtable<String, Animation>();
	}
	
	/*
	 * this private class will be used as a data store to store game information
	 * this is:
	 * 		-Number of playable characters available to play
	 * 		-The name and location of character info
	 * 		-Number of maps that exist
	 * 		-name and locations of maps
	 */
	private class Data{
		public int characterCount,mapCount;
		private DataO[] characters;
		private DataO[] maps;
		public Data(Properties p){
			mapCount = Integer.parseInt(p.getProperty("maps.count"));
			characterCount = Integer.parseInt(p.getProperty("char.count"));
			maps = new DataO[mapCount];
			characters = new DataO[characterCount];
			for(int i=0;i<mapCount;i++){
				maps[i] = new DataO(
					p.getProperty("level." + i + ".name"),
					p.getProperty("level." + i + ".res"),
					p.getProperty("level." + i + ".description"));
			}
			for(int i=0;i<characterCount;i++){
				characters[i] = new DataO(
					p.getProperty("char." + i + ".name"),
					p.getProperty("char." + i + ".res"),
					p.getProperty("char." + i + ".description"));
			}
		}
	}
	private class DataO{
		public String 	name,
						resourceLocation,
						description;
		public DataO(String name, String resLoc, String des){
			this.name=name;
			resourceLocation = resLoc;
			description = des;
		}
	}
}

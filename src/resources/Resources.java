package resources;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Properties;

public class Resources {
	
	private HashMap<String, BufferedImage> staticImages;
	private HashMap<String, Map> maps;
	private HashMap<String, Character> chars;
	
	public Resources(){
		//stream interaction
		//gather resources
		//Initialise cache
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
		int characterCount,mapCount;
		DataO[] characters;
		DataO[] maps;
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

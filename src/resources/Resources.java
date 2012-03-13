package resources;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import towered.Settings;

public class Resources {
	
	public Settings SETTINGS;
	
	private Streams stream;
	private HashMap<String, Properties> properties;
	private Data d;
	private HashMap<String, BufferedImage> staticImages;
	private HashMap<String, Map> maps;
	private HashMap<String, Character> chars;
	private String settingsLoc;
	
	public Resources(Settings s){
		SETTINGS = s;
		
		settingsLoc = System.getenv("appdata") + "\\" + s.GAMENAME + "\\game.settings";
		stream = new Streams();		
		properties = new HashMap<String, Properties>();
		staticImages = new HashMap<String, BufferedImage>();
		
		properties.put("resData", getIntP("res.data"));
		properties.put("settings", getExtP(settingsLoc));
		
		d = new Data(properties.get("resData"));
		

		SETTINGS = getSettings();
		System.out.println(SETTINGS.print());
		//stream interaction
		//gather resources
		//Initialise cache
	}
	
	private Settings getSettings(){
		System.out.println(SETTINGS.print());
		return new Settings( // Settings(String gn, String q, int w, int h, int j, int l, int r, int a)
					SETTINGS.GAMENAME,
					properties.get("settings").getProperty("res.quality"),
					Integer.parseInt(properties.get("settings").getProperty("screen.width")),
					Integer.parseInt(properties.get("settings").getProperty("screen.height")),
					Integer.parseInt(properties.get("settings").getProperty("user.jump")),
					Integer.parseInt(properties.get("settings").getProperty("user.left")),
					Integer.parseInt(properties.get("settings").getProperty("user.right")),
					Integer.parseInt(properties.get("settings").getProperty("user.attack"))
				);
	}
	
	private Properties getIntP(String s){
		Properties pBin = new Properties();
		try {
			pBin.load(stream.intResource(s));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return pBin;
	}
	
	private Properties getExtP(String s){
		Properties pBin = new Properties();
		try {
			pBin.load(stream.extResource(s));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return pBin;
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
		int characterCount,mapCount,miscCount;
		DataO[] characters, maps, misc;
		public Data(Properties p){
			mapCount = Integer.parseInt(p.getProperty("maps.count"));
			characterCount = Integer.parseInt(p.getProperty("char.count"));
			miscCount = Integer.parseInt(p.getProperty("misc.count"));
			maps = new DataO[mapCount];
			characters = new DataO[characterCount];
			misc = new DataO[miscCount];
			for(int i=0;i<mapCount;i++){
				maps[i] = new DataO(
					p.getProperty("level" + i + ".name"),
					p.getProperty("level" + i + ".res"),
					p.getProperty("level" + i + ".description"));
			}
			for(int i=0;i<characterCount;i++){
				characters[i] = new DataO(
					p.getProperty("char" + i + ".name"),
					p.getProperty("char" + i + ".res"),
					p.getProperty("char" + i + ".description"));
			}
			for(int i=0;i<miscCount;i++){
				misc[i] = new DataO(
					p.getProperty("misc" + i + ".name"),
					p.getProperty("misc" + i + ".res"),
					p.getProperty("misc" + i + ".description"));
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

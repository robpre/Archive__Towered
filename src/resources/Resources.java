package resources;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import javax.imageio.ImageIO;

import towered.Settings;

public class Resources {
	
	public Settings SETTINGS;
	
	private Streams stream;
	private HashMap<String, Properties> properties;
	private Data d;
	private String settingsLoc;
	public HashMap<String, Static> staticImages;
	public HashMap<String, Map> maps;
	public HashMap<String, Character> chars;
	public HashMap<String, TSI> tileSheets;
	
	
	public Resources(Settings s){
		settingsLoc = System.getenv("appdata") + "\\" + s.GAMENAME + "\\game.settings";
		init(s);
	}
	
	public Resources(Settings s, String sl){
		settingsLoc = sl;
		init(s);
	}
	
	private void init(Settings s){
		properties = new HashMap<String, Properties>();
		staticImages = new HashMap<String, Static>();
		maps = new HashMap<String, Map>();
		chars = new HashMap<String, Character>();
		tileSheets = new HashMap<String, TSI>();
		SETTINGS = s;		
		stream = new Streams();				
		
		properties.put("resData", getIntP("res.data"));
		if(checkFile(settingsLoc)){
			properties.put("settings", getExtP(settingsLoc));
		}else{
			settingsLoc = System.getenv("appdata") + "\\" + s.GAMENAME + "\\game.settings";
			createSettings();
			storeSettings();
			properties.put("settings", getExtP(settingsLoc));
			System.out.println("Could not find settings, creating blank default and saving at:\n" + settingsLoc);
		}
		SETTINGS = getSettings();
		
		d = new Data(properties.get("resData"));
		for(DataO dO: d.misc){
			TSI tsi;
			if((dO.name.equals(null) || dO.name.equals("null")) || (dO.description.equals(null) || dO.description.equals("null")))
				tsi = new TSI(getIntP("misc/" + SETTINGS.QUALITY + "_" + dO.resourceLocation));
			else
				tsi = new TSI(getIntP("misc/" + SETTINGS.QUALITY + "_" + dO.resourceLocation), dO.name, dO.description);
			BufferedImage img = getIntImage(tsi.res);
			staticImages.put(dO.name, new Static(img, tsi));
		}
		for(DataO dO: d.characters){
			TSI tsi;
			if((dO.name.equals(null) || dO.name.equals("null")) || (dO.description.equals(null) || dO.description.equals("null")))
				tsi = new TSI(getIntP("character/" + SETTINGS.QUALITY + "_" + dO.resourceLocation));
			else
				tsi = new TSI(getIntP("character/" + SETTINGS.QUALITY + "_" + dO.resourceLocation), dO.name, dO.description);
			BufferedImage img = getIntImage(tsi.res);
			chars.put(dO.name, new Character(img, tsi));
		}
		for(DataO dO: d.tiles){
			TSI tsi;
			if((dO == null || dO.name.equals("null")) || (dO.description == null || dO.description.equals("null")))
				tsi = new TSI(getIntP("tiles/" + SETTINGS.QUALITY + "_" + dO.resourceLocation));
			else
				tsi = new TSI(getIntP("tiles/" + SETTINGS.QUALITY + "_" + dO.resourceLocation), dO.name, dO.description);
			tileSheets.put(dO.name, tsi);
		}
		for(DataO dO: d.maps){
			 Properties p = getIntP("map/" + dO.resourceLocation);
			 TSI tsi = tileSheets.get(p.getProperty("system.source"));
			 BufferedImage src = getIntImage(tsi.res);
			 Map m = new Map(p, tsi, src, dO.description);
			 maps.put(dO.name, m);
		}
		//stream interaction
		//gather resources
		//Initialise cache
	}
	
	private boolean checkFile(String settingsLoc) {
		try{
			if(new File(settingsLoc).exists())
				return true;
			else return false;
		} catch (NullPointerException e){
			//e.printStackTrace();
			return false;
		}		
	}

	private void createSettings(){
		SETTINGS = new Settings( // Settings(String gn, String q, int w, int h, int j, int l, int r, int a)
				SETTINGS.GAMENAME,
				"860x672",
				860,
				672,
				32,
				65,
				68,
				17
		);
	}
	
	private void storeSettings(){
		try {
			new File(formatSave(settingsLoc)).mkdirs();
			FileOutputStream fos = new FileOutputStream(settingsLoc);			
			SETTINGS.getProps().store(fos, null);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private String formatSave(String s){
		int loc;
		loc = s.lastIndexOf('\\');
		if(loc==-1)
			loc = s.lastIndexOf('/');
		return s.substring(0,loc);
	}
	
	private Settings getSettings(){
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
	
	private BufferedImage getIntImage(String s){
		try{
			return ImageIO.read(stream.intResource(s));
		} catch(IOException e){
			System.out.println("unable to find file: " + s);
			e.printStackTrace();
			return null;
		} catch(IllegalArgumentException e){
			System.out.println("unable to find file: " + s);
			e.printStackTrace();
			return null;
		}
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
		int characterCount,mapCount,miscCount,tileCount;
		DataO[] characters, maps, misc,tiles;
		public Data(Properties p){
			mapCount = Integer.parseInt(p.getProperty("maps.count"));
			characterCount = Integer.parseInt(p.getProperty("char.count"));
			miscCount = Integer.parseInt(p.getProperty("misc.count"));
			tileCount = Integer.parseInt(p.getProperty("tiles.count"));
			maps = new DataO[mapCount];
			characters = new DataO[characterCount];
			misc = new DataO[miscCount];
			tiles = new DataO[tileCount];
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
			for(int i=0;i<tileCount;i++){
				tiles[i] = new DataO(
						p.getProperty("tile" + i + ".name"),
						p.getProperty("tile" + i + ".res"),
						p.getProperty("tile" + i + ".description"));
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

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
	private HashMap<String, Static> staticImages;
	private HashMap<String, Map> maps;
	private HashMap<String, Character> chars;
	private String settingsLoc;
	
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
		SETTINGS = s;		
		stream = new Streams();				
		
		properties.put("resData", getIntP("res.data"));
		if(new File(settingsLoc).exists()){
			properties.put("settings", getExtP(settingsLoc));
		}else{
			settingsLoc = System.getenv("appdata") + "\\" + s.GAMENAME + "\\game.settings";
			createSettings();
			storeSettings();
			properties.put("settings", getExtP(settingsLoc));
		}
		
		
		d = new Data(properties.get("resData"));
		for(DataO dO: d.misc){			
		}

		SETTINGS = getSettings();
		System.out.println(SETTINGS.print());
		//stream interaction
		//gather resources
		//Initialise cache
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
				32
		);
	}
	
	private void storeSettings(){
		try {
			FileOutputStream fos = new FileOutputStream(settingsLoc);
			new File(formatSave(settingsLoc)).mkdirs();
			SETTINGS.getProps().store(fos, null);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private String formatSave(String s){
		int loc;
		loc = s.lastIndexOf('\\');
		System.out.println(loc);
		if(loc==-1)
			loc = s.lastIndexOf('/');
		System.out.println(loc);
		System.out.println(s.substring(0, loc));
		return s.substring(0,loc);
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
		System.out.println(s);
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

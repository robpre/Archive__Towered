package towered;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class CopyOfSettings {		
	////public vars currently only accessible via Settings.varname
	////TODO: IMPLEMENT GET/SET?
	public String GAMENAME = "untitledtdm";
	public String QUALITY;
	public Dimension RESOLUTION;
	public int 	JUMP,
				LEFT,
				RIGHT,
				ATTACK;
	
	//protected private vars.
	private Properties settings;
	private String save;
	
	//Constructor
	public CopyOfSettings(){
		save = System.getenv("appdata") + "\\" + GAMENAME + "\\game.settings";
		System.out.println(save);
		if(!open()){
			createSettings();
			save();
			open();
		}
		load();
	}
	
	/* String getSave()
	 * returns the value of the location where save and settings information
	 * is stored. currently ~AppData~/Roaming/untitledtdm 
	 */
	public String getSave(){
		return save;
	}
	
	/* boolean open()
	 * attempts to load the prop file from storage, returns
	 * true if successful otherwise false.
	 * used to deal with the file not found exception
	 */
	private boolean open() {
		FileInputStream inStream;
			try {
				inStream = new FileInputStream(save);
				settings.load(inStream);
				return true;
		} catch (FileNotFoundException e) {
				e.printStackTrace();
		} catch (IOException e) {
				e.printStackTrace();
		}
		return false;
	}
	
	/* void load()
	 * used to load the information from the prop file settings
	 * settings get loaded into object variables.
	 * currently stored in public vars
	 * TODO:
	 * implement get/set? vars are currently unprotected. 
	 */
	private void load() {
		int w = Integer.parseInt(settings.getProperty("screen.width"));
		int h = Integer.parseInt(settings.getProperty("screen.height"));
		RESOLUTION.setSize(w, h);
		QUALITY = settings.getProperty("res.quality");
		JUMP = Integer.parseInt(settings.getProperty("user.jump"));
		LEFT = Integer.parseInt(settings.getProperty("user.left"));
		RIGHT = Integer.parseInt(settings.getProperty("user.right"));
		ATTACK = Integer.parseInt(settings.getProperty("user.attack"));
	}
	
	/*
	 * void save()
	 * method used to save the current settings prop file, in the
	 * default settings file location
	 * TODO: perhaps make it so you can save it in different 
	 * locations..point? atleast change from a hardcoded location?
	 */
	private void save() {
		try {
			FileOutputStream fos = new FileOutputStream(save);
			settings.store(fos, null);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}
	
	/* void createSettings()
	 * this method is called to create a default settings file
	 * mainly debug purposes, need to create a more eloquent solution 
	 * to the inevitable file not found exception. installation?  
	 * TODO: 
	 * property res.quality is place holder for when i implement 
	 * settings to change screen size/auto screen size. perhaps 
	 * use as name of resource files. eg: 860x672  - default screen size
	 * 							+ file lvl0.860x672.png default resource
	 * 							+ file lvl0.1720x1344.png double quality/res/screen size etc. 
	 */
	private void createSettings() {
		settings.setProperty("screen.width", "860");
		settings.setProperty("screen.height", "672");
		settings.setProperty("res.quality", "860x672");
		settings.setProperty("user.left", Integer.toString(KeyEvent.VK_A));
		settings.setProperty("user.right", Integer.toString(KeyEvent.VK_D));
		settings.setProperty("user.jump", Integer.toString(KeyEvent.VK_SPACE));
		settings.setProperty("user.attack", Integer.toString(KeyEvent.VK_SPACE));
	}
}
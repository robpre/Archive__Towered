package towered;

import java.awt.Dimension;

public class Settings {		
	////public vars currently only accessible via Settings.varname
	////TODO: IMPLEMENT GET/SET?
	public String GAMENAME;
	public String QUALITY;
	public Dimension RESOLUTION;
	public int 	JUMPKEY,
				LEFTKEY,
				RIGHTKEY,
				ATTACKKEY;
	public Settings(String gn){
		GAMENAME = gn;
		RESOLUTION = null;
		QUALITY = null;
		JUMPKEY = 0;
		LEFTKEY = 0;
		RIGHTKEY = 0;
		ATTACKKEY = 0;		
	}
	public Settings(String gn, String q, int w, int h, int j, int l, int r, int a){
		GAMENAME = gn;
		RESOLUTION = new Dimension(w,h);
		QUALITY = q;
		JUMPKEY = j;
		LEFTKEY = l;
		RIGHTKEY = r;
		ATTACKKEY = a;		
	}
	public Settings(String gn, String q, Dimension d, int j, int l, int r, int a){
		GAMENAME = gn;
		RESOLUTION = d;
		QUALITY = q;
		JUMPKEY = j;
		LEFTKEY = l;
		RIGHTKEY = r;
		ATTACKKEY = a;		
	}
	public String print(){
		return 
			GAMENAME + "\n" +
			QUALITY + "\n" +
			RESOLUTION + "\n" +
			JUMPKEY + "\n" +
			LEFTKEY + "\n" +
			RIGHTKEY + "\n" +
			ATTACKKEY + "\n";
	}
	public Settings clone(){
		return new Settings(GAMENAME, QUALITY, RESOLUTION, JUMPKEY, LEFTKEY, RIGHTKEY, ATTACKKEY);
	}
}
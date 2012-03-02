package towered;

import java.awt.Dimension;

public class Settings {		
	////public vars currently only accessible via Settings.varname
	////TODO: IMPLEMENT GET/SET?
	public String GAMENAME;
	public String QUALITY;
	public Dimension RESOLUTION;
	public int 	JUMP,
				LEFT,
				RIGHT,
				ATTACK;
	public Settings(String gn, String q, int w, int h, int j, int l, int r, int a){
		GAMENAME = gn;
		RESOLUTION = new Dimension(w,h);
		QUALITY = q;
		JUMP = j;
		LEFT = l;
		RIGHT = r;
		ATTACK = a;		
	}
	public Settings(String gn, String q, Dimension d, int j, int l, int r, int a){
		GAMENAME = gn;
		RESOLUTION = d;
		QUALITY = q;
		JUMP = j;
		LEFT = l;
		RIGHT = r;
		ATTACK = a;		
	}
	public Settings clone(){
		return new Settings(GAMENAME, QUALITY, RESOLUTION, JUMP, LEFT, RIGHT, ATTACK);
	}
}
package resources;

import java.awt.image.BufferedImage;

public class Static {
	public BufferedImage src;
	public TSI tsi;
	public String desc;
	
	public Static(TSI tsi, String desc){
		this.desc = desc;
		this.tsi = tsi;
	}
}

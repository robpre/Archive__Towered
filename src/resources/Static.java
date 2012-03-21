package resources;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class Static {
	public BufferedImage src;
	public HashMap<String, BufferedImage> i;
	
	public Static(Streams str, TSI tsi, String desc){
		System.out.println("loading :" + desc);
		src=getImage(str.intResource(tsi.res));
		if(tsi.count>1){
			mult(tsi);
		} else {
			single(tsi);
		}
		System.out.println("finished :" + desc);
	}

	private void mult(TSI tsi) {
		
	}

	private void single(TSI tsi) {
		src = 
	}

	private BufferedImage getImage(InputStream is){
		try{
			return ImageIO.read(is);
			//return null;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}

package resources;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Animation {
	
	//default image is orientated right
	private long movieTime,totalTime;
	private ArrayList<Scene> scenes;
	private int index;
	
	public void start(){
		index=0;
		movieTime=0;
	}
	
	
	public synchronized void add(Point img, long len, Rectangle clip){
		totalTime += len;
		scenes.add(new Scene(img,len,clip));
	}
	
	public synchronized void update(long timePassed){
		if(scenes.size()>1){
			movieTime += timePassed;
			if(movieTime >= totalTime){
				movieTime = 0;
				index = 0;
			}
			while(movieTime > scenes.get(index).len){
				index++;				
			}
		}
	}
	
///////////////// PRIVATE INNER CLASS ///////////////
	
	private class Scene {
		public Point img;
		public long len;
		public Rectangle clip;
		public Scene(Point img, long len, Rectangle clip){
			this.img = img;
			this.len = len;
			this.clip = clip;
		}
	}
}
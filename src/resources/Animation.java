package resources;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Animation {
	
	//default image is orientated right
	private long movieTime,totalTime;
	private ArrayList<Scene> scenes;
	private int index;
	
	public Animation(){
		scenes = new ArrayList<Scene>();
		start();
		totalTime = 0;
	}
	
	public void start(){
		index=0;
		movieTime=0;
	}
	
	public synchronized Point getPoint(){
		return scenes.get(index).p;
	}
	
	public synchronized Rectangle getClip(){
		return scenes.get(index).clip;
	}
	
	public synchronized void add(Point p, long len, Rectangle clip){
		totalTime += len;
		scenes.add(new Scene(p,totalTime,clip));
	}
	
	public synchronized void update(long timePassed){
		if(scenes.size()>1){
			movieTime += timePassed;
			if(movieTime >= totalTime){
				movieTime = 0;
				index = 0;
			}
			while(movieTime > getScene(index).len){
				index++;
			}
		}
	}
	
	private Scene getScene(int x){
		return (Scene)scenes.get(x);
	}
	
///////////////// PRIVATE INNER CLASS ///////////////
	
	private class Scene {
		public Point p;
		public long len;
		public Rectangle clip;
		public Scene(Point p, long len, Rectangle clip){
			this.p = p;
			this.len = len;
			this.clip = clip;
		}
	}
}
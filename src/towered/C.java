package towered;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import resources.Resources;
import window.ScreenManager;

public abstract class C {
	
	public Settings gameSettings;
	private ArrayList<String> debugText;
	public Resources resources;
	private boolean running;
	protected ScreenManager s;
	
	
	public void stop(){
		running = false;
	}
	
	//call init and game loop
	public void run(){
		try{
			init();
			gameLoop();
		}finally{
			close();
		}
	}
	
	private void close() {
		//System.exit(0);
	}

	//set screen size and launch
	public void init(){
		resources = new Resources(gameSettings);
		gameSettings = resources.SETTINGS.clone();
		s = new ScreenManager(gameSettings);
		s.launch();
		s.debug(); 
		running = true;
	}
	
	public void gameLoop(){
		long startTime = System.currentTimeMillis();
		long totTime = startTime;
		
		while(running){
			long timePassed = System.currentTimeMillis() - totTime;
			totTime+=timePassed;
			
			physics(timePassed);
			update(timePassed);
			
			Graphics2D g = s.getGraphics();
			draw(g);
			g.dispose();
			s.update();
			
			try{
				Thread.sleep(20-timePassed);
			}catch(Exception ex){}			
		}
	}
	
	public void drawDebugText(Graphics2D g){
		g.setColor(Color.white);
		for(int i=0;i<debugText.size();i++){
			g.drawString(debugText.get(i), 50, i*25);
		}
	}
	
	public void addDebugText(String s){
		debugText.add(s);
		if(debugText.size()>10){
			debugText.remove(0);
			debugText.trimToSize();
		}
	}
	
	//update physics
	public void physics(long timePassed){}

	//update animation
	public void update(long timePassed){
		
	}
	
	//draws to screen
	public abstract void draw(Graphics2D g);

}

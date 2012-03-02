package towered;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import towered.Settings;
import window.ScreenManager;

public abstract class C {
	public Settings gameSettings;
	
	
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
		System.exit(0);
	}

	//set screen size and launch
	public void init(){
		gameSettings = new Settings("untitledtdm", "860x672", 860, 672, 32, 65, 68, 32);
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
	
	//update physics
	public void physics(long timePassed){}

	//update animation
	public void update(long timePassed){}
	
	//draws to screen
	public abstract void draw(Graphics2D g);

}

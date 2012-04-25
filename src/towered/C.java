package towered;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import resources.Entity;
import resources.Resources;
import resources.Character;
import resources.Static;
import window.ScreenManager;

public abstract class C {
	
	public Settings gameSettings;
	public Resources resources;	
	public ScreenManager s;	
	
	private ArrayList<String> debugText;
	private static String sStore;
	private boolean running;
	private static boolean debug;	
	private ArrayList<Entity> activeEntities;	
	private ArrayList<QueItem> que;
	
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
	
	protected void close() {
		//System.exit(0);
	}


	//set screen size and launch
	public void init(){ // I love faye williams
		if(debug && sStore.equals("default")){
			resources = new Resources(gameSettings);
			debugText = new ArrayList<String>();
		}else if(debug){
			resources = new Resources(gameSettings, sStore);
			debugText = new ArrayList<String>();
		} else {
			resources = new Resources(gameSettings);
		}
		gameSettings = resources.SETTINGS.clone();
		s = new ScreenManager(gameSettings);
		s.launch();
		s.debug(); 
		running = true;
		activeEntities = new ArrayList<Entity>();
		que = new ArrayList<QueItem>();
		addDebugText("a");
		addDebugText("a");
		addDebugText("a");
		addDebugText("a");
		addDebugText("a");
		addDebugText("a");
		addDebugText("a");
		addDebugText("a");
		addDebugText("a");
	}
	
	public void addKeyListener(KeyListener kl){
		s.gameWindow.addKeyListener(kl);
	}
	
	public void addKeyListener(MouseListener ml){
		s.gameWindow.addMouseListener(ml);
	}
	
	public ArrayList<Entity> getAE(){
		return activeEntities;
	}
	
	public void emptyEntities(){
		for(Entity e:getAE()){
			que.add(new QueItem(false, e));
		}
	}
	public synchronized void removeEntity(String... s){
		for(String s1:s){
			for(Entity e:getAE()){
				if(e.sceneName.equals(s1))
					que.add(new QueItem(false,e));
			}
		}
	}
	public void addEntity(Entity e){
		que.add(new QueItem(true, e));
	}
	public Character getCharacter(){
		Character c = null;
		for(int i =0;i<getAE().size();i++){
			if(getAE().get(i).type.contains("c"))
				c = (Character)getAE().get(i);
		}
		return c;
	}
	public Static getStatic(String s){
		for(Entity e:getAE()){
			if(e.sceneName.equals(s))
				return (Static)e;
		}
		return null;
	}
	

	
	public static void parseCmdLine(String[] args){
		for(int i=0, j=1;i<args.length && j<args.length;i++, j++){
			String s = args[i];
			String t = args[j];
			switch(s.charAt(0)){
			case '-':
				if(s.substring(1).toLowerCase().equals("debug"))
					debug = (t.equals("true"));
				if(s.substring(1).toLowerCase().equals("settings"))
					sStore = t;
				else
					sStore="default";
			}
		}
	}
	
	public void gameLoop(){
		long startTime = System.currentTimeMillis();
		long totTime = startTime;
		
		while(running){
			long timePassed = System.currentTimeMillis() - totTime;
			totTime+=timePassed;
			
			upKeep();
			
			setDebugText(0,"TimePassed :\t" + timePassed);
			setDebugText(1,"Active Entities size: :" + getAE().size());
			
			update(timePassed);
			
			Graphics2D g = s.getGraphics();
			draw(g);
			g.dispose();
			s.update();
			
			try{
				Thread.sleep(25-timePassed);
			}catch(Exception ex){}			
		}
	}
	private synchronized void upKeep(){
		if(que.size()==0)
			return;
		for(int i=0;i<que.size();i++){
			QueItem qi = que.get(i);
			if(qi.mode){
				getAE().add(qi.e);
			} else {
				getAE().remove(qi.e);
			}		
		}
		que.clear();
		getAE().trimToSize();		
	}
	
	public void drawDebugText(Graphics2D g){
		g.setColor(Color.white);
		for(int i=0;i<debugText.size();i++){
			g.drawString(i + ": " + debugText.get(i), 50, i*25 + 30);
		}
	}		
	
	public void setDebugText(int x, String s){
		if(x>debugText.size())
			return;
		debugText.set(x, s);
	}
	public void addDebugText(String s){
		debugText.add(s);
		if(debugText.size()>10){
			debugText.remove(0);
			debugText.trimToSize();
		}
	}

	//update animation
	public abstract void update(long timePassed);
	
	//draws to screen
	public abstract void draw(Graphics2D g);
	
	private class QueItem{
		public boolean mode;
		public Entity e;
		public QueItem(boolean mode, Entity e){
			this.mode = mode;
			this.e = e;
		}
	}

}

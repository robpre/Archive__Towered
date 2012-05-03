package towered;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import resources.Entity;
import resources.Map;
import resources.Static;

public class Towered extends C{
	public static void main(String[] args) {
		parseCmdLine(args);
		new Towered().run();
	}

	private Listener listener;
	private Physics physics;
	private Map activeMap;
	private boolean sticky;
	
	@Override
	public void init(){
		gameSettings = new Settings("Towered");
		super.init(); // do this after game settings gets started
		openMenu();
		listener = new Listener();
		s.gameWindow.addKeyListener(listener);
		s.gameWindow.addMouseListener(listener);
		s.gameWindow.addMouseMotionListener(listener);
		physics = new Physics(this);
	}
	
	public void click(String s){
		s = s.toLowerCase();
		System.out.println("Clicked: " + s);
		if(s.equals("exitbutton")){
			close();
		} else if(s.equals("startbutton")){
			closeMenu();
			System.out.println(resources.chars.toString());
			addEntity(((Entity)resources.chars.get("Blarie").clone()));
			getPlayer().test();
			getPlayer().setPos(250, 350);
		}
	}
	
	public void pressed(int c){
		if(c == gameSettings.JUMPKEY)
			physics.jump();
		if(c == gameSettings.LEFTKEY)
			physics.moveLeft();
		if(c == gameSettings.RIGHTKEY)
			physics.moveRight();
	}
	public void released(int c){
		if(c == gameSettings.LEFTKEY)
			physics.stopMove();
		if(c == gameSettings.RIGHTKEY)
			physics.stopMove();
	}
	
	public Map getMap(){
		return activeMap;
	}
	
	public void closeMenu(){
		removeEntity("exitbutton", "startbutton", "main");
		activeMap = resources.maps.get("TestLevel").clone();
	}
	
	/*
	 * here is proof of concept of my importing method.
	 * each of the 
	 * 
	 */
	public void openMenu(){
		getAE().clear();
		getAE().add((Entity)resources.staticImages.get("Main").clone());
		getAE().add((Entity)resources.staticImages.get("Static").clone());
		getAE().add((Entity)resources.staticImages.get("Static").clone());
		Static m = (Static)getAE().get(0);
		m.changeScene("main");
		m.setPos(0, 0);
		m = (Static)getAE().get(1);
		m.changeScene("startbutton");
		m.setPos(340, 200);
		m.clickable(true);
		m = (Static)getAE().get(2);
		m.changeScene("exitbutton");
		m.setPos(340, 350);
		m.clickable(true);
	}
	
	@Override
	public synchronized void update(long timePassed){			
		for(Entity aE:getAE()){
			aE.update(timePassed);
		}
		physics.update(timePassed);
	}

	@Override
	public synchronized void draw(Graphics2D g) {
		if(activeMap!=null){
			activeMap.draw(g);
			g.setColor(new Color(0f, 1f, 0f, 0.5f));
			g.setFont(new Font("Serif", Font.BOLD, 32));
			g.drawString(getMap().x + "," + getMap().y, 50, 50);
		}
		for(Entity aE:getAE()){
			aE.draw(g);
		}
//		g.setColor(new Color(255, 0, 0, 155));
//		g.fillRect(0, 0, 250, gameSettings.RESOLUTION.height);
//		g.fillRect(gameSettings.RESOLUTION.width-250, 0, 250, gameSettings.RESOLUTION.height);
//		g.setColor(new Color(0, 255, 0, 155));		
//		g.fillRect(250, 0, gameSettings.RESOLUTION.width-500, gameSettings.RESOLUTION.height);
	}
	
	/*
	 * private inner class, listener
	 */
	
	private class Listener implements KeyListener,MouseListener,MouseMotionListener{

		public Listener(){
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			for(Entity ent:getAE()){
				if(ent.clickable){
					if(ent.click.contains(e.getPoint())){
						click(ent.sceneName);
					}
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			sticky =false;
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getExtendedKeyCode()==KeyEvent.VK_LEFT)
				getMap().x -= 48;
			if(e.getKeyCode()==KeyEvent.VK_RIGHT)
				getMap().x += 48;		
			pressed(e.getKeyCode());
		}

		@Override
		public void keyReleased(KeyEvent e) {
			released(e.getKeyCode());			
		}

		@Override
		public void keyTyped(KeyEvent e) {	
		}

		@Override
		public void mouseDragged(MouseEvent arg0) {
			// TODO Auto-generated method stub
			if(getPlayer() != null){
				if(getPlayer().getClipping().contains(arg0.getPoint()) || sticky){
					getPlayer().setPos(arg0.getX(), arg0.getY());
					if(!sticky)sticky=true;
				}
			}
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
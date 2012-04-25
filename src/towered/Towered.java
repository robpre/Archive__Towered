package towered;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import resources.Entity;
import resources.Static;

public class Towered extends C{
	public static void main(String[] args) {
		parseCmdLine(args);
		new Towered().run();
	}

	private Listener listener;
	private Physics physics;
	
	@Override
	public void init(){
		gameSettings = new Settings("Towered");
		super.init(); // do this after game settings gets started
		openMenu();
		listener = new Listener();
		s.gameWindow.addKeyListener(listener);
		s.gameWindow.addMouseListener(listener);
		physics = new Physics(this);
	}
	
	public void click(String s){
		s = s.toLowerCase();
		System.out.println("Clicked:" + s);
		if(s.equals("exitbutton")){
			close();
		} else if(s.equals("startbutton")){
			closeMenu();
			System.out.println(resources.chars.toString());
			addEntity(((Entity)resources.chars.get("Blarie").clone()));
		}
	}
	
	public void pressed(char c){
		switch(c){
		case 'w':
			
			break;
		case 'd':
			
			break;
		}
	}
	
	public void closeMenu(){
		removeEntity("exitbutton", "startbutton", "main");
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
		physics.update(timePassed);
		for(Entity aE:getAE()){
			aE.update(timePassed);
		}
	}

	@Override
	public synchronized void draw(Graphics2D g) {
		g.fillRect(0, 0, gameSettings.RESOLUTION.width, gameSettings.RESOLUTION.height);
		for(Entity aE:getAE()){
			aE.draw(g);
		}
		g.setColor(new Color(255, 0, 0));
		//g.fillRect(0, 0, 250, 672);
		drawDebugText(g);
	}
	
	/*
	 * private inner class, listener
	 */
	
	private class Listener implements KeyListener,MouseListener{

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
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			pressed(e.getKeyChar());
		}
		
	}

}
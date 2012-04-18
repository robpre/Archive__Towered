package towered;

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
	
	@Override
	public void init(){
		gameSettings = new Settings("Towered");
		super.init(); // do this after game settings gets started
		openMenu();
		listener = new Listener();
		s.gameWindow.addKeyListener(listener);
		s.gameWindow.addMouseListener(listener);
	}
	
	public void click(String s){
		if(s.equals("exitbutton")){
			close();
		}
			
	}
	
	public void openMenu(){
		getAE().clear();
		getAE().add((Static)resources.staticImages.get("Main").clone());
		getAE().add((Static)resources.staticImages.get("Static").clone());
		getAE().add((Static)resources.staticImages.get("Static").clone());
		Static m = (Static)getAE().get(0);
		m.changeScene("main");
		m.setPos(0, 0);
		m.clickable(true);
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
	public void update(long timePassed){
		for(Entity aE:getAE()){
			aE.update(timePassed);
		}
	}

	@Override
	public void draw(Graphics2D g) {
		for(Entity aE:getAE()){
			aE.draw(g);
		}
		//g.setColor(new Color(255, 0, 0));
		//g.fillRect(0, 0, 250, 672);
		//drawDebugText(g);
	}
	
	/*
	 * private inner class, listener
	 */
	
	private class Listener implements KeyListener,MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			for(Entity ent:getAE()){
				if(ent.clickable){
					Static st = (Static)ent;
					if(st.click.contains(e.getPoint())){
						click(st.sceneName);
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
			// TODO Auto-generated method stub
			
		}
		
	}

}
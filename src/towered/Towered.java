package towered;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import resources.Entity;
import resources.Static;

public class Towered extends C{
	public static void main(String[] args) {
		parseCmdLine(args);
		new Towered().run();
	}
	
	private ArrayList<Entity> activeEntities;
	
	@Override
	public void init(){
		gameSettings = new Settings("Towered");
		super.init(); // do this after game settings gets started
		activeEntities = new ArrayList<Entity>();
		openMenu();
	}
	
	public void openMenu(){
		activeEntities.clear();
		activeEntities.add((Static)resources.staticImages.get("Main").clone());
		activeEntities.add((Static)resources.staticImages.get("Static").clone());
		activeEntities.add((Static)resources.staticImages.get("Static").clone());
		Static m = (Static)activeEntities.get(0);
		m.changeScene("main");
		m.setPos(0, 0);
		m = (Static)activeEntities.get(1);
		m.changeScene("startbutton");
		m.setPos(340, 200);
		m = (Static)activeEntities.get(2);
		m.changeScene("exitbutton");
		m.setPos(340, 350);
	}
	
	@Override
	public void update(long timePassed){
		for(Entity aE:activeEntities){
			aE.update(timePassed);
		}
	}

	@Override
	public void draw(Graphics2D g) {
		for(Entity aE:activeEntities){
			aE.draw(g);
		}
		//g.setColor(new Color(255, 0, 0));
		//g.fillRect(0, 0, 250, 672);
		//drawDebugText(g);
	}
}

package towered;

import java.awt.Color;
import java.awt.Graphics2D;

public class Towered extends C{
	public static void main(String[] args) {
		parseCmdLine(args);
		new Towered().run();
	}
	
	@Override
	public void init(){
		gameSettings = new Settings("Towered");
		super.init(); // do this at the end of the init		
	}
	
	@Override
	public void physics(long timePassed) {
		
	}
	
	public void update(long timePassed){
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(new Color(255, 0, 0));
		g.fillRect(0, 0, 250, 672);
		drawDebugText(g);
	}
}

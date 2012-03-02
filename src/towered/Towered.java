package towered;

import java.awt.Graphics2D;

public class Towered extends C{
	public static void main(String[] args) {
		new Towered().run();
	}
	
	@Override
	public void init(){
		
		super.init(); // do this at the end of the init		
	}
	
	@Override
	public void physics(long timePassed) {
		
	}
	
	public void update(long timePassed){}

	@Override
	public void draw(Graphics2D g) {
		
	}
}

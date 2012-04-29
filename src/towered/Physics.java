package towered;

import java.awt.Rectangle;

import resources.Entity;
import resources.Character;
import resources.Static;

public class Physics {
	private double gravity;
	private Towered t;
	private boolean jumping;
	private int maxHeight;
	
	public Physics(Towered t){
		this.t = t;
		gravity = 0.15;		
	}
	
	public void update(long timePassed){
		move(timePassed);
		applyGravity(timePassed);
	}
	
	public void jump(){
		if(!jumping){
			jumping = true;
			maxHeight = t.getPlayer().y - 150;
		}
	}
	
	private void move(long timePassed) {
		if(jumping){
			int y = t.getPlayer().y;
			y -= (timePassed * gravity);
			Rectangle r = t.getPlayer().getClipping();
			int cy = r.y;
			cy -= (timePassed * gravity);
			r.setLocation(r.x, cy);
			if(!(checkCollision(r) || y <= maxHeight)){
				t.getPlayer().y=y;
			}
			if(y<=maxHeight || checkCollision(r)){
				jumping = false;
			}
		}
		
	}

	public void applyGravity(long timePassed){
		for(Entity e:t.getAE()){
			if(!e.type.equals("s")){
				int y = e.click.y;
				y+=(timePassed * gravity);
				Rectangle r = e.click;
				r.setLocation(r.x, y);
				if(!checkCollision(r) && !playerJumping(e)){
					e.y += (timePassed * gravity);
				}
			}
		}
	}
	
	private boolean playerJumping(Entity e){
		if(e.type.charAt(0) == 'c')
			if(jumping)
				return true;
		return false;
	}
	
	private boolean checkCollision(Rectangle r){
		for(Rectangle r1:t.getMap().clipping){
			if(r.intersects(r1))
				return true;
		}
		return false;
	}
	
	private Object getEntity(Entity e){
		switch(e.type.charAt(0)){
		case 'c':
			return (Character)e;
		case 's':
			return (Static)e;
		default:
			return e;
		}
	}
}

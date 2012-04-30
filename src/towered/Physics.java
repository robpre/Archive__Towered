package towered;

import java.awt.Point;
import java.awt.Rectangle;

import resources.Character;
import resources.Entity;
import resources.Static;

public class Physics {
	private double gravity;
	private Towered t;
	private boolean jumping, canJump;
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
		if(!jumping && canJump){
			jumping = true;
			maxHeight = t.getPlayer().y - 150;
		}
	}
	
	private void move(long timePassed) {
		if(jumping){
			int y = t.getPlayer().y;
			y -= (timePassed * gravity);
			jumping = !checkCollision(t.getPlayer().getClipping(), y);
			jumping = !(y<=maxHeight);
			if(jumping){
				t.getPlayer().y=y;
			}
		}
		
	}

	public void applyGravity(long timePassed){
		for(Entity e:t.getAE()){
			if(!e.type.equals("s")){
				int y = e.click.y;
				y+=(timePassed * gravity);
				if(!checkCollision(e.getClipping(), y) && !playerJumping(e)){
					e.y += (timePassed * gravity);
				}
				canJump = checkCollision(e.getClipping(), y);
			}
		}
	}
	
	private boolean playerJumping(Entity e){
		if(e.type.charAt(0) == 'c')
			if(jumping)
				return true;
		return false;
	}
	
	public boolean checkCollision(Rectangle r, Point p){
		Rectangle r1 = r;
		r.setLocation(p);
		for(Rectangle r2:t.getMap().clipping){
			if(r1.intersects(r2))
				return true;
		}
		return false;
	}
	public boolean checkCollision(Rectangle r, int y){
		Rectangle r1 = r;
		r1.setLocation(r.x, y);
		for(Rectangle r2:t.getMap().clipping){
			if(r1.intersects(r2))
				return true;
		}
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

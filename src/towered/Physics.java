package towered;

import java.awt.Point;
import java.awt.Rectangle;

import resources.Character;
import resources.Entity;
import resources.Static;

public class Physics {
	private double gravity;
	private Towered t;
	private boolean jumping, canJump, moving, moveLeft, stuck;
	private int maxHeight;
	
	public Physics(Towered t){
		this.t = t;
		gravity = 0.180;	
		moving = false;
		jumping = false;
		canJump = false;
		moveLeft = false;
		stuck=false;
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
	
	public void moveLeft(){
		moveLeft = true;
		moving = true;
		System.out.println("moving left");
	}
	
	public void moveRight(){
		moveLeft = false;
		moving = true;
		System.out.println("moving right");
	}
	
	public void stopMove(){
		moving = false;
		System.out.println("stop");
	}
	
	private void move(long timePassed) {
		if(t.getPlayer()!=null)
			if(t.getPlayer().y > t.gameSettings.RESOLUTION.height)
				t.getPlayer().kill();
		if(moving){
			if(moveLeft){
				int x = t.getPlayer().x;
				x -= (t.getPlayer().speed*timePassed);
				moveTo(t.getPlayer(), x, t.getPlayer().y);
			} else {
				int x = t.getPlayer().x;
				x += (t.getPlayer().speed*timePassed);
				moveTo(t.getPlayer(), x, t.getPlayer().y);
			}
		}
		if(jumping){
			int y = t.getPlayer().y;
			y -= (timePassed * gravity);
			jumping = !(y<=maxHeight);
			if(jumping){
				jumping = moveTo(t.getPlayer(), t.getPlayer().x, y);
			}
		}
	}

	public void applyGravity(long timePassed){
		for(Entity e:t.getAE()){
			if(!e.type.equals("s")){
				int y = e.getClipping().y;
				y+=(timePassed * gravity);
				if(!checkCollision(e.getClipping(), y) && !playerJumping(e)){
					e.y += (timePassed * gravity);
				}
				canJump = checkCollision(e.getClipping(), y);
				if(checkCollision(e.getClipping())){
					switch(collisionPosition(e.getClipping()).charAt(0)){
					case 't':
						t.getPlayer().y -= (timePassed * gravity);
						break;
					case 'b':
						t.getPlayer().y += (timePassed * gravity);
						break;
					}
					switch(collisionPosition(e.getClipping()).charAt(1)){
					case 'l':
						t.getPlayer().x -= (timePassed * gravity);
						break;
					case 'r':
						t.getPlayer().x += (timePassed * gravity);
						break;
					}
					stuck = true;
				} else {
					stuck = false;
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
	
	private int compare(int startInt, int endInt){
		return endInt - startInt;
	}
	
	private boolean moveTo(Entity e, int x, int y){
		int dx = compare(e.x, x);
		int dy = compare(e.y, y);
		dx = dx + e.getClipping().x;
		dy = dy + e.getClipping().y;
		if(!checkCollision(e.getClipping(), new Point(dx, dy))){
			e.setPos(x, y);
			return true;
		} else {
			return false;
		}
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
	
	private String collisionPosition(Rectangle r){
		Rectangle r1 = null;
		for(Rectangle tr:t.getMap().clipping){
			if(r.intersects(tr)){
				r1 = tr;
				break;
			}
		}
		Rectangle r2 = r1.intersection(r);
		int x = r1.x + (r1.width/2);
		int y = r1.y + (r1.height/2);
		String s;
		if(r2.y > y){
			s = "t";
			if(r2.x>x)
				s += "r";
			else
				s += "l";
		} else {
			s = "b";
			if(r2.x > x)
				s += "r";
			else
				s += "l";
		}
		return s;
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

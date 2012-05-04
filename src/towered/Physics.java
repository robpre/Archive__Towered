package towered;

import java.awt.Point;
import java.awt.Rectangle;

import resources.Character;
import resources.Entity;
import resources.Static;

public class Physics {
	private double gravity;
	private Towered t;
	private boolean jumping, canJump, moving, moveLeft, attacking;
	private int maxHeight;
	
	public Physics(Towered t){
		this.t = t;
		gravity = 0.25;
		moveLeft = false;
		moving = false;
		jumping = false;
		canJump = true;
	}

	public void update(long timePassed){
		if(t.getMap()!=null && t.getPlayer() != null){
			move(timePassed);
			applyGravity(timePassed);
		}
	}
	
	public void jump(){
		if(!jumping && canJump){
			jumping = true;
			maxHeight = t.getPlayer().y - 150;
			canJump=false;
		}
	}
	
	public void moveLeft(){
		if(!t.getPlayer().isFacingLeft())
			t.getPlayer().setOLeft(true);
		moveLeft = true;
		if(!moving){
			moving = true;
			if(!attacking)
				t.getPlayer().setAnimation("walk");
		}
	}
	
	public void moveRight(){
		if(t.getPlayer().isFacingLeft())
			t.getPlayer().setOLeft(false);
		moveLeft = false;
		if(!moving){
			moving = true;
			if(!attacking)
				t.getPlayer().setAnimation("walk");
		}		
	}
	
	public void attack() {
		if(!attacking){
			attacking = true;
			t.getPlayer().setAnimation((String)randomChoice("attack1","attack2"));
		}			
	}
	
	public void stopAttacking(){
		if(t.getPlayer().animationComplete() && attacking){
			attacking = false;
			t.getPlayer().setAnimation("idle");
		}
	}
	
	private Object randomChoice(Object... o){
		return o[ranNum(0,o.length-1)];
	}
	
	public int ranNum(int i1, int i2){
		return i1 + (int)(Math.random() * ((i2 - i1) + 1));
	}
	
	public void stopMove(){
		moving = false;
	}
	
	public void stopMoveIdle(){
		if(moving){
			if(!attacking)
				t.getPlayer().setAnimation("idle");
			moving = false;
		}
	}
	
	/*
	 * move method, this is ran every game tick. 
	 * 
	 * this controls when to move the player or the screen
	 * 
	 * and jmuping
	 * 
	 * 
	 */
	
	private void move(long timePassed) {
		if(jumping){
			int y = t.getPlayer().y;
			y -= (timePassed * gravity);
			jumping = !(y<=maxHeight);
			if(jumping){
				jumping = moveTo(t.getPlayer(), t.getPlayer().x, y);
			}
		}
		if(t.getPlayer()!=null)
			if(t.getPlayer().y > t.gameSettings.RESOLUTION.height)
				t.getPlayer().kill();
		if(moving){
			int mapOffsetX = -t.getMap().x;
			int screenW = t.gameSettings.RESOLUTION.width;
			int mapW = t.getMap().width;
			int sSize = 250;
			if(moveLeft){
				if((t.getPlayer().x > sSize) || (t.getPlayer().x <= sSize && mapOffsetX <= 0)){
					int x = t.getPlayer().x;
					x -= (t.getPlayer().speed*timePassed);
					moveTo(t.getPlayer(), x, t.getPlayer().y);
				} else if(t.getPlayer().x <= sSize && mapOffsetX > 0){
					mapOffsetX -= (t.getPlayer().speed*timePassed);
				}
			} else {
				if((t.getPlayer().x < screenW - sSize) || (t.getPlayer().x >= screenW - sSize && mapOffsetX >= mapW - screenW)){
					int x = t.getPlayer().x;
					x += (t.getPlayer().speed*timePassed);
					moveTo(t.getPlayer(), x, t.getPlayer().y);
				} else if(t.getPlayer().x >= screenW - sSize && mapOffsetX < mapW - screenW){
					mapOffsetX += (t.getPlayer().speed*timePassed);
				}
			}
			if(mapOffsetX > 0)
				t.getMap().x = -mapOffsetX;
			else
				t.getMap().x = 0;
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
//				if(checkCollision(e.getClipping())){
//					System.out.println("Clipping!");
//					switch(collisionPosition(e.getClipping()).charAt(0)){
//					case 't':
//						t.getPlayer().y -= (timePassed * gravity);
//						break;
//					case 'b':
//						t.getPlayer().y += (timePassed * gravity);
//						break;
//					}
//					switch(collisionPosition(e.getClipping()).charAt(1)){
//					case 'l':
//						t.getPlayer().x -= (timePassed * gravity);
//						break;
//					case 'r':
//						t.getPlayer().x += (timePassed * gravity);
//						break;
//					}
//				} else {
//				}
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
			System.out.println("Can't move! : " + x + "," + y);
			return false;
		}
	}
	
	public boolean checkCollision(Rectangle r, Point p){
		Rectangle r1 = r;
		r.setLocation(p);
		for(Rectangle r2:t.getMap().getClipping()){
			if(r1.intersects(r2))
				return true;
		}
		return false;
	}
	public boolean checkCollision(Rectangle r, int y){
		Rectangle r1 = r;
		r1.setLocation(r.x, y);
		for(Rectangle r2:t.getMap().getClipping()){
			if(r1.intersects(r2))
				return true;
		}
		return false;
	}
	private boolean checkCollision(Rectangle r){
		for(Rectangle r1:t.getMap().getClipping()){
			if(r.intersects(r1))
				return true;
		}
		return false;
	}
	
	private String collisionPosition(Rectangle r){
		Rectangle r1 = null;
		for(Rectangle tr:t.getMap().getClipping()){
			if(r.intersects(tr)){
				r1 = tr;
				break;
			}
		}
		if(r1 == null || r == null)
			return "bl";
		Rectangle r2 = r1.intersection(r);
		int x = r1.x + (r1.width/2);
		int y = r1.y + (r1.height/2);
		String s;
		if(r2.y < y){
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

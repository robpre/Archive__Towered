package resources;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Character extends Entity{
		
	private Sprite sprite;
	
	public Character(BufferedImage img, TSI tsi){
		this.img = img;
		sprite = new Sprite(tsi, img.getWidth(), img.getHeight());
		speed = 1.5;
		type = "c";
	}
	
	public Character(){}
	
	public void test(){
		sprite.changeAnimation("idle");
		sprite.isFacingLeft = !sprite.isFacingLeft;
	}
	
	@Override
	public void update(long timePassed) {
		sprite.update(timePassed);
	}

	@Override
	public void draw(Graphics2D g) {
		BufferedImage draw = img.getSubimage(	sprite.getImageRect().x,
												sprite.getImageRect().y, 
												sprite.getImageRect().width, 
												sprite.getImageRect().height);
		if(sprite.defaultOrientationLeft != sprite.isFacingLeft){
			g.drawImage (draw, 
						draw.getWidth() + x, 	//dest x1
						y, 						//dest y1
						x,						//dest x2
						draw.getHeight() + y,	//dest y2
						0, 						//src x1
						0, 						//src y1
						draw.getWidth(),		//src x2
						draw.getHeight(),		//src y2
						null);
		} else {
			g.drawImage(draw, x, y, null);
		}
	}


	@Override
	public Object clone() {
		Character c;
		c = new Character();
		c.sprite = sprite;
		c.setPos(x, y);
		c.speed = speed;
		c.img = img;
		c.type = "c";
		return c;
	}
}

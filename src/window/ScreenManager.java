package window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import towered.Settings;

public class ScreenManager implements ComponentListener{
	public GameFrame gameFrame;
	public GameWindow gameWindow;
	
	private Settings s;

	public ScreenManager(Settings s){this.s = s;}
	
	public void launch(){
		gameFrame = new GameFrame(s.GAMENAME,s.RESOLUTION);
		gameWindow = new GameWindow(gameFrame, s.RESOLUTION);
		gameFrame.addComponentListener(this);
		gameWindow.moveTo(gameFrame.getDesiredWinPos());
	}
	
	public void debug(){
		gameWindow.setBackground(Color.green);
	}
	
	@Override
	public void componentMoved(ComponentEvent e) {
		gameWindow.moveTo(gameFrame.getDesiredWinPos());
	}
	
	public BufferStrategy getWinBS(){
		if(gameWindow!=null)
			return gameWindow.getBufferStrategy();
		return null;
	}
	
	public Graphics2D getGraphics(){
		if(gameWindow!=null)
			return gameWindow.getBufferGraphics();
		return null;
	}
	
	public void update(){
		if(gameWindow!=null){
			BufferStrategy s = getWinBS();
			if(!s.contentsLost()){
				s.show();
			}
		}
	}
	
	@Override
	public void componentHidden(ComponentEvent e) {}
	@Override
	public void componentResized(ComponentEvent e) {}
	@Override
	public void componentShown(ComponentEvent e) {}
	
	/**
	 * private classes
	 * 
	 * Windows window which contains a java "window" to draw on,
	 * this gives a container to the screen.
	 */
	
	private class GameFrame extends JFrame{
		/**
		 * 
		 */
		public Dimension insetOffsets;		
		public GameFrame(String name,Dimension res){
			super();
			setTitle(name);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setUndecorated(false);
			setResizable(false);
			setVisible(true);
			insetOffsets = new Dimension(getInsets().left+getInsets().right,getInsets().top+getInsets().bottom);
			setSize(res.width+insetOffsets.width,res.height+insetOffsets.height);
			setLocationRelativeTo(null);
		}		
		public Point getDesiredWinPos(){
			return new Point(getLocation().x+getInsets().left,getLocation().y+getInsets().top);
		}
	}
	public class GameWindow extends Window{
		/**
		 * 
		 */
		public GameWindow(Frame arg0, Dimension d) {
			super(arg0);
			setSize(d);
			setFocusable(true);
			setVisible(true);
			createBufferStrategy(2);
			setIgnoreRepaint(true);
		}
		public void moveTo(Point p){
			this.setLocation(p);
		}
		public Graphics2D getBufferGraphics() {
			return (Graphics2D)getBufferStrategy().getDrawGraphics();
		}
	}
}
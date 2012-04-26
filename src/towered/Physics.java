package towered;

public class Physics {
	private double gravity;
	private Towered t;
	
	public Physics(Towered t){
		this.t = t;
		
	}
	
	public void update(long timePassed){
		t.getPlayer();
		t.getAE();
	}
}

package resources;

public class Streams {
	public void extResource(String item){
		item = "resources/" + item;
		ClassLoader.getSystemResource(item).toString();
	}
	public void intResource(String item){
		
	}
}

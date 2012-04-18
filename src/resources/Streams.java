package resources;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Streams {
	public InputStream extResource(String item){
		InputStream out;
		try{
			out = new FileInputStream(item);
		} catch(FileNotFoundException e){
			System.out.println("Could not find: \n" + item + "\nReturning null.");
			out = null;
		} catch(Exception e){
			System.out.println("Error found: " + e.toString());
			out = null;
		}
		return out;
	}
    public InputStream intResource(String item){
    	//System.out.println(ClassLoader.getSystemResource("resources/" + item));
    	return ClassLoader.getSystemResourceAsStream("resources/" + item);
    }
}
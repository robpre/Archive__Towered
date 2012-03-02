package resources;

import towered.Settings;

public class Streams {
	private String storage;
	private Settings settings;
	
	public Streams(Settings s){
		settings = s.clone();
	}
}

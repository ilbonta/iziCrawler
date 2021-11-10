package ch.bnt.izicrawler.model.dec;

import java.util.List;

import lombok.Data;

@Data
public class City {
	
	private String uuid;
    
	private String type;
    
	private List<String> languages;
    
	private String status;
    
	private Map map;
    
	private String hash;
    
	private boolean visible;
    
	private String title;
    
	private String summary;
    
    private String language;
    
    private Location location;

}

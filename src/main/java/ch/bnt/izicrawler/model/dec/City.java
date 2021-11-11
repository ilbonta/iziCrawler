package ch.bnt.izicrawler.model.dec;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class City implements Serializable {
	
	private static final long serialVersionUID = 3334291728989110021L;

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
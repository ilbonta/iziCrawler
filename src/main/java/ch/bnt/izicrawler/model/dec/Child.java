package ch.bnt.izicrawler.model.dec;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class Child implements Serializable {

	private static final long serialVersionUID = 9072696266814134683L;

	private String uuid;
    
    private String status;
    
    private String type;
    
    private List<String> languages;
    
    private String hash;
    
    private ContentProvider content_provider;
    
    private Publisher publisher;
    
    private List<Image> images;
    
    private Location location;
    
    private String language;
    
    private String summary;
    
    private String desc;
    
    private String title;
}
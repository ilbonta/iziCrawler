package ch.bnt.izicrawler.model.dec;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data 
public class Content implements Serializable {
	
	private static final long serialVersionUID = 6225976827141178832L;

	private List<Video> video;
    
    private List<Audio> audio;
    
    private List<Image> images;
    
    private String language;
    
    private String summary;
    
    private String desc;
    
    private String title;
    
    private List<Child> children;
}
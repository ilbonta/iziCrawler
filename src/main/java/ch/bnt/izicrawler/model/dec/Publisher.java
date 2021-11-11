package ch.bnt.izicrawler.model.dec;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class Publisher implements Serializable {

	private static final long serialVersionUID = 2066275565330515232L;

	private String uuid;
    
    private String type;
    
    private List<String> languages;
    
    private String status;
    
    private String hash;
    
    private String title;
    
    private String summary;
    
    private String language;
    	    
    private List<Image> images;
    
    private ContentProvider content_provider;
    
}
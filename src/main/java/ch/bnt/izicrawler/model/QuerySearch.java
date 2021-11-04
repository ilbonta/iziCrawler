package ch.bnt.izicrawler.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ch.bnt.izicrawler.model.dec.ContentProvider;
import ch.bnt.izicrawler.model.dec.Image;
import ch.bnt.izicrawler.model.dec.Location;
import ch.bnt.izicrawler.model.dec.Map;
import ch.bnt.izicrawler.model.dec.Publisher;
import ch.bnt.izicrawler.model.dec.Reviews;
import ch.bnt.izicrawler.model.dec.Translation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Slf4j
public class QuerySearch implements Serializable {

	private static final long serialVersionUID = -5639382746766109216L;

	private String uuid;
	
	private String type;
	
	private List<String> languages;

	private String status;
	
	private int children_count;

    private ContentProvider content_provider;
    
    private Reviews reviews;
    
    private Publisher publisher;
	
	private List<Translation> translations;

    private Map map;
    
    private String hash;
    
    private boolean visible;
    
    private String title;
    
    private String summary;
    
    private String language;
    
    private List<Image> images;
    
    private Location location;

}

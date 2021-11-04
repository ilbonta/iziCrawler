package ch.bnt.izicrawler.model;

import java.io.Serializable;
import java.util.List;

import ch.bnt.izicrawler.model.dec.Contacts;
import ch.bnt.izicrawler.model.dec.Content;
import ch.bnt.izicrawler.model.dec.ContentProvider;
import ch.bnt.izicrawler.model.dec.Map;
import ch.bnt.izicrawler.model.dec.Publisher;
import ch.bnt.izicrawler.model.dec.Reviews;
import ch.bnt.izicrawler.model.dec.Schedule;
import lombok.Data;
import lombok.ToString;

@Data
public class IZIObject implements Serializable {

	private static final long serialVersionUID = -5639382746766109216L;

	private String uuid;
	
	private String status;

	private String type;
	
	private List<String> languages;

	private Map map;
	
	private String hash;

	private int size;

	private ContentProvider content_provider;
	
	private Reviews reviews;
    
    private Publisher publisher;
    
    private Schedule schedule;
    
    private Contacts contacts;
    
    private List<Content> content;

}

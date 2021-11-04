package ch.bnt.izicrawler.model.dec;

import java.io.Serializable;

public abstract class Multimedia implements Serializable {

	private static final long serialVersionUID = 7416144871461162674L;

	protected String uuid;
    
	protected String type;
    
	protected int duration;
    
	protected int order;
    
	protected String hash;
    
	protected int size;

}

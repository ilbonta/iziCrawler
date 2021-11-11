package ch.bnt.izicrawler.model.dec;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class Schedule implements Serializable {

	private static final long serialVersionUID = 7882022382297816961L;

		private List<String> mon;
	    
	    private List<String> tue;
	    
	    private List<String> wed;
	    
	    private List<String> thu;
	    
	    private List<String> fri;
	    
	    private List<String> sat;
	    
	    private List<String> sun;
	}
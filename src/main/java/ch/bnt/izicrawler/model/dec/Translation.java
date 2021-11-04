package ch.bnt.izicrawler.model.dec;

import java.io.Serializable;

import lombok.Data;

@Data
public class Translation implements Serializable {

	private static final long serialVersionUID = 6158286808563791067L;

	private String name;
	
	private String language;

}

package ch.bnt.izicrawler.model.dec;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Translation implements Serializable {

	private static final long serialVersionUID = 6158286808563791067L;

	private String name;
	
	private String language;

}
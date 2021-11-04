package ch.bnt.izicrawler.model.dec;

import java.io.Serializable;

import lombok.Data;

@Data
public class ContentProvider implements Serializable {

	private static final long serialVersionUID = 393279413554877213L;

	private String uuid;
    
    private String name;
    
    private String copyright;
}
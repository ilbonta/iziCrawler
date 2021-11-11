package ch.bnt.izicrawler.model.dec;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Image implements Serializable {

	private static final long serialVersionUID = -4373889664094340890L;

	private String uuid;
	
	private String type;
	
	private int order;

    private String hash;
    
    private int size;

}
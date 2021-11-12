package ch.bnt.izicrawler.model.dec;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Audio extends Multimedia {

	private static final long serialVersionUID = -612793025148353449L;
	
    private String uuid;
    
    private String type;
    
    private int duration;
    
    private int order;
    
    private String hash;
    
    private int size;

}
package ch.bnt.izicrawler.model.dec;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Contacts implements Serializable {
	
	private static final long serialVersionUID = 435472549699201094L;
	
	private String phone_number;
	
    private String website;
    
    private String country;
    
    private String city;
    
    private String address;

}
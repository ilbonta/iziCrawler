package ch.bnt.izicrawler.model.dec;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Location implements Serializable {

	private static final long serialVersionUID = -695585175954929803L;
	
    private String ip;
        
    private double altitude;
	
    private double latitude;
    
    private double longitude;

    private String city_uuid;
    
    private String country_code;
    
    private String country_uuid;

}
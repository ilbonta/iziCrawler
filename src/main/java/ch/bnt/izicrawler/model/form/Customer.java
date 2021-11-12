package ch.bnt.izicrawler.model.form;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import ch.bnt.izicrawler.model.dec.Schedule;
import lombok.Data;

@Data
public class Customer implements Serializable {

	private static final long serialVersionUID = -3066638847862917244L;

	private String address;

	private String phone;

	private String website;

	private String countryCode;

	private String city;

	private Double altitude;
	private Double latitude;
	private Double longitude;

	private Schedule schedule;

	// Language	
	private Map<String, String> titles;
	private Map<String, String> summaries;
	private Map<String, String> descriptions;
	
	public Customer() {
		init();
	}

	private void init() {
		titles = new HashMap<>();
		summaries = new HashMap<>();
		descriptions = new HashMap<>();		
	}
}

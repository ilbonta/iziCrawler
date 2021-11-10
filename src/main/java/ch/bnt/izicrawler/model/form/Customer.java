package ch.bnt.izicrawler.model.form;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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

//	private 
}

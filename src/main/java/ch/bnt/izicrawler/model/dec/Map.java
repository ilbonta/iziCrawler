package ch.bnt.izicrawler.model.dec;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Map implements Serializable {

	private static final long serialVersionUID = -7140837201084021953L;
	
	private String bounds;

}
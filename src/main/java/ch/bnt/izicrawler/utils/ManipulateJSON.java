package ch.bnt.izicrawler.utils;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ManipulateJSON {
	
	public static String printReadableJSON(ResponseEntity<String> response) {		
		String body = response.getBody().substring(1, response.getBody().length()).replace("\\", "");		
		JSONObject json = new JSONObject(body);
		
		log.info(response.getBody().replace("\\", ""));
		
		return json.toString(4);
	}

}

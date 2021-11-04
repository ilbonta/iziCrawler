package ch.bnt.izicrawler.utils;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ManipulateJSON {
	
	public static void printReadableJSON(ResponseEntity<String> response) {
		JSONObject json = new JSONObject(response); // Convert text to object
		log.info(json.toString(4));
	
		log.info(response.getBody().replace("\\", ""));
	}

}

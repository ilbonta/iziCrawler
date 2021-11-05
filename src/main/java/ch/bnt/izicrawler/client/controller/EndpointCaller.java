package ch.bnt.izicrawler.client.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import ch.bnt.izicrawler.model.IziObject;
import ch.bnt.izicrawler.model.QuerySearchObj;
import ch.bnt.izicrawler.utils.Globals;
import ch.bnt.izicrawler.utils.ManipulateJSON;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class EndpointCaller {

	@Autowired private RestTemplate restTemplate;
	
	public void searchForCity(RestTemplate restTemplate) {

		// get "Geneva" data (uuid=10dc8fe9-1905-4084-b143-63af8486bca7)
		String url = Globals.SEARCH_CITY_ENDPOINT + "geneva";
		
//		ResponseEntity<String> response = restTemplate.getForEntity(Globals.CITY_ENDPOINT + "geneva", String.class);	
//		ManipulateJSON.printReadableJSON(response);
	
		QuerySearchObj[] cities = restTemplate.getForObject(url, QuerySearchObj[].class);
		
		for (int i = 0; i < cities.length; i++) {
			QuerySearchObj city = cities[i];			
			log.info("============= " +city.toString());
		}
	}
	
	public String searchForMuseum(ResultBox rb) {
		
		String url = Globals.SEARCH_MUSEUM_ENDPOINT +rb.getTitle();
		
		// Get JSON
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		String body = response.getBody().trim();
		int start = body.indexOf("[");
		int end = body.lastIndexOf("]");
		body = body.substring(start+1, end).trim();
		JSONObject json = new JSONObject(body);
		System.out.println(body);

		log.info("============= " +response.getBody().replace("\\", ""));
		
		rb.setJson(json);
		rb.setJsonString(body);
		
		// Get Object
		QuerySearchObj[] museumArray = restTemplate.getForObject(url, QuerySearchObj[].class);
		QuerySearchObj io = museumArray[0];
		
		rb.setQuerySearch(io);
		
		// Info
		log.info("============= Title: {}", io.getTitle());
		log.info("============= Disponible languages: {}", io.getLanguages().toString());
		
		// Serialize on FS
		ManipulateJSON.persistIziObject(rb);
		
		return rb.getJsonString();
	}
	
	public void getMuseumByUuid(RestTemplate restTemplate) {
		
	}
	
	public void getCustomerBanner(RestTemplate restTemplate) {
		
	}
	
	public void getMuseumImages(RestTemplate restTemplate) {
		
	}
	
//	private void generateResponseJSON() {
//		
//	}

	public void getCityByUuid() {
//		ResponseEntity<String> response = restTemplate.getForEntity("https://api.izi.travel/mtgobjects/7061495d-f2bf-43e2-9f3b-e232b2a921b9?languages=en", String.class);
//		ManipulateJSON.printReadableJSON(response);		
		
		IziObject[] izis = restTemplate.getForObject("https://api.izi.travel/mtgobjects/7061495d-f2bf-43e2-9f3b-e232b2a921b9?languages=en", IziObject[].class);

		for (int i = 0; i < izis.length; i++) {
			IziObject izi = izis[i];	
			log.info("============= " +izi.toString());
		}
	}

}

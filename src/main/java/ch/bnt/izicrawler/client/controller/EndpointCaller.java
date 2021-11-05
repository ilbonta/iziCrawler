package ch.bnt.izicrawler.client.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import ch.bnt.izicrawler.model.IziObject;
import ch.bnt.izicrawler.model.QuerySearch;
import ch.bnt.izicrawler.utils.Globals;
import ch.bnt.izicrawler.utils.ManipulateJSON;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class EndpointCaller {

	@Autowired private RestTemplate restTemplate;
	
	public void searchForCity(RestTemplate restTemplate) {
		
		String url = Globals.SEARCH_CITY_ENDPOINT + "geneva";
		
//		ResponseEntity<String> response = restTemplate.getForEntity(Globals.CITY_ENDPOINT + "geneva", String.class);	
//		ManipulateJSON.printReadableJSON(response);
	
		QuerySearch[] cities = restTemplate.getForObject(url, QuerySearch[].class);
		
		for (int i = 0; i < cities.length; i++) {
			QuerySearch city = cities[i];			
			log.info(city.toString());
		}
	}
	
	public String searchForMuseum(ResultBox rb) {
		
		String url = Globals.SEARCH_MUSEUM_ENDPOINT +rb.getTitle();
				
		ManipulateJSON.persist(rb);
		
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);				
		rb.setResultJSON(ManipulateJSON.printReadableJSON(response));
		
		IziObject[] museumArray = restTemplate.getForObject(url, IziObject[].class);
		rb.setIziObject(museumArray[0]);
		
		return rb.getResultJSON(); 

	}
	
	public void getMuseumByUuid(RestTemplate restTemplate) {
		
	}
	
	public void getCustomerBanner(RestTemplate restTemplate) {
		
	}
	
	public void getCustomerIcon(RestTemplate restTemplate) {
		
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
			log.info(izi.toString());
		}
	}

}

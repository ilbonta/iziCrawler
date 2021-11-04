package ch.bnt.izicrawler.client.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import ch.bnt.izicrawler.model.QuerySearch;
import ch.bnt.izicrawler.model.IZIObject;
import ch.bnt.izicrawler.utils.Globals;
import ch.bnt.izicrawler.utils.ManipulateJSON;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class SearchController {
	
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
	
	public void searchForMuseum(RestTemplate restTemplate) {
		
		String url = Globals.SEARCH_MUSEUM_ENDPOINT + "Musée international d'horlogerie";
		
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);	
		ManipulateJSON.printReadableJSON(response);
		
//		City[] cities = restTemplate.getForObject(Globals.SEARCH_CITY_ENDPOINT + "horloge", City[].class);
//		
//		for (int i = 0; i < cities.length; i++) {
//			City city = cities[i];			
//			log.info(city.toString());
//		}
		String folderName = "MIH";
		serializeToFileInFolder(folderName);
		
	}
	
	private void serializeToFileInFolder(String folderName) {
		// TODO Auto-generated method stub
		
	}

	public void getCityByUuid(RestTemplate restTemplate) {
//		ResponseEntity<String> response = restTemplate.getForEntity("https://api.izi.travel/mtgobjects/7061495d-f2bf-43e2-9f3b-e232b2a921b9?languages=en", String.class);
//		ManipulateJSON.printReadableJSON(response);		
		
		IZIObject[] izis = restTemplate.getForObject("https://api.izi.travel/mtgobjects/7061495d-f2bf-43e2-9f3b-e232b2a921b9?languages=en", IZIObject[].class);

		for (int i = 0; i < izis.length; i++) {
			IZIObject izi = izis[i];	
			log.info(izi.toString());
		}
	}

	public void getMuseumByUuid(RestTemplate restTemplate) {
		
	}
	
	public void getCustomerBanner(RestTemplate restTemplate) {
		
	}
	
	public void getCustomerIcon(RestTemplate restTemplate) {
		
	}

}

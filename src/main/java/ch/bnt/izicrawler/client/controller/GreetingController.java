package ch.bnt.izicrawler.client.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import ch.bnt.izicrawler.utils.Globals;

@Controller
public class GreetingController {

	@Autowired private SearchController searchController;	
	@Autowired private RestTemplate restTemplate;

	@GetMapping("/greeting")
	public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
		model.addAttribute("name", name);
		
		caller();
		
		return "greeting";
	}
	
	public void caller() {
		restTemplate.getInterceptors().add(new ClientHttpRequestInterceptor() {
			@Override
			public org.springframework.http.client.ClientHttpResponse intercept(
					org.springframework.http.HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
					throws IOException {
				request.getHeaders().set("X-IZI-API-KEY", Globals.API_KEY);
				return execution.execute(request, body);
			}
		});
		
		
		// search for "Geneva"
//		searchController.searchForObject(restTemplate);

		// search for "horloge"
		searchController.searchForMuseum(restTemplate);
		
		// get "Geneva" data (uuid=10dc8fe9-1905-4084-b143-63af8486bca7)
//		searchController.getCityByUuid(restTemplate);
		
		

		// 
//		ResponseEntity<String> response = restTemplate.getForEntity(Globals.CITY_ENDPOINT + "geneva", String.class);	
//		
//		response.getBody().
		
//		// Print output
//		printReadableJSON(response);
	
//		City[] cities = restTemplate.getForObject(Globals.CITY_ENDPOINT + "geneva", City[].class);
//		
//		for (int i = 0; i < cities.length; i++) {
//			City city = cities[i];			
//			log.info(city.toString());
//		}
		
	}

//	private void printReadableJSON(ResponseEntity<String> response) {
//		JSONObject json = new JSONObject(response); // Convert text to object
//		log.info(json.toString(4));
//
//		log.info(response.getBody().replace("\\", ""));
//	}

}
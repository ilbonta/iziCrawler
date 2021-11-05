package ch.bnt.izicrawler.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ClientController {

	@Autowired private EndpointCaller ep;

	@GetMapping("/")
	public String greeting(Model model) {
		model.addAttribute("name", "Benvenuto");
		
		return "entryPoint";
	}
	
	@GetMapping("/museum")
	public ModelAndView getMuseum(@RequestParam(name="title") String title) {
		ModelAndView mav =  new ModelAndView("result");
		
		ResultBox rb =  new ResultBox(title);
		
		String json = "ok";
				ep.searchForMuseum(rb);
//				.toString();
		
		
		

		
		// GET image
		// Serialize
		
		
		
		mav.addObject("json", json);
		
//		ManipulateJSON.serializeToFileInFolder(objToPersist, fileName);

		// search for "Geneva"
//		searchController.searchForObject(restTemplate);

		// search for "horloge"
		
		
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
		
		
		
		
		return mav;
	}

//	private void printReadableJSON(ResponseEntity<String> response) {
//		JSONObject json = new JSONObject(response); // Convert text to object
//		log.info(json.toString(4));
//
//		log.info(response.getBody().replace("\\", ""));
//	}

}
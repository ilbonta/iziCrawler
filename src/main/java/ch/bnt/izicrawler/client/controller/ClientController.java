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
		
		String json = ep.searchForMuseum(rb);
		
		mav.addObject("json", json);	
		
		return mav;
	}

//	private void printReadableJSON(ResponseEntity<String> response) {
//		JSONObject json = new JSONObject(response); // Convert text to object
//		log.info(json.toString(4));
//
//		log.info(response.getBody().replace("\\", ""));
//	}

}
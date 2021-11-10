package ch.bnt.izicrawler.client.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ch.bnt.izicrawler.utils.Globals;
import ch.bnt.izicrawler.utils.ManipulateJSON;

@Controller
public class ClientController {

	@Autowired private EndpointCaller ep;

	@GetMapping("/")
	public String greeting(Model model) {
		model.addAttribute("name", "Benvenuto");
		
		return "entryPoint";
	}
	
	@GetMapping("/museum")
	public ModelAndView getMuseum(@RequestParam(name="searchKeyWord") String searchKeyWord) {
		ModelAndView mav =  new ModelAndView("result");
		
		String json = ep.searchForMuseum(searchKeyWord);
		
		mav.addObject("json", json);	
		
		return mav;
	}
		
	@GetMapping("/uuidMin")
	public ModelAndView getByUuidMinimal(@RequestParam(name="uuid") String uuid) {
		ModelAndView mav =  new ModelAndView("result");
		
		String json = ep.getByUuidMinimal(uuid);
		
		mav.addObject("json", json);	
		
		return mav;
	}
	
	@GetMapping("/uuidAll")
	public ModelAndView getByUuidAll(@RequestParam(name="uuid") String uuid) {
		ModelAndView mav =  new ModelAndView("result");
		
		String json = ep.getByUuidAll(uuid);
		
		mav.addObject("json", json);	
		
		return mav;
	}
	
	@GetMapping("/media")
	public ResponseEntity<String> getMediaByUuid(
			@RequestParam(name="contentProviderUuid") String contentProviderUuid,
			@RequestParam(name="imageUuid") String imageUuid,
			@RequestParam(name="type") String type,
			@RequestParam(name="ext") String ext) {
		
        String folderName = "temp";
		
		byte[] imageBytes = ep.getMedia(contentProviderUuid, imageUuid, ext);
		
		String jsonFileName = folderName +"_" +type +"." +ext;	
		String filePath = Globals.MAIN_OUTPUT_FOLDER +folderName +File.separator +jsonFileName;
		ManipulateJSON.persistIziObjectImage(imageBytes, filePath, ext);
		
		return new ResponseEntity<String>("File Downloaded Successfully", HttpStatus.OK);
	}
	
	@GetMapping("/publisher")
	public ModelAndView getPublisherByUuid(@RequestParam(name="uuid") String uuid) {
		ModelAndView mav =  new ModelAndView("result");
		
		String json = ep.getPublisherByUuid(uuid);
		
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
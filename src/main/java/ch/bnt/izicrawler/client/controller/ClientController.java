package ch.bnt.izicrawler.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ClientController {

	@Autowired private EndpointCaller endpointCaller;
	@Autowired private MuseumCaller museumCaller;

	@GetMapping("/")
	public String greeting(Model model) {
		model.addAttribute("name", "Benvenuto");
		
		return "entryPoint";
	}
	
	@GetMapping("/museum")
	public ModelAndView getMuseum(@RequestParam(name="searchKeyWord") String searchKeyWord) {
		ModelAndView mav =  new ModelAndView("result");
		
		String json = museumCaller.searchForMuseum(searchKeyWord);
		
		mav.addObject("json", json);	
		
		return mav;
	}
		
	@GetMapping("/uuidMin")
	public ModelAndView getByUuidMinimal(@RequestParam(name="uuid") String uuid, @RequestParam(name="lang") String lang) {
		ModelAndView mav =  new ModelAndView("result");
		
		String json = endpointCaller.getByUuidMinimal(uuid, lang);
		
		mav.addObject("json", json);	
		
		return mav;
	}
	
	@GetMapping("/uuidAll")
	public ModelAndView getByUuidAll(@RequestParam(name="uuid") String uuid) {
		ModelAndView mav =  new ModelAndView("result");
		
		String json = endpointCaller.getByUuidAll(uuid);
		
		mav.addObject("json", json);	
		
		return mav;
	}
	
//	@GetMapping("/media")
//	public ResponseEntity<String> getMediaByUuid(
//			@RequestParam(name="contentProviderUuid") String contentProviderUuid,
//			@RequestParam(name="imageUuid") String imageUuid,
//			@RequestParam(name="type") String type,
//			@RequestParam(name="ext") String ext) {
//		
//        String folderName = "temp";
//		
//		byte[] imageBytes = endpointCaller.getMedia(contentProviderUuid, imageUuid, ext);
//		
//		String jsonFileName = folderName +"_" +type +"." +ext;	
//		String filePath = Globals.MAIN_OUTPUT_FOLDER +folderName +File.separator +"img" +File.separator +jsonFileName;
//		ManipulateData.persistIziObjectMedia(imageBytes, filePath, ext);
//		
//		return new ResponseEntity<String>("File Downloaded Successfully", HttpStatus.OK);
//	}
	
	@GetMapping("/publisher")
	public ModelAndView getPublisherByUuid(@RequestParam(name="uuid") String uuid) {
		ModelAndView mav =  new ModelAndView("result");
		
		String json = endpointCaller.getPublisherByUuid(uuid);
		
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
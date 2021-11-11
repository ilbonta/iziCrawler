package ch.bnt.izicrawler.client.controller;

import java.lang.reflect.Array;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import ch.bnt.izicrawler.model.IziObject;
import ch.bnt.izicrawler.model.QuerySearchObj;
import ch.bnt.izicrawler.utils.Globals;
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
			log.info("============= CITY: " +city.toString());
		}
	}

	public String getPublisherByUuid(String uuid) {
		
		ResultBox rb =  new ResultBox();
		
		ResponseEntity<String> response = restTemplate.getForEntity(Globals.GET_PUBLISHER +uuid +"?languages=nl,en&except=children", String.class);
		log.debug("============= " +response.getBody());
				
		String body = extractFromArray(response);

		rb.setJsonString(body);
		
		return rb.getJsonString();
		
	}
	
	public IziObject getIziObjectByUuid(String uuid, String lang) {
		
		IziObject[] izi = restTemplate.getForObject("https://api.izi.travel/mtgobjects/" +uuid +"?languages=" +lang, IziObject[].class);
		
		return izi[0];
	}

	public String getByUuidMinimal(String uuid, String lang) {
		
		ResultBox rb =  new ResultBox();
		
		ResponseEntity<String> response = restTemplate.getForEntity("https://api.izi.travel/mtgobjects/" +uuid +"?languages=" +lang, String.class);
		log.debug("============= " +response.getBody());
		
		String body = extractFromArray(response);
		
		rb.setJsonString(body);
		
		return rb.getJsonString();
		
	}
	
	protected String extractFromArray(ResponseEntity<String> response) {
		String body = response.getBody().trim();
		int start = body.indexOf("[");
		int end = body.lastIndexOf("]");
		body = body.substring(start+1, end).trim();
		System.out.println(body);
		
		return body;
	}
	
	public String getByUuidAll(String uuid) {
		
		ResultBox rb =  new ResultBox();
		
		ResponseEntity<String> response = restTemplate.getForEntity("https://api.izi.travel/mtgobjects/" +uuid +"?languages=en&includes=all,city,country&except=translations,download", String.class);
		log.debug("============= " +response.getBody());

		String body = extractFromArray(response);

		rb.setJsonString(body);
		
		return rb.getJsonString();
		
	}
	
	protected byte[] getMedia(String contentProviderUuid, String imageUuid, String ext) {

//		String url = Globals.GET_MEDIA +contentProviderUuid +"/" +imageUuid +ext;
		String url = Globals.GET_MEDIA +contentProviderUuid +"/" +imageUuid +Globals.IMG_SIZE_800x600 +"." +ext;
		log.debug("============= IMG URL: {}", url);		

//		String jsonFileName = folderName +"_" +type +"." +ext;	
//		String folderPath = Globals.MAIN_OUTPUT_FOLDER +folderName;	
		
		byte[] imageBytes = restTemplate.getForObject(url, byte[].class);
		
//		ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
//	    try {
//	    	BufferedImage bImage2 = ImageIO.read(bis);
//			ImageIO.write(bImage2, ext, new File(folderPath +File.separator +jsonFileName));
//			log.debug("============= IMG SAVED: {}", folderPath +File.separator +jsonFileName);
//		} catch (IOException e) {
//			log.error("IO error", e);
//		}
		
		return imageBytes;		
	}
	
//	public byte[] getMapImage(String contentProviderUuid, String imageUuid) {
//		String url = Globals.GET_MEDIA +contentProviderUuid +"/" +imageUuid +"." +Globals.IMG_LOGO_EXT;
//		log.debug("============= IMG URL: {}", url);
//		
//		byte[] imageBytes = restTemplate.getForObject(url, byte[].class);
//		
//		return imageBytes;		
//	}
//	
//	public byte[] getMediaBrand(String contentProviderUuid, String imageUuid) {
//		String url = Globals.GET_MEDIA +contentProviderUuid +"/" +imageUuid +"." +Globals.IMG_BRAND_LOGO_EXT;
//		log.debug("============= IMG URL: {}", url);
//		
//		byte[] imageBytes = restTemplate.getForObject(url, byte[].class);
//		
//		return imageBytes;		
//	}
//
//	public void getCityByUuid() {
//		
////		ResponseEntity<String> response = restTemplate.getForEntity("https://api.izi.travel/mtgobjects/7061495d-f2bf-43e2-9f3b-e232b2a921b9?languages=en", String.class);
////		ManipulateJSON.printReadableJSON(response);
//		String url = "https://api.izi.travel/mtgobjects/ebbd4c2a-f6b2-46b5-aaee-2347707152d8?languages=en";
//		
//		IziObject[] izis = restTemplate.getForObject(url, IziObject[].class);
//
//		for (int i = 0; i < izis.length; i++) {
//			IziObject izi = izis[i];
//			log.info("============= " +izi.toString());
//		}
//	}

}

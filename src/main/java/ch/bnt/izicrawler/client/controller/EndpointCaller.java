package ch.bnt.izicrawler.client.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

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
	
	public String searchForMuseum(String searchKeyWord) {
		
		ResultBox rb =  new ResultBox(searchKeyWord);
		
		String url = Globals.SEARCH_MUSEUM_ENDPOINT +rb.getSearchKeyWord();
//		String url = Globals.GET_OBJECT_ENDPOINT +"fe178907-694b-484e-b3c4-39a74cc21e58" +Globals.GET_OBJECT_ENDPOINT_GET_PARAM;
				
// Get JSON
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		String body = extractFromArray(response);

		log.info("============= " +response.getBody().replace("\\", ""));
		
		rb.setJson(new JSONObject(body));
		rb.setJsonString(body);
		
// Get Object
		QuerySearchObj[] museumArray = restTemplate.getForObject(url, QuerySearchObj[].class);
		QuerySearchObj museum = museumArray[0];
		
		rb.setQuerySearchObject(museum);
		
		// Info
		log.info("============= Title: {}", museum.getTitle());
		log.info("============= Disponible languages: {}", museum.getLanguages().toString());
	
// Serialize on FS
		ManipulateJSON.persistIziObject(rb);
		
		// Info
//		String uuid = museum.getUuid();
//		String name = museum.getContent_provider().getName();

		// Get image
		String contentProviderUuid = museum.getContent_provider().getUuid();
		String imageLogoUuid = museum.getImages().get(0).getUuid();
		String imageLogoType = museum.getImages().get(0).getType().trim().toLowerCase();
		
		getMedia(contentProviderUuid, imageLogoUuid, imageLogoType, Globals.IMG_LOGO_EXT);
		
//		logo: https://media.izi.travel/8a6db7f5-da67-4652-9172-bbe4a96f47e8/b23985b4-9d0c-4637-953b-352ccc11738d_800x600.jpg);
		
		return rb.getJsonString();
	}

	private String extractFromArray(ResponseEntity<String> response) {
		String body = response.getBody().trim();
		int start = body.indexOf("[");
		int end = body.lastIndexOf("]");
		body = body.substring(start+1, end).trim();
		System.out.println(body);
		
		return body;
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
	
	public String getPublisherByUuid(String uuid) {
		
		ResultBox rb =  new ResultBox();
		
		ResponseEntity<String> response = restTemplate.getForEntity(Globals.GET_PUBLISHER +uuid +"?languages=nl,en&except=children", String.class);
		System.out.println("============= " +response.getBody());
				
		String body = extractFromArray(response);

		rb.setJsonString(body);
		
		return rb.getJsonString();
		
	}
	
	public String getByUuidMinimal(String uuid) {
		
		ResultBox rb =  new ResultBox();
		
		ResponseEntity<String> response = restTemplate.getForEntity("https://api.izi.travel/mtgobjects/" +uuid +"?languages=en", String.class);
		System.out.println("============= " +response.getBody());
		
		String body = extractFromArray(response);
		
		rb.setJsonString(body);
		
		return rb.getJsonString();
		
	}
	
	public String getByUuidAll(String uuid) {
		
		ResultBox rb =  new ResultBox();
		
		ResponseEntity<String> response = restTemplate.getForEntity("https://api.izi.travel/mtgobjects/" +uuid +"?languages=en&includes=all,city,country&except=translations,download", String.class);
		System.out.println("============= " +response.getBody());

		String body = extractFromArray(response);

		rb.setJsonString(body);
		
		return rb.getJsonString();
		
	}
	
	public String getMedia(String contentProviderUuid, String imageUuid, String type, String ext) {

//		String url = Globals.GET_MEDIA +contentProviderUuid +"/" +imageUuid +ext;
		String url = Globals.GET_MEDIA +contentProviderUuid +"/" +imageUuid +Globals.IMG_SIZE_800x600 +"." +ext;
		
//		BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
//
//		
////		ResultBox rb =  new ResultBox();
//		
//		File file = restTemplate.execute(url, HttpMethod.GET, null, clientHttpResponse -> {
//		    File ret = File.createTempFile("logo", ".png");
//		    StreamUtils.copy(clientHttpResponse.getBody(), new FileOutputStream(ret));
//		    return ret;
//		});
		String folderName = "Museinternationaldhorlogerie";
		String jsonFileName = folderName +"_" +type +"." +ext;
		String folderPath = Globals.MAIN_OUTPUT_FOLDER +folderName;	
//		ManipulateJSON.serializeToFileInFolder(file, folderPath +File.separator +jsonFileName);
		
		byte[] imageBytes = restTemplate.getForObject(url, byte[].class);
		
		ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
	    try {
	    	BufferedImage bImage2 = ImageIO.read(bis);
			ImageIO.write(bImage2, ext, new File(folderPath +File.separator +jsonFileName) );
		} catch (IOException e) {
			log.error("IO error", e);
		}
	      System.out.println("image created");
//		System.out.println("============= " +response.getBody());
		
//		String body = extractFromArray(response);
//
//		rb.setJsonString(body);
		
		return "ok";
		
	}

	public void getCityByUuid() {
		
//		ResponseEntity<String> response = restTemplate.getForEntity("https://api.izi.travel/mtgobjects/7061495d-f2bf-43e2-9f3b-e232b2a921b9?languages=en", String.class);
//		ManipulateJSON.printReadableJSON(response);		
		
		IziObject[] izis = restTemplate.getForObject("https://api.izi.travel/mtgobjects/ebbd4c2a-f6b2-46b5-aaee-2347707152d8?languages=en", IziObject[].class);

		for (int i = 0; i < izis.length; i++) {
			IziObject izi = izis[i];
			log.info("============= " +izi.toString());
		}
	}

}

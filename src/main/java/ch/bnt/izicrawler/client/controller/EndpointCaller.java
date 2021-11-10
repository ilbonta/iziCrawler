package ch.bnt.izicrawler.client.controller;

import java.io.File;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import ch.bnt.izicrawler.model.IziObject;
import ch.bnt.izicrawler.model.QuerySearchObj;
import ch.bnt.izicrawler.model.form.Customer;
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
			log.info("============= CITY: " +city.toString());
		}
	}
	
	public String searchForMuseum(String searchKeyWord) {
		
		ResultBox rb =  new ResultBox(searchKeyWord);
		
		String url = Globals.SEARCH_MUSEUM_ENDPOINT +rb.getSearchKeyWord();
//		String url = Globals.GET_OBJECT_ENDPOINT +"fe178907-694b-484e-b3c4-39a74cc21e58" +Globals.GET_OBJECT_ENDPOINT_GET_PARAM;
				
// Get JSON
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		String body = extractFromArray(response);

		log.info("============= GET JSON: " +response.getBody().replace("\\", ""));
		
		rb.getLanguageObjMap().put("en", body);
//		rb.setJson(new JSONObject(body));
		rb.setJsonString(body);
		
// Get Object
		QuerySearchObj[] museumArray = restTemplate.getForObject(url, QuerySearchObj[].class);
		QuerySearchObj museum = museumArray[0];
		
		rb.setQuerySearchObject(museum);
		
		// Info
		String uuid = museum.getUuid();
		rb.setUuid(uuid);
//		String name = museum.getContent_provider().getName();
		log.info("============= TITLE: {}", museum.getTitle());
		
		// Language
		Set<String> langSet = museum.getLanguages().stream().collect(Collectors.toSet());
		rb.setLanguageSet(langSet);
		
		log.info("============= LANGUAGES: {}", museum.getLanguages().toString());
		Customer customer  = new Customer();
		
//		for (String lang : langSet) {	
//			//https://api.izi.travel/mtgobjects/50b5f15b-7328-4509-8250-d36a523292b3?languages=en&includes=download,city&except=publisher,children'
//			String urlLang = Globals.GET_OBJECT_ENDPOINT +rb.getUuid() +"?languages=" +lang +"&except=publisher,schedule,children";
//			log.debug(urlLang);			
//			IziObject[] izis = restTemplate.getForObject(urlLang, IziObject[].class);
//
//			IziObject izi = izis[0];
//			
//			// Info una tantum
//			ManipulateJSON.infoDataRecovery(izi, customer);
//			
//			// Content
//			if(izi.getContent()!=null) {
//				Content contents = izi.getContent().get(0);			
//				log.info("============= LANG TITLE (" +lang +"): -->" +contents.getTitle());			
//				log.info("============= LANG SUMMARY (" +lang +"): -->" +contents.getSummary());
//				log.info("============= LANG DESCR (" +lang +"): -->" +contents.getDesc());				
//			}
//			
//		}
		
// Folder name		
		// Format reference
		String folderName = museum.getContent_provider().getName();
		folderName = folderName.replaceAll("[^a-zA-Z0-9]", "");
		folderName = StringUtils.stripAccents(folderName);
		rb.setFolderName(folderName);
		log.info("============= FOLDER NAME: {}", folderName);
	
// Serialize on FS
		ManipulateJSON.manageFSFolder(rb);		
//		ManipulateJSON.persistIziObjectJSON(rb);
//		ManipulateJSON.persistIziObject(rb);

// Get logo image
//		extractLogoImg(rb);
// Get logo image
		extractBrandLogoImg(rb);
		
		return rb.getJsonString();
	}

	private void extractLogoImg(ResultBox rb) {
		QuerySearchObj qs = rb.getQuerySearchObject();
		String contentProviderUuid = qs.getContent_provider().getUuid();
		String imageLogoUuid = qs.getImages().get(0).getUuid();
		String imageLogoType = qs.getImages().get(0).getType().trim().toLowerCase();

		log.info("============= SAVE LOGO ");
		byte[] imageBytes = getMedia(contentProviderUuid, imageLogoUuid, Globals.IMG_LOGO_EXT);
		
		String jsonFileName = rb.getFolderName() +"_" +imageLogoType +"." +Globals.IMG_LOGO_EXT;	
		String filePath = Globals.MAIN_OUTPUT_FOLDER +rb.getFolderName() +File.separator +jsonFileName;
		ManipulateJSON.persistIziObjectImage(imageBytes, filePath, Globals.IMG_LOGO_EXT);
	}
	
	private void extractBrandLogoImg(ResultBox rb) {
		
//		{MEDIA_BASE_URL}/{CONTENT_PROVIDER_UUID}/{IMAGE_UUID}.jpg
		
//		https://media.izi.travel/8a6db7f5-da67-4652-9172-bbe4a96f47e8/ebbd4c2a-f6b2-46b5-aaee-2347707152d8.jpg
		
//		QuerySearchObj qs = rb.getQuerySearchObject();
//		String contentProviderUuid = qs.getContent_provider().getUuid();
//		String imageLogoUuid = qs.getImages().get(0).getUuid();
//		String imageLogoType = qs.getImages().get(0).getType().trim().toLowerCase();
//		
//		log.info("============= SAVE LOGO ");
		byte[] imageBytes = getMediaBrand("8a6db7f5-da67-4652-9172-bbe4a96f47e8", "ebbd4c2a-f6b2-46b5-aaee-2347707152d8");		
		
		String jsonFileName = rb.getFolderName() +"_brand" +"." +Globals.IMG_BRAND_LOGO_EXT;	
		String filePath = Globals.MAIN_OUTPUT_FOLDER +rb.getFolderName() +File.separator +jsonFileName;
		ManipulateJSON.persistIziObjectImage(imageBytes, filePath, Globals.IMG_BRAND_LOGO_EXT);
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
	
	public byte[] getMedia(String contentProviderUuid, String imageUuid, String ext) {

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
	
	public byte[] getMediaBrand(String contentProviderUuid, String imageUuid) {
		String url = Globals.GET_MEDIA +contentProviderUuid +"/" +imageUuid +"." +Globals.IMG_BRAND_LOGO_EXT;
		log.debug("============= IMG URL: {}", url);
		
		byte[] imageBytes = restTemplate.getForObject(url, byte[].class);
		
		return imageBytes;		
	}

	public void getCityByUuid() {
		
//		ResponseEntity<String> response = restTemplate.getForEntity("https://api.izi.travel/mtgobjects/7061495d-f2bf-43e2-9f3b-e232b2a921b9?languages=en", String.class);
//		ManipulateJSON.printReadableJSON(response);
		String url = "https://api.izi.travel/mtgobjects/ebbd4c2a-f6b2-46b5-aaee-2347707152d8?languages=en";
		
		IziObject[] izis = restTemplate.getForObject(url, IziObject[].class);

		for (int i = 0; i < izis.length; i++) {
			IziObject izi = izis[i];
			log.info("============= " +izi.toString());
		}
	}

}

package ch.bnt.izicrawler.client.controller;

import java.io.File;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import ch.bnt.izicrawler.model.IziObject;
import ch.bnt.izicrawler.model.QuerySearchObj;
import ch.bnt.izicrawler.model.dec.Audio;
import ch.bnt.izicrawler.model.dec.Child;
import ch.bnt.izicrawler.model.dec.Content;
import ch.bnt.izicrawler.model.dec.Image;
import ch.bnt.izicrawler.model.form.Customer;
import ch.bnt.izicrawler.utils.Globals;
import ch.bnt.izicrawler.utils.ManipulateData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class MuseumCaller extends EndpointCaller {

	@Autowired private RestTemplate restTemplate;
	
	public String searchForMuseum(String searchKeyWord) {
		
		ResultBox rb =  new ResultBox(searchKeyWord);
		
		String url = Globals.SEARCH_MUSEUM_ENDPOINT +rb.getSearchKeyWord();
		log.debug("============= SEARCH FOR UUID: -->" +rb.getSearchKeyWord());
				
// Get JSON
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		String body = extractFromArray(response);

		log.info("============= GET JSON: " +response.getBody().replace("\\", ""));
		
		rb.getLanguageObjMap().put("en", body);
		rb.setJsonString(body);
		
// Get Object
		QuerySearchObj[] museumArray = restTemplate.getForObject(url, QuerySearchObj[].class);
		QuerySearchObj museum = museumArray[0];
		
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

		// Folder name		
		// Format reference
		String folderName = museum.getContent_provider().getName();
		folderName = folderName.replaceAll("[^a-zA-Z0-9]", "");
		folderName = StringUtils.stripAccents(folderName);
		rb.setFolderName(folderName);
		log.info("============= FOLDER NAME: {}", folderName);
		
		// Serialize on FS
		ManipulateData.manageFSFolder(rb);
		
		boolean once = false;
		for (String lang : langSet) {
			log.debug("============= OBJ UUID (" +lang +"): -->" +rb.getUuid());
			//https://api.izi.travel/mtgobjects/50b5f15b-7328-4509-8250-d36a523292b3?languages=en&includes=download,city&except=publisher,children'
//			String urlLang = Globals.GET_OBJECT_ENDPOINT +rb.getUuid() +"?languages=" +lang +"&except=publisher,schedule,children";
			String urlLang = Globals.GET_OBJECT_ENDPOINT +rb.getUuid() +"?languages=" +lang +"&except=publisher,schedule";
			log.debug(urlLang);			
			IziObject[] izis = restTemplate.getForObject(urlLang, IziObject[].class);

			IziObject izi = izis[0];
			
			// Info una tantum
//			ManipulateData.infoDataRecovery(izi, customer);

			if(!once) {
//				extractLangImage(rb, izi);
				once = true;
			}
			
			// Lang Content
//			ManipulateData.infoLangDataRecovery(izi, customer, lang);

			String jsonFileName = rb.getFolderName() +"_obj_" +lang +".json";
			ManipulateData.persistIziObjectJSON(rb, new JSONObject(izi).toString(), jsonFileName);
			
			// Permanente
			organizeChildren(rb, izi, lang);
		}	

		String jsonFileName = rb.getFolderName() +"_search.json";
//		ManipulateData.persistIziObjectJSON(rb, rb.getJsonString(), jsonFileName);
//		ManipulateData.persistIziObject(rb, museum);
		
		String contentProviderUuid = museum.getContent_provider().getUuid();
// Get logo image
		Image image = museum.getImages().get(0);
//		extractLogoImg(rb, image, contentProviderUuid);
// Get logo image
		Image image2 = museum.getPublisher().getImages().get(0);
//		extractBrandLogoImg(rb, image2, contentProviderUuid);
		
		return rb.getJsonString();
	}

	private void organizeChildren(ResultBox rb, IziObject izi, String lang) {
		if(izi.getContent()!=null && izi.getContent().size() >0) {
			Content content = izi.getContent().get(0);			
			if(content.getChildren()!=null && content.getChildren().size()>0) {
				Integer i = 0;
				for(Child child : content.getChildren()) {
					boolean once = false;
					if("published".equals(child.getStatus())) {
						if("en".equals(child.getLanguage())) {
							// Increment
							String inc = i<10 ? "0"+i : ""+i;
							
							// Id
							String exhibUuid = child.getUuid();
							log.debug("============= MEDIA UUID (" +lang +"): -->" +exhibUuid);
							
							// JSON
							IziObject izi2 = getIziObjectByUuid(exhibUuid, lang);
							ManipulateData.persistExhibPermanentIziObject(rb, new JSONObject(izi2).toString(), inc +"_" +lang +"_" +child.getTitle() +".json");
							Content content2 = izi2.getContent().get(0);	
	
							// Title
							content2.getTitle();
							log.info("============= LANG TITLE (" +lang +"): -->" +child.getTitle());
							
							// Description
							content2.getDesc();
							log.debug("============= LANG DESCR (" +lang +"): -->" +child.getDesc());
							
							// Image
							if(!once) {
								String contentProviderUuid = izi.getContent_provider().getUuid();
								
								if(content2.getImages()!=null && content2.getImages().size() >0) {
									for(Image image : content2.getImages()) {
										String imageLogoUuid = image.getUuid();

										if("story".equals(image.getType())) {
											String imageLogoType = image.getType().trim().toLowerCase();
						
											log.debug("============= SAVE STORY IMAGE ");
											byte[] imageBytes = getImage(contentProviderUuid, imageLogoUuid, Globals.IMG_SIZE_800x600, Globals.IMG_LOGO_EXT);
											
											String jsonFileName = inc +"_" +child.getTitle() +"_" +imageLogoType +"_" +"." +Globals.IMG_LOGO_EXT;	
											String filePath = rb.getFolderPath_EXHIB_PERMANENT() +File.separator +jsonFileName;
											ManipulateData.persistIziObjectMedia(imageBytes, filePath, Globals.IMG_LOGO_EXT);					
										}
									}
								}
								once = true;
							}
							
							// Audio
							Audio audio = content2.getAudio().get(0);
							String audioUuid = audio.getUuid();
							String contentProviderUuid = izi.getContent_provider().getUuid();
							byte[] audioBytes = getMedia(contentProviderUuid, audioUuid, Globals.AUDIO_EXT);
							
							String mediaFile = rb.getFolderPath_EXHIB_PERMANENT() +File.separator +inc +"_" +lang +"_" +child.getTitle() +"." +Globals.AUDIO_EXT;
							ManipulateData.persistIziObjectAudio(audioBytes, mediaFile, Globals.AUDIO_EXT);							
						}
					}
					i = i+1;
				}				
			}			
		}		
	}

	private void extractLangImage(ResultBox rb, IziObject izi) {
		String contentProviderUuid = izi.getContent_provider().getUuid();
		
		if(izi.getContent()!=null && izi.getContent().size() >0) {
			Content content = izi.getContent().get(0);
			if(content.getImages()!=null && content.getImages().size() >0) {
				for(Image image : content.getImages()) {
					String imageLogoUuid = image.getUuid();

					if("story".equals(image.getType())) {
						String imageLogoType = image.getType().trim().toLowerCase();
	
						log.debug("============= SAVE STORY IMAGE ");
						byte[] imageBytes = getImage(contentProviderUuid, imageLogoUuid, Globals.IMG_SIZE_800x600, Globals.IMG_LOGO_EXT);
						
						String jsonFileName = rb.getFolderName() +"_" +imageLogoType +"_" +image.getOrder() +"." +Globals.IMG_LOGO_EXT;	
						String filePath = rb.getFolderPath_IMG() +File.separator +jsonFileName;
						ManipulateData.persistIziObjectMedia(imageBytes, filePath, Globals.IMG_LOGO_EXT);					
					}
					else if("map".equals(image.getType())) {
						log.debug("============= SAVE MAP IMAGE ");
						byte[] imageBytes = getMedia(contentProviderUuid, imageLogoUuid, Globals.IMG_LOGO_EXT);		
						
						String jsonFileName = rb.getFolderName() +"_map_" +image.getOrder()+"." +Globals.IMG_BRAND_LOGO_EXT;	
						String filePath = rb.getFolderPath_IMG() +File.separator +jsonFileName;
						ManipulateData.persistIziObjectMedia(imageBytes, filePath, Globals.IMG_BRAND_LOGO_EXT);
					}
				}
			}
		}
	}

	private void extractLogoImg(ResultBox rb, Image image, String contentProviderUuid) {
		String imageLogoUuid = image.getUuid();
		String imageLogoType = image.getType().trim().toLowerCase();

		log.debug("============= SAVE LOGO IMAGE");
		byte[] imageBytes = getImage(contentProviderUuid, imageLogoUuid, Globals.IMG_SIZE_800x600, Globals.IMG_LOGO_EXT);
		
		String jsonFileName = rb.getFolderName() +"_" +imageLogoType +"." +Globals.IMG_LOGO_EXT;
		String filePath = rb.getFolderPath_IMG() +File.separator +jsonFileName;
		ManipulateData.persistIziObjectMedia(imageBytes, filePath, Globals.IMG_LOGO_EXT);
	}
	
	private void extractBrandLogoImg(ResultBox rb, Image image, String contentProviderUuid) {
		String imageBrandUuid = image.getUuid();
		
		log.debug("============= SAVE BRAND IMAGE ");
		byte[] imageBytes = getMedia(contentProviderUuid, imageBrandUuid, Globals.IMG_BRAND_LOGO_EXT);		
		
		String jsonFileName = rb.getFolderName() +"_brand" +"." +Globals.IMG_BRAND_LOGO_EXT;	
		String filePath = rb.getFolderPath_IMG() +File.separator +jsonFileName;
		ManipulateData.persistIziObjectMedia(imageBytes, filePath, Globals.IMG_BRAND_LOGO_EXT);
	}
	
//	public String getPublisherByUuid(String uuid) {
//		
//		ResultBox rb =  new ResultBox();
//		
//		ResponseEntity<String> response = restTemplate.getForEntity(Globals.GET_PUBLISHER +uuid +"?languages=nl,en&except=children", String.class);
//		System.out.println("============= " +response.getBody());
//				
//		String body = extractFromArray(response);
//
//		rb.setJsonString(body);
//		
//		return rb.getJsonString();
//		
//	}
//	
//	public String getByUuidMinimal(String uuid) {
//		
//		ResultBox rb =  new ResultBox();
//		
//		ResponseEntity<String> response = restTemplate.getForEntity("https://api.izi.travel/mtgobjects/" +uuid +"?languages=en", String.class);
//		System.out.println("============= " +response.getBody());
//		
//		String body = extractFromArray(response);
//		
//		rb.setJsonString(body);
//		
//		return rb.getJsonString();
//		
//	}
//	
//	public String getByUuidAll(String uuid) {
//		
//		ResultBox rb =  new ResultBox();
//		
//		ResponseEntity<String> response = restTemplate.getForEntity("https://api.izi.travel/mtgobjects/" +uuid +"?languages=en&includes=all,city,country&except=translations,download", String.class);
//		System.out.println("============= " +response.getBody());
//
//		String body = extractFromArray(response);
//
//		rb.setJsonString(body);
//		
//		return rb.getJsonString();
//		
//	}
	
	public byte[] getMedia(String contentProviderUuid, String mediaUuid, String ext) {
		String url = Globals.GET_MEDIA +contentProviderUuid +"/" +mediaUuid +"." +ext;
		log.debug("============= MEDIA URL: {}", url);
		
		byte[] imageBytes = restTemplate.getForObject(url, byte[].class);
		
		return imageBytes;		
	}
	
	public byte[] getImage(String contentProviderUuid, String mediaUuid, String size, String ext) {
        String url = Globals.GET_MEDIA +contentProviderUuid +"/" +mediaUuid +size +"." +ext;
        log.debug("============= IMG URL: {}", url); 
        
        byte[] imageBytes = restTemplate.getForObject(url, byte[].class);
        
        return imageBytes;        
    }

	public void getCityByUuid() {
		
//		ResponseEntity<String> response = restTemplate.getForEntity("https://api.izi.travel/mtgobjects/7061495d-f2bf-43e2-9f3b-e232b2a921b9?languages=en", String.class);
//		ManipulateData.printReadableJSON(response);
		String url = "https://api.izi.travel/mtgobjects/ebbd4c2a-f6b2-46b5-aaee-2347707152d8?languages=en";
		
		IziObject[] izis = restTemplate.getForObject(url, IziObject[].class);

		for (int i = 0; i < izis.length; i++) {
			IziObject izi = izis[i];
			log.debug("============= " +izi.toString());
		}
	}

}

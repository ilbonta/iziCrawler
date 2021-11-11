package ch.bnt.izicrawler.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

import ch.bnt.izicrawler.client.controller.ResultBox;
import ch.bnt.izicrawler.model.IziObject;
import ch.bnt.izicrawler.model.QuerySearchObj;
import ch.bnt.izicrawler.model.dec.Content;
import ch.bnt.izicrawler.model.form.Customer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ManipulateJSON {
	
	/**
	 * |_<main_output_folder>
	 * 		|_<foldername>
	 * 			|_img
	 * 			|_json
	 * 			|_exhib
	 * 				|_permanent
	 * 
	 * @param rb
	 */
	public static void manageFSFolder(ResultBox rb) {
		String folderPath = Globals.MAIN_OUTPUT_FOLDER +rb.getFolderName();
		
		// Generate Folder
		new File(folderPath).mkdirs();
		log.debug("============= FOLDER CREATED: {}", folderPath);

		rb.setFolderPath_IMG(folderPath +File.separator +"img");
		new File(rb.getFolderPath_IMG()).mkdirs();
		log.debug("============= SUBFOLDER CREATED: {}", rb.getFolderPath_IMG());
		
		rb.setFolderPath_JSON(folderPath +File.separator +"json");
		new File(rb.getFolderPath_JSON()).mkdirs();
		log.debug("============= SUBFOLDER CREATED: {}", rb.getFolderPath_JSON());
		
		rb.setFolderPath_EXHIB(folderPath +File.separator +"exhib");
		new File(rb.getFolderPath_EXHIB()).mkdirs();
		log.debug("============= SUBFOLDER CREATED: {}", rb.getFolderPath_EXHIB());

		rb.setFolderPath_EXHIB_PERMANENT(folderPath +File.separator +"exhib" +File.separator +"permanent");	
		new File(rb.getFolderPath_EXHIB_PERMANENT()).mkdirs();
		log.debug("============= SUBSUBFOLDER CREATED: {}", rb.getFolderPath_EXHIB_PERMANENT());	
	}
	
	public static void persistIziObjectJSON(ResultBox rb, String json, String jsonFileName) {
		// Serialize JSON
		serializeToFileInFolder(json, rb.getFolderPath_JSON() +File.separator +jsonFileName);
		log.debug("============= JSON SERIALIZED: {}", jsonFileName);
	}
	
	public static void persistIziObject(ResultBox rb, QuerySearchObj museum) {
		// Serialize object
		String objFileName = rb.getFolderName() +".smrt";		
		serializeToFileInFolder(museum, rb.getFolderPath_JSON() +File.separator +objFileName);
		log.debug("============= OBJ SERIALIZED: {}", objFileName);
	}	

	public static void persistIziObjectImage(byte[] imageBytes, String filePath, String ext) {
		ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
	    try {
	    	BufferedImage bImage2 = ImageIO.read(bis);
			ImageIO.write(bImage2, ext, new File(filePath));
			log.debug("============= IMG SAVED: {}", filePath);
		} catch (IOException e) {
			log.error("IO error", e);
		}	
	}
	
//	public static void persistIziObject(ResultBox rb) {		
//		// Serialize description
//		String descriptionFileName = rb.getFolderName() +"_description.txt";	
////		serializeToFileInFolder(rb.getQuerySearchObject()., folderPath +File.separator +descriptionFileName);
//		log.debug("============= DESCRIPTION SERIALIZED: {}", descriptionFileName);
//	}	
	
	public static void serializeToFileInFolder(Object objToPersist, String fileName) {		
	    try {
	    	BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
	    	if(objToPersist instanceof String) {
	    		writer.write(((String)objToPersist).toString());
	    	}
	    	else {
	    		writer.write(objToPersist.toString());				
	    	}	    	
			writer.close();
		}
	    catch (IOException e) {
			log.error("Error persist file " +fileName, e);
		}
	}

	public static void infoDataRecovery(IziObject izi, Customer customer) {
		
		if(customer.getSchedule() == null) {
			customer.setSchedule(izi.getSchedule());
			log.info("============= SCHEDULE: -->" +customer.getSchedule());
		}
		
		if(customer.getAddress()==null || "".equals(customer.getAddress())) {
			customer.setAddress(izi.getContacts().getAddress().trim());
			log.info("============= ADDRESS: -->" +customer.getAddress());
		}
		
		if(customer.getPhone()==null || "".equals(customer.getPhone())) {
			customer.setPhone(izi.getContacts().getPhone_number().trim());
			log.info("============= PHONE: -->" +customer.getPhone());
		}
		
		if(customer.getWebsite()==null || "".equals(customer.getWebsite())) {
			customer.setWebsite(izi.getContacts().getWebsite().trim());
			log.info("============= WEBSITE: -->" +customer.getWebsite());
		}
		
		if(customer.getCity()==null || "".equals(customer.getCity())) {				
			customer.setCity(izi.getContacts().getCity().trim());
			if(customer.getCity()==null || "".equals(customer.getCity())) {
				customer.setCity(izi.getCity().getTitle().trim());
			}
			log.info("============= CITY: -->" +customer.getCity());				
		}

		if(customer.getCountryCode()==null || "".equals(customer.getCountryCode())) {
			customer.setCountryCode(izi.getContacts().getCountry().trim());
			if(customer.getCountryCode()==null || "".equals(customer.getCountryCode())) {
				customer.setCountryCode(izi.getCity().getLocation().getCountry_code().trim());
				if(customer.getCountryCode()==null || "".equals(customer.getCountryCode())) {
					customer.setCountryCode(izi.getLocation().getCountry_code().trim());
				}
			}
			log.info("============= COUNTRY CODE: -->" +customer.getCountryCode());
		}
		
		if(customer.getLatitude()==null || "".equals(customer.getLatitude())) {
			customer.setLatitude(izi.getLocation().getLatitude());
			log.info("============= LATITUDE: {}",customer.getLatitude());
		}
		
		if(customer.getLongitude()==null || "".equals(customer.getLongitude())) {
			customer.setLongitude(izi.getLocation().getLongitude());
			log.info("============= LONGITUDE: {}", customer.getLongitude());
		}
		
//		if(customer.getAltitude()==null || "".equals(customer.getAltitude())) {
//			customer.setAltitude(izi.getLocation().getAltitude());
//			log.info("============= ALTITUDE: {}", customer.getAltitude());
//		}
		
	}

	public static void infoLangDataRecovery(IziObject izi, Customer customer, String lang) {
		if(izi.getContent()!=null && izi.getContent().size() >0) {
			Content contents = izi.getContent().get(0);
			customer.getTitles().put(lang, contents.getTitle());
			log.info("============= LANG TITLE (" +lang +"): -->" +contents.getTitle());
			
			customer.getTitles().put(lang, contents.getSummary());				
			log.info("============= LANG SUMMARY (" +lang +"): -->" +contents.getSummary());
			
			customer.getTitles().put(lang, contents.getDesc());
			log.info("============= LANG DESCR (" +lang +"): -->" +contents.getDesc());				
		}		
	}
	
//	public void deserializeFromFile(String fileName) {
//		try {
//			FileOutputStream f = new FileOutputStream(new File(fileName));
//			ObjectOutputStream o = new ObjectOutputStream(f);
//
//			// Write objects to file
//			o.writeObject(objToPersist);
//
//			o.close();
//			f.close();
//
//			FileInputStream fi = new FileInputStream(new File("myObjects.txt"));
//			ObjectInputStream oi = new ObjectInputStream(fi);
//
//			// Read objects
//			Person pr1 = (Person) oi.readObject();
//
//			System.out.println(pr1.toString());
//			System.out.println(pr2.toString());
//
//			oi.close();
//			fi.close();
//
//		} catch (FileNotFoundException e) {
//			log.error("File not found");
//		} catch (IOException e) {
//			log.error("Error initializing stream");
//		} catch (ClassNotFoundException e) {
//		}
//	}

}

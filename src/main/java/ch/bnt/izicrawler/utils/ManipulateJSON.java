package ch.bnt.izicrawler.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;

import ch.bnt.izicrawler.client.controller.ResultBox;
import ch.bnt.izicrawler.model.IziObject;
import ch.bnt.izicrawler.model.QuerySearchObj;
import ch.bnt.izicrawler.model.form.Customer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ManipulateJSON {
	
	public static void manageFSFolder(ResultBox rb) {
		String folderPath = Globals.MAIN_OUTPUT_FOLDER +rb.getFolderName();
		// Generate Folder
		new File(folderPath).mkdirs();
		log.debug("============= DIR CREATED: {}", folderPath);
	}
	
	public static void persistIziObjectJSON(ResultBox rb) {
		String folderPath = Globals.MAIN_OUTPUT_FOLDER +rb.getFolderName();
		
		// Serialize JSON
		String jsonFileName = rb.getFolderName() +".json";
		serializeToFileInFolder(rb.getJsonString(), folderPath +File.separator +jsonFileName);
		log.debug("============= JSON SERIALIZED: {}", jsonFileName);
	}
	
	public static void persistIziObject(ResultBox rb) {
		String folderPath = Globals.MAIN_OUTPUT_FOLDER +rb.getFolderName();
		
		// Serialize object
		String objFileName = rb.getFolderName() +".smrt";		
		serializeToFileInFolder(rb.getQuerySearchObject(), folderPath +File.separator +objFileName);
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

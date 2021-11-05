package ch.bnt.izicrawler.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;

import ch.bnt.izicrawler.client.controller.ResultBox;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ManipulateJSON {
	
	public static String printReadableJSON(ResponseEntity<String> response) {		
		String body = response.getBody().substring(1, response.getBody().length()).replace("\\", "");		
		JSONObject json = new JSONObject(body);
		
		log.info(response.getBody().replace("\\", ""));
		
		return json.toString(4);
	}
	
	public static void persist(ResultBox rb) {
		
		// Format reference
		String folderName = rb.getTitle();
		folderName = folderName.replaceAll("[^a-zA-Z0-9]", "");
		folderName = StringUtils.stripAccents(folderName);
		log.info("FOLDER: " +folderName);
		
		// File to serialize
		String jsonFileName = folderName +".json";
		String objFileName = folderName +".smrt";		
//		String iconFileName = "icon.png";
//		String bannerFileName = "banner.png";
	}
	
	public static void serializeToFileInFolder(Object objToPersist, String fileName) {
		try {
			FileOutputStream f = new FileOutputStream(new File(fileName));
			ObjectOutputStream o = new ObjectOutputStream(f);
			
			o.writeObject(objToPersist);
			
			o.close();
			f.close();
		} catch (FileNotFoundException e) {
			log.error("File not found", e); 
		} catch (IOException e) {
			log.error("Error initializing stream", e);
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
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

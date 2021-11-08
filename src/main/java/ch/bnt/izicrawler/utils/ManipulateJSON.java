package ch.bnt.izicrawler.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import ch.bnt.izicrawler.client.controller.ResultBox;
import ch.bnt.izicrawler.model.QuerySearchObj;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ManipulateJSON {
	
	public static void persistIziObject(ResultBox rb) {		
		String folderPath = Globals.MAIN_OUTPUT_FOLDER +rb.getFolderName();
		// Generate Folder
		new File(folderPath).mkdirs();
		log.debug("============= DIR CREATED: {}", folderPath);
		
		// Serialize JSON
		String jsonFileName = rb.getFolderName() +".json";
//		serializeToFileInFolder(rb.getJsonString(), folderPath +File.separator +jsonFileName);
		log.debug("============= JSON SERIALIZED: {}", jsonFileName);

		// Serialize object
		String objFileName = rb.getFolderName() +".smrt";		
//		serializeToFileInFolder(rb.getQuerySearchObject(), folderPath +File.separator +objFileName);
		log.debug("============= OBJ SERIALIZED: {}", objFileName);

		// Serialize description
		String descriptionFileName = rb.getFolderName() +"_description.txt";	
//		serializeToFileInFolder(rb.getQuerySearchObject()., folderPath +File.separator +descriptionFileName);
		log.debug("============= DESCRIPTION SERIALIZED: {}", descriptionFileName);
		
	}	
	
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

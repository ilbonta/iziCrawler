package ch.bnt.izicrawler.client.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.json.JSONObject;

import ch.bnt.izicrawler.model.QuerySearchObj;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResultBox {
	
	public ResultBox(String searchKeyWord) {
		this.searchKeyWord = searchKeyWord;
//		init();
	}
	
//	private void init() {
//	}
	
	private String uuid;

	private String titleFormatted;

	private Set<String> languageSet;	
	private Map<String, String> LanguageObjMap = new HashMap<>();		
	
	private String folderName;

//	private JSONObject json;
	private String jsonString;

	private QuerySearchObj querySearchObject;
	
	private String searchKeyWord;


}

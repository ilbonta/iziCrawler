package ch.bnt.izicrawler.client.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
	private String folderPath_IMG;
	private String folderPath_JSON;
	private String folderPath_EXHIB;
	private String folderPath_EXHIB_PERMANENT;

//	private JSONObject json;
	private String jsonString;

	
	private String searchKeyWord;


}

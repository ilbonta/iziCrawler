package ch.bnt.izicrawler.client.controller;

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
	}
	
	private String folderName;

	private JSONObject json;
	private String jsonString;

	private QuerySearchObj querySearchObject;
	
	private String searchKeyWord;

	private String titleFormatted;

}

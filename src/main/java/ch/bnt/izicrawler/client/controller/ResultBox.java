package ch.bnt.izicrawler.client.controller;

import org.json.JSONObject;

import ch.bnt.izicrawler.model.QuerySearchObj;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultBox {
	
	public ResultBox(String title) {
		this.title = title;
	}

	private JSONObject json;
	private String jsonString;

	private QuerySearchObj querySearch;
	
	private String title;

	private String titleFormatted;

}

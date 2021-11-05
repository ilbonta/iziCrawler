package ch.bnt.izicrawler.client.controller;

import ch.bnt.izicrawler.model.IziObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultBox {
	
	public ResultBox(String title) {
		this.title = title;
	}

	private String resultJSON;

	private IziObject iziObject;
	
	private String title;

	private String titleFormatted;

}

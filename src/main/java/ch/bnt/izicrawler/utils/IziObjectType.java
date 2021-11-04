package ch.bnt.izicrawler.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IziObjectType {
	ATTRACTION("attraction"),
	CITY("city"),
	COLLECTION("collection"),
	COUNTRY("country"),
	MUSEUM("museum"),
	NAVIGATIONAL("navigational"),
	QUEST("quest"),
	STORY("story"),
	TOUR("tour"),
	TOURIST("tourist"),
	;

	private String paramGET;
}

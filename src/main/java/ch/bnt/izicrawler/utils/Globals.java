package ch.bnt.izicrawler.utils;

public class Globals {
	
	public static final String API_KEY = "0a2bd207-a0ca-4f0a-a6c5-8f14c674ff34";

	public static final String PROTOCOL = "https://";

	public static final String DOMAIN = "api.izi.travel/";

	public static final String MTG = "mtg/";

	public static final String OBJECTS = "objects/";
	public static final String MTGOBJECTS = "mtgobjects/";

	public static final String SEARCH = "search";

	// ENDPOINT
	public static final String SEARCH_CITY_ENDPOINT = PROTOCOL +DOMAIN +MTG +OBJECTS +SEARCH +"?languages=en&includes=translations&type=" +IziObjectType.MUSEUM.getParamGET() +"&query=";
	public static final String SEARCH_MUSEUM_ENDPOINT = PROTOCOL +DOMAIN +MTG +OBJECTS +SEARCH +"?languages=en&includes=translations&type=" +IziObjectType.MUSEUM.getParamGET() +"&query=";

	public static final String GET_OBJECT_ENDPOINT = PROTOCOL +DOMAIN +MTGOBJECTS;
	public static final String GET_OBJECT_ENDPOINT_GET_PARAM = "?languages=en";
	
	public static final String MAIN_OUTPUT_FOLDER = "C:/TempMy/izi/izicrawler/output/";

}


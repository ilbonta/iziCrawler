package ch.bnt.izicrawler.utils;

public class Globals {
	
	public static final String API_KEY = "0a2bd207-a0ca-4f0a-a6c5-8f14c674ff34";

	public static final String PROTOCOL = "https://";

	public static final String DOMAIN = "api.izi.travel/";
	public static final String DOMAIN_MEDIA = "media.izi.travel/";

	public static final String MTG = "mtg/";

	public static final String OBJECTS = "objects/";
	public static final String MTGOBJECTS = "mtgobjects/";

	public static final String SEARCH = "search";
	

	// ENDPOINT
	public static final String SEARCH_CITY_ENDPOINT = PROTOCOL +DOMAIN +MTG +OBJECTS +SEARCH +"?languages=en&includes=translations&type=" +IziObjectType.MUSEUM.getParamGET() +"&query=";
	public static final String SEARCH_MUSEUM_ENDPOINT = PROTOCOL +DOMAIN +MTG +OBJECTS +SEARCH +"?languages=en&includes=translations&type=" +IziObjectType.MUSEUM.getParamGET() +"&query=";

	public static final String GET_OBJECT_ENDPOINT = PROTOCOL +DOMAIN +MTGOBJECTS;
	public static final String GET_OBJECT_ENDPOINT_GET_PARAM = "?languages=en&form=full";
//	&except=children";

//	{MEDIA_BASE_URL}/{CONTENT_PROVIDER_UUID}/{IMAGE_UUID}_{IMAGE_SIZE}.jpg
	public static final String GET_MEDIA = PROTOCOL +DOMAIN_MEDIA;
	public static final String IMG_SIZE_1600x1200 = "_1600x1200";
	public static final String IMG_SIZE_800x600 = "_800x600";
	public static final String IMG_SIZE_480x360 = "_480x360";
	public static final String IMG_SIZE_240x180 = "_240x180";
	public static final String IMG_SIZE_120x90 = "_120x90";
	
	public static final String IMG_LOGO_EXT = "jpg";
	public static final String IMG_BRAND_LOGO_EXT = "png";

	public static final String GET_PUBLISHER = PROTOCOL +DOMAIN +MTG +"publisher/";
	
	public static final String MAIN_OUTPUT_FOLDER = "C:/TempMy/izi/izicrawler/output/";

	
	

}


package ch.bnt.izicrawler.model.dec;

import java.io.Serializable;

import lombok.Data;

@Data
public class Reviews implements Serializable {
	
	private static final long serialVersionUID = -9139433676942153515L;

	private double rating_average;
    
    private int ratings_count;
    
    private int reviews_count;
}
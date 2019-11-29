package com.example.avis.payload;

import javax.persistence.Lob;

public interface CommentaireResponse {
	Long getId();
	Long getNote();
	String getUsername();
	@Lob String getDescritpion();
	String getEmail();

}

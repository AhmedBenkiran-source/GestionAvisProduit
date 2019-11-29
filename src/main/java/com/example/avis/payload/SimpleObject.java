package com.example.avis.payload;

public class SimpleObject {
	private Long id ;
	private String name;
	private String icone;
	private Long nbrCommentaire;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIcone() {
		return icone;
	}
	public void setIcone(String icone) {
		this.icone = icone;
	}
	public Long getNbrCommentaire() {
		return nbrCommentaire;
	}
	public void setNbrCommentaire(Long nbrCommentaire) {
		this.nbrCommentaire = nbrCommentaire;
	}
	public SimpleObject() {
		super();
	}
	
}

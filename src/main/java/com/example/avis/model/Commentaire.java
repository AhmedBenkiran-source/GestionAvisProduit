package com.example.avis.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

import com.example.avis.model.audit.DateAudit;

@Entity
public class Commentaire extends DateAudit{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_commentaire;
	
	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;
	
	@Lob
	private String description;
	
	private Long note;
	
	private String type;
	
	private Long id_type;
	
	private boolean isActive;

	public Long getId_commentaire() {
		return id_commentaire;
	}

	public void setId_commentaire(Long id_commentaire) {
		this.id_commentaire = id_commentaire;
	}

	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getNote() {
		return note;
	}

	public void setNote(Long note) {
		this.note = note;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getId_type() {
		return id_type;
	}

	public void setId_type(Long id_type) {
		this.id_type = id_type;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Commentaire() {
		super();
	}

	
}

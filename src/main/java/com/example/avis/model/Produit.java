package com.example.avis.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.example.avis.model.audit.DateAudit;
import com.google.gson.JsonObject;

@Entity
public class Produit extends DateAudit implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nameProduit;

	@Lob
	private String description;
	@Lob
	private String iconeproduit;
	@Lob
	private ArrayList<String> imageProduit;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "produit_categories", joinColumns = @JoinColumn(name = "produit_id"), inverseJoinColumns = @JoinColumn(name = "categorie_id"))
	private Collection<Categorie> categories;
	
	

	@OneToOne
	@JoinColumn(name = "marque_id", referencedColumnName = "id")
	private Marque marque;

	private boolean isActive;

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Produit() {
		super();
	}

	public String getNameProduit() {
		return nameProduit;
	}

	public void setNameProduit(String nameProduit) {
		this.nameProduit = nameProduit;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Collection<Categorie> getCategories() {
		return categories;
	}

	public void setCategories(Collection<Categorie> categories) {
		this.categories = categories;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Marque getMarque() {
		return marque;
	}

	

	public ArrayList<String> getImageProduit() {
		return imageProduit;
	}

	public void setImageProduit(ArrayList<String> imageProduit) {
		this.imageProduit = imageProduit;
	}

	public void setMarque(Marque marque) {
		this.marque = marque;
	}

	public String getIconeproduit() {
		return iconeproduit;
	}

	public void setIconeproduit(String iconeproduit) {
		this.iconeproduit = iconeproduit;
	}



}

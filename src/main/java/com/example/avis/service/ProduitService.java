package com.example.avis.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.avis.model.Categorie;
import com.example.avis.model.Marque;
import com.example.avis.model.Produit;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface ProduitService {

	public Produit saveProduit(MultipartFile[] files, String produit, String marque, String[] categories,MultipartFile iconeproduit)
			throws JsonParseException, JsonMappingException, IOException;

	public Produit updateProduit(Long id, MultipartFile[] files, String produit, String marque, String[] categories,MultipartFile iconeproduit)
			throws JsonParseException, JsonMappingException, IOException;

	public boolean desactiveProduit(Produit produit);

	public boolean activeProduit(Produit produit);

	public void desactiveMarqueProduct(Long marque);
	
	public void desactiveCategorieProduct(Categorie categorie);
	
	
}

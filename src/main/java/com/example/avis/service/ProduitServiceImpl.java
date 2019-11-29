package com.example.avis.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.avis.model.Categorie;
import com.example.avis.model.Marque;
import com.example.avis.model.Produit;
import com.example.avis.repository.CategorieRepository;
import com.example.avis.repository.MarqueRepository;
import com.example.avis.repository.ProduitRepository;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import lombok.Data;

@Service
@Transactional
public class ProduitServiceImpl implements ProduitService {

	@Autowired
	private FileService fileService;
	@Autowired
	private MarqueRepository marqueRepository;
	@Autowired
	private CategorieRepository categorieRepository;
	@Autowired
	private ProduitRepository produitRepository;

	ArrayList<String> urlImage = new ArrayList<>();

	@Override
	public Produit saveProduit(MultipartFile[] files, String produit, String marque, String[] categories,MultipartFile iconeproduit)
			throws JsonParseException, JsonMappingException, IOException {

		Produit product = new ObjectMapper().readValue(produit, Produit.class);
		AddIconeToProduit(product, iconeproduit);
		AddMarqueToProduit(product, marque);
		AddCategoriesToProduit(product, categories);
		AddImagesToProduit(product, files);
		product.setActive(false);
		return produitRepository.save(product);
	}
	private void AddIconeToProduit(Produit product, MultipartFile file) throws IOException {
		String urlImage = fileService.singleFileUpload(file);
		product.setIconeproduit(urlImage);

	}
	private void AddImagesToProduit(Produit product, MultipartFile[] files) throws IOException {
		ArrayList<String> ImagesUrl = new ArrayList<>();
		ImagesUrl =   fileService.multiFileUpload(files);
		/*Gson g = new Gson();
		JsonObject convertedObject = g.fromJson(ImagesUrl.toString(), JsonObject.class);
		String str = g.toJson(convertedObject);
		System.out.println("------------------JOSN--------------" + convertedObject);
*/
		System.out.println("------------------JOSN--------------" + ImagesUrl);

		product.setImageProduit(ImagesUrl);
	}

	private void AddMarqueToProduit(Produit product, String marque) {
		Optional<Marque> brand = marqueRepository.findById(Long.parseLong(marque));
		product.setMarque(brand.get());
	}

	private void AddCategoriesToProduit(Produit product, String[] categories) {
		ArrayList<Categorie> cat = new ArrayList<Categorie>();
		for (String categorie : categories) {
			Optional<Categorie> catego = categorieRepository.findById(Long.parseLong(categorie));
			cat.add(catego.get());
		}
		product.setCategories(cat);
	}

	@Override
	public boolean desactiveProduit(Produit produit) {
		produit.setActive(false);
		produitRepository.save(produit);
		return true;
	}

	@Override
	public boolean activeProduit(Produit produit) {
		produit.setActive(true);
		produitRepository.save(produit);
		return true;
	}

	@Override
	public Produit updateProduit(Long id ,MultipartFile[] files, String produit, String marque, String[] categories,MultipartFile iconeproduit)
			throws JsonParseException, JsonMappingException, IOException {
		Produit product = new ObjectMapper().readValue(produit, Produit.class);
		product.setId(id);
		Optional<Produit> p = this.produitRepository.findById(product.getId());
		product.setCreatedAt(p.get().getCreatedAt());
		
		AddMarqueToProduit(product, marque);
		AddCategoriesToProduit(product, categories);
		AddImagesToProduit(product, files);
		product.setActive(false);
		return produitRepository.save(product);
	}

	@Override
	public void desactiveMarqueProduct(Long marque) {
		List<Produit> ListProduit = produitRepository.findByMarque(marque);
		for (Produit produit : ListProduit) {
			produit.setMarque(null);
		}
		
	}

	@Override
	public void desactiveCategorieProduct(Categorie categorie) {
		List<Produit> ListProduit = produitRepository.findAll();
		for (Produit produit : ListProduit) {
			if(produit.getCategories().remove(categorie));
		}
	
	}

	

	
}

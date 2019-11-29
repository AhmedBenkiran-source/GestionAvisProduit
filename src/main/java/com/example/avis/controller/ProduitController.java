package com.example.avis.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.avis.model.Produit;
import com.example.avis.payload.ProduitSimpleObjetRequest;
import com.example.avis.repository.ProduitRepository;
import com.example.avis.service.ProduitService;
@RestController
public class ProduitController {

	@Autowired
	private ProduitRepository produitRepository;
	

	@Autowired
	private ProduitService produitService;

	@PostMapping("/produit")
	public Produit createProduit(@RequestParam("file") MultipartFile[] files, @RequestParam("produit") String produit,
			@RequestParam("marque") String marque, @RequestParam("categories") String[] categories,@RequestParam("iconeproduit") MultipartFile iconeproduit)
			throws IOException, NoSuchAlgorithmException {

		return produitService.saveProduit(files, produit, marque, categories,iconeproduit);
	}

	@GetMapping("/produits")
	public List<Produit> getAllProduits() {
		return produitRepository.findAll();
	}

	@SuppressWarnings("deprecation")
	@GetMapping("/produit/all")
	public Page<Produit> getProduitPagination(@RequestParam(defaultValue = "0") int page) {
		return produitRepository.findAll(new PageRequest(page, 4));
	}

	@SuppressWarnings("deprecation")
	@GetMapping("/produit")
	public Page<Produit> getProduitPaginationisActive(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "0") int pagination, @RequestParam(defaultValue = "1") Boolean isActive) {
		return produitRepository.findAllEnabled(isActive, new PageRequest(page, pagination));

	}

	@DeleteMapping("/produit/{id}")
	public boolean deleteCarnet(@PathVariable Long id) {
		produitRepository.deleteById(id);
		return true;
	}

	@GetMapping("/produit/desactive/{id}")
	public boolean desactiveMarque(@PathVariable Long id) {
		Optional<Produit> produit = produitRepository.findById(id);
		return produitService.desactiveProduit(produit.get());
	}

	@GetMapping("/produit/active/{id}")
	public boolean activeProduit(@PathVariable Long id) {
		Optional<Produit> produit = produitRepository.findById(id);
		return produitService.activeProduit(produit.get());

	}
	@GetMapping("/produit/all/isActive")
	public List<Produit> getAllCategoriesIsActive(@RequestParam(defaultValue = "1") Boolean isActive) {
		return produitRepository.findAllEnabled(isActive);
	}
	@PutMapping("/produit")
	public Produit updateProduit(@RequestParam("id") Long id, @RequestParam("file") MultipartFile[] files,
			@RequestParam("produit") String produit, @RequestParam("marque") String marque,
			@RequestParam("categories") String[] categories,@RequestParam("iconeproduit") MultipartFile iconeproduit ) throws IOException, NoSuchAlgorithmException {
		return produitService.updateProduit(id, files, produit, marque, categories,iconeproduit);
	}

	/*@SuppressWarnings("deprecation")
	@GetMapping("/produit/allSimpleProduit")
	public Page<ProduitSimpleObjetRequest> getSimpleProduitPagination(@RequestParam(defaultValue = "0") int page) {
		
		return produitRepository.listeSimpleProduit(new PageRequest(page, 6));
	}
	*/
	@SuppressWarnings("deprecation")
	@GetMapping("/produit/allSimpleProduit/{id_category}")
	public Page<ProduitSimpleObjetRequest> getSimpleProduitPagination(@PathVariable Long id_category , @RequestParam(defaultValue = "0") int page,
			 @RequestParam String search) {
		System.out.println(id_category);
		System.out.println(search);
		if(id_category == -1 ) {
			return produitRepository.listeSimpleProduit(new PageRequest(page, 8));
		}else {
			return produitRepository.findSimpleProduitByCategory(id_category , new PageRequest(page, 8));
		}
		
	}
	
	@GetMapping("/produit/findSimpleProduitByMarque/{id_marque}")
	public List<ProduitSimpleObjetRequest> getfindProduitByMarque(@PathVariable Long id_marque) {	
			return produitRepository.findSimpleProduitByBrand(id_marque);
	}
	@GetMapping("/produit/{id}")
	public Produit getProduit(@PathVariable Long id) {
		return produitRepository.findById(id).get();
	}
	
	
	
}

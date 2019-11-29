package com.example.avis.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.avis.model.Categorie;
import com.example.avis.payload.LibelleCategorieObjetRequest;
import com.example.avis.repository.CategorieRepository;
import com.example.avis.service.ProduitService;
@RestController

public class CategorieController {
	@Autowired
	private CategorieRepository categorieRepository;
	@Autowired
	private ProduitService produitService;

	@SuppressWarnings("deprecation")
	@GetMapping("/categorie")
	public Page<Categorie> getCategoriesIsActivePagination(@RequestParam(defaultValue = "0") int page ,@RequestParam(defaultValue = "1") Boolean isActive) {
		return categorieRepository.findAllEnabled(isActive,new PageRequest(page, 4));
	}
	@SuppressWarnings("deprecation")
	@GetMapping("/categorie/all")
	public Page<Categorie> getAllCategoriesPagination(@RequestParam(defaultValue = "0") int page ) {
		return categorieRepository.findAll(new PageRequest(page, 4));

	}
	@GetMapping("/categories")
	public List<Categorie> getAllCategories() {
		return categorieRepository.findAll();
	}
	@GetMapping("/categorie/all/isActive")
	public List<Categorie> getAllCategoriesIsActive(@RequestParam(defaultValue = "1") Boolean isActive) {
		return categorieRepository.findAllEnabled(isActive);
	}
	
	@GetMapping("/categorie/{id}")
	public Optional<Categorie> getCarnet(@PathVariable Long id) {
		return categorieRepository.findById(id);
	}

	@GetMapping("/categorie/desactive/{id}")
	public boolean desactiveCategorie(@PathVariable Long id) {
		Optional<Categorie> categorie = categorieRepository.findById(id);
		categorie.get().setActive(false);
		categorieRepository.save(categorie.get());
		produitService.desactiveCategorieProduct(categorie.get());
		
		return true;
	}
	@GetMapping("/categorie/active/{id}")
	public boolean activeCategorie(@PathVariable Long id) {
		Optional<Categorie> categorie = categorieRepository.findById(id);
		categorie.get().setActive(true);
		categorieRepository.save(categorie.get());
		return true;
	}

	@PutMapping("/categorie")
	public Categorie updateCarnet(@RequestBody Categorie categorie) {
		Optional<Categorie> M = this.categorieRepository.findById(categorie.getId());
		categorie.setCreatedAt(M.get().getCreatedAt());
		return categorieRepository.save(categorie);
	}

	@PostMapping("/categorie")
	public Categorie createCategorie(@RequestBody Categorie categorie) {
		categorie.setActive(false);
		return categorieRepository.save(categorie);
	}
	
	@SuppressWarnings("deprecation")
	@GetMapping("/categorie/libelle")
	public Page<LibelleCategorieObjetRequest> getLibelleCategoriesIsActivePagination(@RequestParam(defaultValue = "0") int page ,@RequestParam(defaultValue = "1") Boolean isActive) {
		return categorieRepository.findAllEnabledLibelle(isActive,new PageRequest(page, 6));
	}
}

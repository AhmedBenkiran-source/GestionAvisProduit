package com.example.avis.controller;

import java.util.List;
import java.util.Optional;
import java.io.IOException;
import java.net.InetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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

import com.example.avis.model.Marque;
import com.example.avis.payload.MarqueSimpleObjetRequest;
import com.example.avis.repository.MarqueRepository;
import com.example.avis.service.FileService;
import com.example.avis.service.ProduitService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController

public class MarqueController {

	@Autowired
	private MarqueRepository brandRepository;
	

	@Autowired
	private FileService marqueService;
	
	@Autowired
	private ProduitService produitService;

	@SuppressWarnings("deprecation")
	@GetMapping("/marque")
	public Page<Marque> getMarquesIsActivePagination(@RequestParam(defaultValue = "0") int page ,@RequestParam(defaultValue = "1") Boolean isActive) {
		return brandRepository.findAllEnabled(isActive,new PageRequest(page, 8));
	}
	@SuppressWarnings("deprecation")
	@GetMapping("/marque/all")
	public Page<Marque> getAllMarquesPagination(@RequestParam(defaultValue = "0") int page ) {
		return brandRepository.findAll(new PageRequest(page, 4));

	}
	@GetMapping("/marques")
	public List<Marque> getAllMarques() {
		
		return brandRepository.findAll();
	}
	@GetMapping("/marque/all/isActive")
	public List<Marque> getAllMarquesIsActive(@RequestParam(defaultValue = "1") Boolean isActive) {
		return brandRepository.findAllEnabled(isActive);
	}

	@Autowired
	Environment environment;

	@GetMapping("/marque/{id}")
	public String getImage(@PathVariable Long id) {
		Optional<Marque> brand = brandRepository.findById(id);
		Marque marque = brand.get();

		marque.setImageMarque("http://" + InetAddress.getLoopbackAddress().getHostName() + ":"
				+ environment.getProperty("server.port") + marque.getImageMarque());
		return marque.getImageMarque();
	}

	@DeleteMapping("/marque/{id}")
	public boolean deleteCarnet(@PathVariable Long id) {
		brandRepository.deleteById(id);
		return true;
	}

	@GetMapping("/marque/desactive/{id}")
	public boolean desactiveMarque(@PathVariable Long id) {
		Optional<Marque> marque = brandRepository.findById(id);
		marque.get().setActive(false);
		brandRepository.save(marque.get());
		produitService.desactiveMarqueProduct(marque.get().getId());
		return true;
	}

	@GetMapping("/marque/active/{id}")
	public boolean activeMarque(@PathVariable Long id) {
		Optional<Marque> marque = brandRepository.findById(id);
		marque.get().setActive(true);

		brandRepository.save(marque.get());

		return true;
	}

	@PutMapping("/marque")
	public Marque updateMarque(@RequestParam("file") MultipartFile file, @RequestParam("marque") String brand)
			throws IOException {
		String urlImage = marqueService.singleFileUpload(file);
		Marque marque = new ObjectMapper().readValue(brand, Marque.class);
		marque.setImageMarque(urlImage.toString());
		Optional<Marque> M = this.brandRepository.findById(marque.getId());
		marque.setCreatedAt(M.get().getCreatedAt());
		return brandRepository.save(marque);
	}

	@PostMapping("/marque")
	public Marque createMarque(@RequestParam("file") MultipartFile file, @RequestParam("marque") String brand)
			throws IOException {

		String urlImage = marqueService.singleFileUpload(file);
		Marque marque = new ObjectMapper().readValue(brand, Marque.class);
		marque.setImageMarque(urlImage.toString());
		marque.setActive(false);
		return brandRepository.save(marque);

	}
	
	@SuppressWarnings("deprecation")
	@GetMapping("/marque/allSimpleMarque")
	public Page<MarqueSimpleObjetRequest> getSimpleMarquePagination(@RequestParam(defaultValue = "0") int page) {
		return brandRepository.listeSimpleMarque(new PageRequest(page, 8));
	}

}

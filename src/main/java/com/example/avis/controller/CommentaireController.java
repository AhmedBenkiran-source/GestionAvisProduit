package com.example.avis.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.avis.model.Commentaire;
import com.example.avis.model.User;
import com.example.avis.payload.CommentaireResponse;
import com.example.avis.repository.CommentaireRepository;
import com.example.avis.repository.UserRepository;

@RestController
@RequestMapping("/commentaire")

public class CommentaireController {

	@Autowired
	private CommentaireRepository commentaireRepository;
	@Autowired
	private UserRepository userRepository;

	@SuppressWarnings("deprecation")
	@GetMapping("/pagination/isActive")
	public Page<Commentaire> getMarquesIsActivePagination(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "1") Boolean isActive) {
		return commentaireRepository.findAllEnabled(isActive, new PageRequest(page, 4));
	}

	@SuppressWarnings("deprecation")
	@GetMapping("/pagination")
	public Page<Commentaire> getAllMarquesPagination(@RequestParam(defaultValue = "0") int page) {
		return commentaireRepository.findAll(new PageRequest(page, 4));

	}

	@GetMapping("/all")
	public List<Commentaire> getAllMarques() {

		return commentaireRepository.findAll();
	}

	@GetMapping("/all/isActive")
	public List<Commentaire> getAllCommentairesIsActive(@RequestParam(defaultValue = "1") Boolean isActive) {
		return commentaireRepository.findAllEnabled(isActive);
	}

	

	@GetMapping("/desactive/{id}")
	public boolean desactiveCommentaire(@PathVariable Long id) {
		Optional<Commentaire> commentaire = commentaireRepository.findById(id);
		commentaire.get().setActive(false);
		commentaireRepository.save(commentaire.get());
		return true;
	}

	@GetMapping("/active/{id}")
	public boolean activeCommentaire(@PathVariable Long id) {
		Optional<Commentaire> commentaire = commentaireRepository.findById(id);
		commentaire.get().setActive(true);
		commentaireRepository.save(commentaire.get());
		return true;
	}

	@SuppressWarnings("deprecation")
	@GetMapping("/findproduit/pagination/{id}")
	public Page<CommentaireResponse> getCommentaireProduct(@RequestParam(defaultValue = "0") String page,
			@PathVariable String id) {
		int p = Integer.parseInt(page);
		Long i = Long.parseLong(id);
		return commentaireRepository.findCommentaireProduitByIdPagination(i, new PageRequest(p, 2));
	}

	@SuppressWarnings("deprecation")
	@GetMapping("/findmarque/pagination/{id}")
	public Page<CommentaireResponse> getCommentairMarque(@RequestParam(defaultValue = "0") int page,
			@PathVariable Long id) {
		return commentaireRepository.findCommentaireMarqueByIdPagination(id, new PageRequest(page, 2));
	}

	@PostMapping("/add/{user_id}")
	public Commentaire createCommentaire(@RequestBody Commentaire commentaire, @PathVariable String user_id) {
		User user = userRepository.findById(Long.parseLong(user_id)).get();
		commentaire.setUser(user);
		commentaire.setActive(false);
		return commentaireRepository.save(commentaire);
	}
}

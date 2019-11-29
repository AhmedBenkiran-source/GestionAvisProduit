package com.example.avis.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.avis.model.Commentaire;
import com.example.avis.payload.CommentaireResponse;

public interface CommentaireRepository extends JpaRepository<Commentaire, Long> {
	@Query(value = "select * from Commentaire where is_active = ?1", nativeQuery = true)
	public Page<Commentaire> findAllEnabled(boolean active, PageRequest pageRequest);

	@Query(value = "select * from Commentaire where is_active = ?1", nativeQuery = true)
	public List<Commentaire> findAllEnabled(boolean active);
	
	@Query(value = "select * from Commentaire  where id_type = ?1 is_active = true", nativeQuery = true)
	public List<Commentaire> findAllEnabledByIdType(Long id);

	//@Query(value = "select * from  Commentaire where type = 'produit' and is_active = true and id_type = ?1", nativeQuery = true)
	@Query( "select c.id as id , c.user.username as username ,c.user.email as email, c.description as descritpion ,c.note as note" + 
			"			from Commentaire c   " + 
			"			 where type = 'produit' and is_active = true and c.id_type = ?1 ")
	public Page<CommentaireResponse> findCommentaireProduitByIdPagination(Long id, PageRequest pageRequest);
	
	@Query( "select c.id as id , c.user.username as username ,c.user.email as email, c.description as descritpion ,c.note as note" + 
			"			from Commentaire c   " + 
			"			 where type = 'marque' and is_active = true and c.id_type = ?1 ")
	public Page<CommentaireResponse> findCommentaireMarqueByIdPagination(Long id, PageRequest pageRequest);
	
	
	@Query(value = "select * from  Commentaire where type = 'marque' and is_active = true and id_type = ?1", nativeQuery = true)
	public Page<Commentaire> findCommentaireMarqueById(Long id, PageRequest pageRequest);
	
	@Query(value = "select * from  Commentaire where type = 'produit' and is_active = true and id_type = ?1", nativeQuery = true)
	public Commentaire findCommentaireProduitById(Long id);
	
}

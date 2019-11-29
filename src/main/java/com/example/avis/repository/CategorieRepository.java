package com.example.avis.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.avis.model.Categorie;
import com.example.avis.payload.LibelleCategorieObjetRequest;

public interface CategorieRepository extends JpaRepository<Categorie, Long>  {

	@Query(value = "select * from Categorie where is_active = ?1" , nativeQuery = true)
	public Page<Categorie> findAllEnabled(boolean active,PageRequest pageRequest);
	
	@Query(value = "select * from Categorie where is_active = ?1" , nativeQuery = true)
	public List<Categorie> findAllEnabled(boolean active);
	
	@Query(value = "select id as id , libelle as libelle from Categorie where is_active = ?1" , nativeQuery = true)
	public Page<LibelleCategorieObjetRequest> findAllEnabledLibelle(boolean active,PageRequest pageRequest);

}

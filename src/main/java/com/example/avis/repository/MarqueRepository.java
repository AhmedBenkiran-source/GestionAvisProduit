package com.example.avis.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.avis.model.Categorie;
import com.example.avis.model.Marque;
import com.example.avis.payload.MarqueSimpleObjetRequest;
import com.example.avis.payload.ProduitSimpleObjetRequest;

@Repository
public interface MarqueRepository extends JpaRepository<Marque, Long> {
	@Query(value = "select * from Marque where is_active = ?1", nativeQuery = true)
	public Page<Marque> findAllEnabled(boolean active, PageRequest pageRequest);
	
	@Query(value = "select * from Marque where is_active = ?1" , nativeQuery = true)
	public List<Marque> findAllEnabled(boolean active);
	
	@Query(" select p.id as id,p.libelle as name,p.ImageMarque as icone  ,"
			+ "count(id_commentaire) as nbrCommentaire " + 
			"		from Marque p left join Commentaire c on  p.id = c.id_type  "+ 
			" where  p.isActive = true"+
			
			"		group by p.id")
	public Page<MarqueSimpleObjetRequest> listeSimpleMarque(PageRequest pageRequest);
}

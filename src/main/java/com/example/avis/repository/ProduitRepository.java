package com.example.avis.repository;

import java.sql.ResultSet;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.avis.model.Commentaire;
import com.example.avis.model.Marque;
import com.example.avis.model.Produit;
import com.example.avis.payload.ProduitSimpleObjetRequest;
import com.example.avis.payload.findProduitByCategory;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {
	@Query(value = "select * from Produit where is_active = ?1", nativeQuery = true)
	public Page<Produit> findAllEnabled(boolean active, PageRequest pageRequest);

	@Query(value = "select * from Produit where marque_id = ?1", nativeQuery = true)
	public List<Produit> findByMarque(Long marque);

	@Query(value = "select * from Produit where is_active = ?1", nativeQuery = true)
	public List<Produit> findAllEnabled(boolean active);
	
	@Query(value = "select id,nameProduit,ImageProduit,COUNT(description) from Produit p , Commentaire c where p.id == c.id_type is_active = ?1", nativeQuery = true)
	public List<Produit> findAllEnabledNotPagination(boolean active);

	/*
	 * @Query(
	 * value="select id,name_produit, image_produit,count(c.id_type)as id_type from produit p ,commentaire c"
	 * + " where p.id = c.id_type AND c.is_active = true and p.is_active = true"
	 * ,nativeQuery = true) public Page<ProduitSimpleObjetRequest>
	 * listeSimpleProduit(PageRequest pageRequest);
	 */

	@Query(" select p.id as id,p.nameProduit as name,p.iconeproduit as icone,"
			+ "count(id_commentaire) as nbrCommentaire "
			+ "		from Produit p left join Commentaire c on  p.id = c.id_type  " + " where  p.isActive = true "
			+ "		group by p.id")
	public Page<ProduitSimpleObjetRequest> listeSimpleProduit(PageRequest pageRequest);

	@Query(value =" select p.id as id,p.name_produit as name,p.iconeproduit as icone,"
			+ "count(id_commentaire) as nbrCommentaire "
			+ "		from Produit p left join Commentaire c on  p.id = c.id_type  "
			+ " where  p.is_active = true and p.marque_id = ?1" + "		group by p.id", nativeQuery = true)
	public List<ProduitSimpleObjetRequest> findSimpleProduitByBrand(Long id_marque);

	@Query(value = " select p.id as id,p.name_produit as name,p.iconeproduit as icone,"
			+ " count(id_commentaire) as nbrCommentaire from produit_categories pc , Produit p left join Commentaire c on  p.id = c.id_type  "
			+ "			 where  p.is_active = true and pc.categorie_id = ?1 and p.id = pc.produit_id"
			+ "					group by p.id", nativeQuery = true)
	public Page<ProduitSimpleObjetRequest> findSimpleProduitByCategory(Long id_category, PageRequest pageRequest);

}

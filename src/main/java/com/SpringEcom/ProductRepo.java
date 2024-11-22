package com.SpringEcom;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepo extends JpaRepository<Product, Integer>{
	@Query("select p from Product p where "+
			"LOWER(p.name) LIKE LOWER(CONCAT('%',:keyword,'%')) OR "+
			"LOWER(p.description) LIKE LOWER(CONCAT('%',:keyword,'%')) OR "+
			"LOWER(p.brand) LIKE LOWER(CONCAT('%',:keyword,'%')) OR "+
			"LOWER(p.category) LIKE LOWER(CONCAT('%',:keyword,'%'))")
	List<Product> findAllByKeyword(String keyword);

}

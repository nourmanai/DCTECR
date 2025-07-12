package tn.esprit.spring.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.entities.ProductSKU;


@Repository
public interface ProductSKURepository extends MongoRepository<ProductSKU, String> {
  
}

package tn.esprit.spring.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.entities.Product;


@Repository
public interface ProductRepository extends MongoRepository<Product, String> {


}

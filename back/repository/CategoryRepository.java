package tn.esprit.spring.repository;

import tn.esprit.spring.entities.Category;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {


}

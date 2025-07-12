package tn.esprit.spring.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.Ad;

@Repository
public interface AdRepository extends MongoRepository<Ad, String> {
  

}

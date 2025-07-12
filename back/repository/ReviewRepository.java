package tn.esprit.spring.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.entities.Review;


@Repository
public interface ReviewRepository extends MongoRepository< Review, String> {
   
}

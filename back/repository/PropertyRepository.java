package tn.esprit.spring.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.entities.Property;


@Repository
public interface PropertyRepository extends MongoRepository<Property, String> {


}

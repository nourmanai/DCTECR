package tn.esprit.spring.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.entities.FactTable;


@Repository
public interface FactTableRepository extends MongoRepository<FactTable, String> {
  
}

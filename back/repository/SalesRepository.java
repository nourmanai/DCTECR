package tn.esprit.spring.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.entities.Sales;


@Repository
public interface SalesRepository extends MongoRepository<Sales, String> {
   

}

package tn.esprit.spring.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.entities.Supplier;


@Repository
public interface SupplierRepository extends MongoRepository<Supplier, String> {


}

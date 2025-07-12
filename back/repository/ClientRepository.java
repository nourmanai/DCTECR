package tn.esprit.spring.repository;


import tn.esprit.spring.entities.Client;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ClientRepository extends MongoRepository<Client, ObjectId> {

	
}

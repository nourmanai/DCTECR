package tn.esprit.spring.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.entities.Account;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {
	

}

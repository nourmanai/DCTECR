package tn.esprit.spring.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.entities.Team;


@Repository
public interface TeamRepository extends MongoRepository<Team, String> {
   

}

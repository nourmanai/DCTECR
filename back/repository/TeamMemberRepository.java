package tn.esprit.spring.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.entities.TeamMember;


@Repository
public interface TeamMemberRepository extends MongoRepository<TeamMember, String> {
   

}

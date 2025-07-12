package tn.esprit.spring.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.Campaign;

@Repository
public interface CampaignRepository extends MongoRepository<Campaign, String> {


}

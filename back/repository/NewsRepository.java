package tn.esprit.spring.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.entities.News;


@Repository
public interface NewsRepository extends MongoRepository<News, String> {


}

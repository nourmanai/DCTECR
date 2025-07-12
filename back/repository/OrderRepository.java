package tn.esprit.spring.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.entities.Order;


@Repository
public interface OrderRepository extends MongoRepository<Order, String> {


}

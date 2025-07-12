package tn.esprit.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;


@Configuration
public class MongoConfig {
	

    @Autowired
    private MongoClient mongoClient;

    
   

    @Bean
    public MongoDatabase mongoDatabase() {
        return mongoClient.getDatabase("DCSTEC_DW");
    }

    
}


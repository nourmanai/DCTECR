package tn.esprit.spring.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Ad;
import tn.esprit.spring.entities.Client;
import tn.esprit.spring.serviceInterface.IAdService;
import tn.esprit.spring.serviceInterface.IGlobalService;

@Service
@Slf4j
public class AdsServiceImpl implements IAdService {
	
	 private static final Logger log = LoggerFactory.getLogger(AdsServiceImpl.class);

		MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
		MongoDatabase database = mongoClient.getDatabase("DCSTEC_DW");
		MongoCollection<Document> collection = database.getCollection("DIM_Ads");

		
		@Autowired
		IGlobalService globalService;	

		
		// retrieve All Ads
		
//	@Override
//	public List<Ad> retrieveAllAds() {
//		List<Ad> ads =  new ArrayList<>();
//	    for (Document document : collection.find()) {
//	        ObjectId id = document.getObjectId("_id"); 
//	        Map<String, Object> AdFields = new HashMap<>();
//	        for (Entry<String, Object> entry : document.entrySet()) {
//	            if (!entry.getKey().equals("_id")) { 
//	            	AdFields.put(entry.getKey(), entry.getValue());
//	            }
//	        }
//	        Ad ad = new Ad(id, AdFields);
//			 log.info("ad log: {}", ad);
//		     log.info("ads list: {}","Ad{" +
//	                "id=" + ad.getId() +
//		                ", fields=" + ad.getFields() +
//		                '}');
//			 ads.add(ad);
//	        
//	        
//
//	    }
//	return ads;
//	
//	}

		@Override
		public List<Ad> retrieveAllAds() {
		    List<Ad> ads = new ArrayList<>();
		    for (Document document : collection.find()) {
		        ObjectId id = document.getObjectId("_id");
		        Map<String, Object> AdFields = new HashMap<>();
		        for (Entry<String, Object> entry : document.entrySet()) {
		            if (!entry.getKey().equals("_id")) {
		                Object value = entry.getValue();
		                if (value instanceof ObjectId) {
		                    AdFields.put(entry.getKey(), ((ObjectId) value).toHexString());
		                } else {
		                    AdFields.put(entry.getKey(), value);
		                }
		            }
		        }
		        Ad ad = new Ad(id, AdFields);
//		        log.info("ad log: {}", ad);
//		        log.info("ads list: {}", "Ad{" +
//		                "id=" + ad.getId() +
//		                ", fields=" + ad.getFields() +
//		                '}');
		        ads.add(ad);
		    }
		    return ads;
		}

	// get Ad by Field
	
	@Override
	public List<Ad> getAdbyField(String fieldName, String value) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		List<Ad> ads = retrieveAllAds();
		List<Ad> matching = globalService.getObjectsByFieldValue(ads, fieldName, value);

		if (!matching.isEmpty()) {
		    System.out.println(matching.size() + " Ads have been found.");
		    return matching;
		} else {
		    System.out.println("No Ads found.");
		    return Collections.emptyList();
		}
		
	}
	

	
	// Add Ad 
	@Override
	public void addAd( Map<String, Object> fields)
	{
		globalService.addDocument(collection, fields);

	}
	
	// Update Ad 
	@Override	 
	public void updateAd( ObjectId id, Map<String, Object> fields) {
					
		globalService.updateDocument(collection, id, fields);
		
	}
	
	
	// Delete Ad 
	@Override	 
	public void DeleteAd( ObjectId id) {
					
		globalService.deleteDocument(collection, id);
		
	}
	
	// Add Field to  Ad 
		@Override	 
		public void AddFieldToAd( ObjectId id, String fieldName, String fieldValue) {
						
			globalService.addFieldToDocument(collection, id, fieldName, fieldValue);
			
		}	
}

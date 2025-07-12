package tn.esprit.spring.service;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Client;
import tn.esprit.spring.entities.Review;
import tn.esprit.spring.serviceInterface.IGlobalService;
import tn.esprit.spring.serviceInterface.IReviewService;

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
@Service
@Slf4j
public class ReviewServiceImpl implements IReviewService {
	
    private static final Logger log = LoggerFactory.getLogger(ReviewServiceImpl.class);

    MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
	MongoDatabase database = mongoClient.getDatabase("DCSTEC_DW");
	MongoCollection<Document> collection = database.getCollection("DIM_Reviews");

	@Autowired
	IGlobalService globalService;	
	
	@Override
	public List<Review> retrieveAllReviews() {
		
		List< Review> reviews =  new ArrayList<>();
	    for (Document document : collection.find()) {
	        ObjectId id = document.getObjectId("_id"); 
	        Map<String, Object> Fields = new HashMap<>();
	        for (Entry<String, Object> entry : document.entrySet()) {
	            if (!entry.getKey().equals("_id")) {
	                Object value = entry.getValue();
	                if (value instanceof ObjectId) {
	                	Fields.put(entry.getKey(), ((ObjectId) value).toHexString());
	                } else {
	                	Fields.put(entry.getKey(), value);
	                }
	            }
	        }
	        Review obj = new Review(id, Fields);
//			 log.info("review log: {}", obj);
//			 log.info("reviews list: {}","review{" +
//		                "id=" + obj.getId() +
//		                ", fields=" + obj.getFields() +
//		                '}');
			 reviews.add(obj);	        	        
	    }
		return reviews;	
	}
	

	// get Reviews by Field
	
	@Override
	public List<Review> getReviewbyField(String fieldName, String value) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		List<Review> Reviews = retrieveAllReviews();
		List<Review> matching = globalService.getObjectsByFieldValue(Reviews, fieldName, value);

		if (!matching.isEmpty()) {
		    System.out.println(matching.size() + " Reviews have been found.");
		    return matching;
		} else {
		    System.out.println("No Reviews found.");
		    return Collections.emptyList();
		}
		
	}
	
	// Add Review 
	@Override
	public void addReview( Map<String, Object> fields)
	{
		globalService.addDocument(collection, fields);

	}
	
	// Update Review 
	@Override	 
	public void updateReview( ObjectId id, Map<String, Object> fields) {
					
		globalService.updateDocument(collection, id, fields);
		
	}
	
	
	// Delete Review 
	@Override	 
	public void DeleteReview( ObjectId id) {
					
		globalService.deleteDocument(collection, id);
		
	}	
	
	// Add Field to  Review 
	@Override	 
	public void AddFieldToReview( ObjectId id, String fieldName, String fieldValue) {
					
		globalService.addFieldToDocument(collection, id, fieldName, fieldValue);
		
	}	
}

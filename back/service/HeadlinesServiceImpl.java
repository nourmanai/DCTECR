package tn.esprit.spring.service;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Client;
import tn.esprit.spring.entities.Headlines;
import tn.esprit.spring.serviceInterface.IGlobalService;
import tn.esprit.spring.serviceInterface.IHeadlinesService;

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
public class HeadlinesServiceImpl implements IHeadlinesService {
	
    private static final Logger log = LoggerFactory.getLogger(HeadlinesServiceImpl.class);

    MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
	MongoDatabase database = mongoClient.getDatabase("DCSTEC_DW");
	MongoCollection<Document> collection = database.getCollection("DIM_Headlines");

	
	@Autowired
	IGlobalService globalService;	

	
	// get all Headlines
	@Override
	public List<Headlines> retrieveAllHeadlines() {
		List<Headlines>headlines=new ArrayList<>();
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
	        Headlines obj = new Headlines(id, Fields);
//			 log.info("Headline log: {}", obj);
//			 log.info("Headlines list: {}","Headline{" +
//		                "id=" + obj.getId() +
//		                ", fields=" + obj.getFields() +
//		                '}');
			 headlines.add(obj);
	    }
		return headlines;
	
	}

	
	// get Headlines by Field
	
	@Override
	public List<Headlines> getHeadlinesbyField(String fieldName, String value) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		List<Headlines> headlines = retrieveAllHeadlines();
		List<Headlines> matching = globalService.getObjectsByFieldValue(headlines, fieldName, value);

		if (!matching.isEmpty()) {
		    System.out.println(matching.size() + " headlines have been found.");
		    return matching;
		} else {
		    System.out.println("No headlines found.");
		    return Collections.emptyList();
		}
		
	}
	
	
	// Add Headlines 
	@Override
	public void addHeadline( Map<String, Object> fields)
	{
		globalService.addDocument(collection, fields);

	}
	
	// Update Headlines 
	@Override	 
	public void updateHeadline( ObjectId id, Map<String, Object> fields) {
					
		globalService.updateDocument(collection, id, fields);
		
	}
	
	
	// Delete Headlines 
	@Override	 
	public void DeleteHeadline( ObjectId id) {
					
		globalService.deleteDocument(collection, id);
		
	}	
	
	// Add Field to  Headlines 
	@Override	 
	public void AddFieldToHeadlines( ObjectId id, String fieldName, String fieldValue) {
					
		globalService.addFieldToDocument(collection, id, fieldName, fieldValue);
		
	}	

}

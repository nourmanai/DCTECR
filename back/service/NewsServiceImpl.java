package tn.esprit.spring.service;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Client;
import tn.esprit.spring.entities.News;
import tn.esprit.spring.serviceInterface.IGlobalService;
import tn.esprit.spring.serviceInterface.INewsService;

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
public class NewsServiceImpl implements INewsService {
	
    private static final Logger log = LoggerFactory.getLogger(NewsServiceImpl.class);

    MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
	MongoDatabase database = mongoClient.getDatabase("DCSTEC_DW");
	MongoCollection<Document> collection = database.getCollection("DIM_News");

	@Autowired
	IGlobalService globalService;	
	
	@Override
	public List<News> retrieveAllNews() {
		
		List<News> news =  new ArrayList<>();
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
	        News obj = new News(id, Fields);
//			 log.info("new log: {}", obj);
//			 log.info("news list: {}","news{" +
//		                "id=" + obj.getId() +
//		                ", fields=" + obj.getFields() +
//		                '}');
			 news.add(obj);
	        
	        

	    }
		return news;
	
	}
	

	// get News by Field
	
	@Override
	public List<News> getNewsbyField(String fieldName, String value) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		List<News> news = retrieveAllNews();
		List<News> matching = globalService.getObjectsByFieldValue(news, fieldName, value);

		if (!matching.isEmpty()) {
		    System.out.println(matching.size() + " news have been found.");
		    return matching;
		} else {
		    System.out.println("No news found.");
		    return Collections.emptyList();
		}
		
	}
	
	// Add News 
	@Override
	public void addNews( Map<String, Object> fields)
	{
		globalService.addDocument(collection, fields);

	}
	
	// Update News 
	@Override	 
	public void updateNews( ObjectId id, Map<String, Object> fields) {
					
		globalService.updateDocument(collection, id, fields);
		
	}
	
	
	// Delete News 
	@Override	 
	public void DeleteNews( ObjectId id) {
					
		globalService.deleteDocument(collection, id);
		
	}	
	
	// Add Field to  News 
	@Override	 
	public void AddFieldToNews( ObjectId id, String fieldName, String fieldValue) {
					
		globalService.addFieldToDocument(collection, id, fieldName, fieldValue);
		
	}	
}

package tn.esprit.spring.service;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Category;
import tn.esprit.spring.entities.Client;
import tn.esprit.spring.serviceInterface.ICategoryService;
import tn.esprit.spring.serviceInterface.IGlobalService;

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
public class CategoryServiceImpl implements ICategoryService {
	
    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);
	
    MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
	MongoDatabase database = mongoClient.getDatabase("DCSTEC_DW");
	MongoCollection<Document> collection = database.getCollection("DIM_Categories");

	
	@Autowired
	IGlobalService globalService;	
	
	
	@Override
	public List<Category> retrieveAllCategories() {
		
		List<Category> categories =  new ArrayList<>();
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
	        Category category = new Category(id, Fields);
//			 log.info("Category log: {}", category);
//			 log.info("Category list: {}","Category{" +
//		                "id=" + category.getId() +
//		                ", fields=" + category.getFields() +
//		                '}');
			 categories.add(category);
	        
	        

	    }
		return categories;
	
	}
	
	// get Categories by Field
	
			@Override
			public List<Category> getCategorybyField(String fieldName, String value) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
				List<Category> Categories = retrieveAllCategories();
				List<Category> matching = globalService.getObjectsByFieldValue(Categories, fieldName, value);

				if (!matching.isEmpty()) {
				    System.out.println(matching.size() + " Categories have been found.");
				    return matching;
				} else {
				    System.out.println("No Categories found.");
				    return Collections.emptyList();
				}
				
			}

			// Add Category 
			@Override
			public void addCategory( Map<String, Object> fields)
			{
				globalService.addDocument(collection, fields);

			}
			
			// Update Category 
			@Override	 
			public void updateCategory( ObjectId id, Map<String, Object> fields) {
							
				globalService.updateDocument(collection, id, fields);
				
			}
			
			
			// Delete Category 
			@Override	 
			public void DeleteCategory( ObjectId id) {
							
				globalService.deleteDocument(collection, id);
				
			}	

	
			// Add Field to  Category 
			@Override	 
			public void AddFieldToCategory( ObjectId id, String fieldName, String fieldValue) {
							
				globalService.addFieldToDocument(collection, id, fieldName, fieldValue);
				
			}	
    
}

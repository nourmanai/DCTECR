package tn.esprit.spring.service;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Client;
import tn.esprit.spring.entities.Product;
import tn.esprit.spring.serviceInterface.IGlobalService;
import tn.esprit.spring.serviceInterface.IProductService;

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
public class ProductServiceImpl implements IProductService {
	
    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

	MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
	MongoDatabase database = mongoClient.getDatabase("DCSTEC_DW");
	MongoCollection<Document> collection = database.getCollection("DIM_Products");

	@Autowired
	IGlobalService globalService;	
	
	@Override
	public List< Product> retrieveAllProducts() {
		
		List< Product> products =  new ArrayList<>();
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
	        Product obj = new Product(id, Fields);
//			 log.info("Product log: {}", obj);
//			 log.info("Products list: {}","Product{" +
//		                "id=" + obj.getId() +
//		                ", fields=" + obj.getFields() +
//		                '}');
			 products.add(obj);
	        
	        

	    }
		return products;
	
	}
	

	// get Products by Field
	
	@Override
	public List<Product> getProductbyField(String fieldName, String value) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		List<Product> Products = retrieveAllProducts();
		List<Product> matching = globalService.getObjectsByFieldValue(Products, fieldName, value);

		if (!matching.isEmpty()) {
		    System.out.println(matching.size() + " Products have been found.");
		    return matching;
		} else {
		    System.out.println("No Products found.");
		    return Collections.emptyList();
		}
		
	}
	
	// Add Product 
	@Override
	public void addProduct( Map<String, Object> fields)
	{
		globalService.addDocument(collection, fields);

	}
	
	// Update Product 
	@Override	 
	public void updateProduct( ObjectId id, Map<String, Object> fields) {
					
		globalService.updateDocument(collection, id, fields);
		
	}
	
	
	// Delete Product 
	@Override	 
	public void DeleteProduct( ObjectId id) {
					
		globalService.deleteDocument(collection, id);
		
	}	
	// Add Field to  Product 
		@Override	 
		public void AddFieldToProduct( ObjectId id, String fieldName, String fieldValue) {
						
			globalService.addFieldToDocument(collection, id, fieldName, fieldValue);
			
		}	
}

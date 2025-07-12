package tn.esprit.spring.service;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Client;
import tn.esprit.spring.entities.ProductSKU;
import tn.esprit.spring.serviceInterface.IGlobalService;
import tn.esprit.spring.serviceInterface.IProductSKUService;

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
public class ProductSKUServiceImpl implements IProductSKUService {
	
    private static final Logger log = LoggerFactory.getLogger(ProductSKUServiceImpl.class);

    MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
	MongoDatabase database = mongoClient.getDatabase("DCSTEC_DW");
	MongoCollection<Document> collection = database.getCollection("DIM_Products_SKU");

	@Autowired
	IGlobalService globalService;	
	
	@Override
	public List<  ProductSKU> retrieveAllProductSKUs() {
		
		List<  ProductSKU> productSKUs =  new ArrayList<>();
	    for (Document document : collection.find()) {
	        ObjectId id = document.getObjectId("_id"); 
	        Map<String, Object> Fields = new HashMap<>();
	      
	        for (Entry<String, Object> entry : document.entrySet()) {
	            if (!entry.getKey().equals("_id")) {
	                Object value = entry.getValue();
	                
	                if (value instanceof ObjectId) {
	                	Fields.put(entry.getKey(), ((ObjectId) value).toHexString());
	                } else if (value instanceof List) {
	                    List<?> valueList = (List<?>) value;
	                    List<Object> convertedList = new ArrayList<>();
	                    
	                    for (Object item : valueList) {
	                        if (item instanceof ObjectId) {
	                            convertedList.add(((ObjectId) item).toHexString());
	                        } else {
	                            convertedList.add(item);
	                        }
	                    }
	                    Fields.put(entry.getKey(), convertedList);
	                } else {
	                	Fields.put(entry.getKey(), value);
	                }
	            }
	        }
	        ProductSKU obj = new ProductSKU(id, Fields);
//			 log.info("productSKU log: {}", obj);
//			 log.info("productSKUs list: {}","productSKU{" +
//		                "id=" + obj.getId() +
//		                ", fields=" + obj.getFields() +
//		                '}');
			 productSKUs.add(obj);
	        
	        

	    }
		return productSKUs;
	
	}

	

	// get productSKUs by Field
	
	@Override
	public List<ProductSKU> getProductSKUbyField(String fieldName, String value) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		List<ProductSKU> productSKUs = retrieveAllProductSKUs();
		List<ProductSKU> matching = globalService.getObjectsByFieldValue(productSKUs, fieldName, value);

		if (!matching.isEmpty()) {
		    System.out.println(matching.size() + " productSKUs have been found.");
		    return matching;
		} else {
		    System.out.println("No productSKUs found.");
		    return Collections.emptyList();
		}
		
	}
	
	// Add ProductSKU 
	@Override
	public void addProductSKU( Map<String, Object> fields)
	{
		globalService.addDocument(collection, fields);

	}
	
	// Update ProductSKU 
	@Override	 
	public void updateProductSKU( ObjectId id, Map<String, Object> fields) {
					
		globalService.updateDocument(collection, id, fields);
		
	}
	
	
	// Delete ProductSKU 
	@Override	 
	public void DeleteProductSKU( ObjectId id) {
					
		globalService.deleteDocument(collection, id);
		
	}	
	
	// Add Field to  ProductSKU 
	@Override	 
	public void AddFieldToProductSKU( ObjectId id, String fieldName, String fieldValue) {
					
		globalService.addFieldToDocument(collection, id, fieldName, fieldValue);
		
	}	
}

package tn.esprit.spring.service;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Client;
import tn.esprit.spring.entities.Property;
import tn.esprit.spring.serviceInterface.IGlobalService;
import tn.esprit.spring.serviceInterface.IPropertyService;

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
public class PropertyServiceImpl implements IPropertyService {
	
    private static final Logger log = LoggerFactory.getLogger(PropertyServiceImpl.class);

    MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
	MongoDatabase database = mongoClient.getDatabase("DCSTEC_DW");
	MongoCollection<Document> collection = database.getCollection("DIM_Products_Attributes");

	@Autowired
	IGlobalService globalService;	
	
	
	// get all Properties
	@Override
	public List<  Property> retrieveAllProperties() {
		
		List<  Property> properties =  new ArrayList<>();
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
	        Property obj = new Property(id, Fields);
//			 log.info("Property log: {}", obj);
//			 log.info("Properties list: {}","Property{" +
//		                "id=" + obj.getId() +
//		                ", fields=" + obj.getFields() +
//		                '}');
			 properties.add(obj);	        	      
	    }
		return properties;	
	}
	

	// get Property by Field
	
	@Override
	public List<Property> getPropertybyField(String fieldName, String value) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		List<Property> properties = retrieveAllProperties();
		List<Property> matching = globalService.getObjectsByFieldValue(properties, fieldName, value);

		if (!matching.isEmpty()) {
		    System.out.println(matching.size() + " properties have been found.");
		    return matching;
		} else {
		    System.out.println("No properties found.");
		    return Collections.emptyList();
		}
		
	}
	
	// Add Property 
	@Override
	public void addProperty( Map<String, Object> fields)
	{
		globalService.addDocument(collection, fields);

	}
	
	// Update Property 
	@Override	 
	public void updateProperty( ObjectId id, Map<String, Object> fields) {
					
		globalService.updateDocument(collection, id, fields);
		
	}
	
	
	// Delete Property 
	@Override	 
	public void DeleteProperty( ObjectId id) {
					
		globalService.deleteDocument(collection, id);
		
	}	
	
	// Add Field to  Property 
	@Override	 
	public void AddFieldToProperty( ObjectId id, String fieldName, String fieldValue) {
					
		globalService.addFieldToDocument(collection, id, fieldName, fieldValue);
		
	}	
}
